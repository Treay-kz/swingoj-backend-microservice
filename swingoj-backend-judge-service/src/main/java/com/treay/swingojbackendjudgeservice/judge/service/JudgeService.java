package com.treay.swingojbackendjudgeservice.judge.service;


import com.treay.swingojbackendmodel.model.entity.QuestionSubmit;

/**
 * 判题服务 ：执行代码
 *
 * @author Treay
 * @createTime 2023/8/30 星期三 12:04
 */
public interface JudgeService {
    /**
     * 判题
     *
     * @param questionSubmitId
     * @return
     */
    QuestionSubmit doJudge(long questionSubmitId);
}
