package com.treay.swingojbackendjudgeservice.judge.service.impl;

import cn.hutool.json.JSONUtil;
import com.treay.swingojbackendcommon.common.ErrorCode;
import com.treay.swingojbackendcommon.exception.BusinessException;
import com.treay.swingojbackendjudgeservice.judge.JudgeManager;
import com.treay.swingojbackendjudgeservice.judge.codesandbox.CodeSandBox;
import com.treay.swingojbackendjudgeservice.judge.codesandbox.CodeSandBoxProxy;
import com.treay.swingojbackendjudgeservice.judge.codesandbox.CodeSandboxFactory;
import com.treay.swingojbackendjudgeservice.judge.service.JudgeService;
import com.treay.swingojbackendjudgeservice.judge.strategy.JudgeContext;
import com.treay.swingojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.treay.swingojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import com.treay.swingojbackendmodel.model.codesandbox.JudgeInfo;
import com.treay.swingojbackendmodel.model.dto.question.JudgeCase;
import com.treay.swingojbackendmodel.model.entity.Question;
import com.treay.swingojbackendmodel.model.entity.QuestionSubmit;
import com.treay.swingojbackendmodel.model.enums.JudgeInfoMessageEnum;
import com.treay.swingojbackendmodel.model.enums.QuestionSubmitStatusEnum;
import com.treay.swingojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Treay
 * 判题服务实现类
 */
@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionFeignClient questionFeignClient;

    @Resource
    private JudgeManager judgeManager;

    @Value("${codesandbox.type:example}")
    private String judgeType;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        // 1、传入题目的提交 id，获取到对应的题目、提交信息（包含代码、编程语言等）
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        // 通过提交的信息中的题目id 获取到题目的全部信息
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionFeignClient.getQuestionById(questionId);
        if (questionId == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        // 2、如果题目提交状态不为等待中
        if (!questionSubmit.getSubmitState().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }
        // 3、更改判题（题目提交）的状态为 “判题中”，防止重复执行，也能让用户即时看到状态
        QuestionSubmit updateQuestionSubmit = new QuestionSubmit();
        updateQuestionSubmit.setId(questionSubmitId);
        updateQuestionSubmit.setSubmitState(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean updateState = questionFeignClient.updateQuestionSubmitById(updateQuestionSubmit);
        if (!updateState) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新失败");
        }
        // 4、调用沙箱，获取到执行结果
        CodeSandBox codeSandbox = CodeSandboxFactory.newInstance(judgeType);
        codeSandbox = new CodeSandBoxProxy(codeSandbox);
        String submitLanguage = questionSubmit.getSubmitLanguage();
        String submitCode = questionSubmit.getSubmitCode();
        // 获取输入用例
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCasesList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        // 通过Lambda表达式获取到每个题目的输入用例
        List<String> inputList = judgeCasesList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        // 调用沙箱
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(submitCode)
                .language(submitLanguage)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        List<String> outputList = executeCodeResponse.getOutputList();
        // 5、根据沙箱的执行结果，设置题目的判题状态和信息
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCasesList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);
        judgeContext.setExecuteMessage(executeCodeResponse.getMessage());
        // 进行判题
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);

        // 6、修改判题结果
        updateQuestionSubmit = new QuestionSubmit();
        updateQuestionSubmit.setId(questionSubmitId);
        updateQuestionSubmit.setSubmitState(QuestionSubmitStatusEnum.SUCCEED.getValue());
        updateQuestionSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        updateState = questionFeignClient.updateQuestionSubmitById(updateQuestionSubmit);
        if (!updateState) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新失败");
        }
        // 再次查询数据库，返回最新提交信息
        QuestionSubmit questionSubmitResult = questionFeignClient.getQuestionSubmitById(questionId);
        Question updateQuestion = questionFeignClient.getQuestionById(questionId);
        Integer acceptedNum = updateQuestion.getAcceptedNum();
        String acceptedNumkey = questionId + acceptedNum.toString();
        synchronized (acceptedNumkey) {
            if (judgeInfo.getMessage().equals(JudgeInfoMessageEnum.ACCEPTED.getValue())) {
                acceptedNum = acceptedNum + 1;
            }
            updateQuestion.setId(questionId);
            updateQuestion.setAcceptedNum(acceptedNum);
            boolean save = questionFeignClient.updateQuestion(updateQuestion);
            if (!save) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "保存数据失败");
            }
        }
        return questionSubmitResult;
    }
}
