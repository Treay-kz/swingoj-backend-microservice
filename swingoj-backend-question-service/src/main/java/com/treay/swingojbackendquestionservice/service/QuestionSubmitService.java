package com.treay.swingojbackendquestionservice.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.treay.swingojbackendmodel.model.dto.questionsumbit.QuestionSubmitAddRequest;
import com.treay.swingojbackendmodel.model.dto.questionsumbit.QuestionSubmitQueryRequest;
import com.treay.swingojbackendmodel.model.entity.QuestionSubmit;
import com.treay.swingojbackendmodel.model.entity.User;
import com.treay.swingojbackendmodel.model.vo.QuestionSubmitVO;

/**
 * @author Treay
 * @description 针对表【question_submit(题目提交)】的数据库操作Service
 * @createDate 2023-08-25 17:33:25
 */
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest 提交题目信息
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);
}
