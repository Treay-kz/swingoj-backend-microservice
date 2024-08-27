package com.treay.swingojbackendjudgeservice.judge.strategy;


import com.treay.swingojbackendmodel.model.codesandbox.JudgeInfo;

/**
 * 判题策略
 * @author Treay
 */
public interface JudgeStrategy {

    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}