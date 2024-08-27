package com.treay.swingojbackendjudgeservice.judge.strategy;

import com.treay.swingojbackendmodel.model.codesandbox.JudgeInfo;
import com.treay.swingojbackendmodel.model.dto.question.JudgeCase;
import com.treay.swingojbackendmodel.model.entity.Question;
import com.treay.swingojbackendmodel.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 上下文（用于定义在策略中传递的参数）
 * @author Treay
 */
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private Question question;

    private QuestionSubmit questionSubmit;

    private String executeMessage;

}
