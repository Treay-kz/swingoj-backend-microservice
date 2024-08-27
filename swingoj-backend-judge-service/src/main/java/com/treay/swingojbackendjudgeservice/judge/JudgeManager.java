package com.treay.swingojbackendjudgeservice.judge;

import com.treay.swingojbackendjudgeservice.judge.strategy.DefaultJudgeStrategy;
import com.treay.swingojbackendjudgeservice.judge.strategy.JavaLanguageJudgeStrategy;
import com.treay.swingojbackendjudgeservice.judge.strategy.JudgeContext;
import com.treay.swingojbackendjudgeservice.judge.strategy.JudgeStrategy;
import com.treay.swingojbackendmodel.model.codesandbox.JudgeInfo;
import com.treay.swingojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理（简化调用）
 *
 * @author Treay
 */
@Service
public class JudgeManager {

    /**
     * 执行判题
     *
     * @param judgeContext
     * @return
     */
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getSubmitLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}