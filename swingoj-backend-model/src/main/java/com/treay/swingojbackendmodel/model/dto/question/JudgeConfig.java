package com.treay.swingojbackendmodel.model.dto.question;

import lombok.Data;

/**
 * 判题配置
 *
 * @author Treay
 */

@Data
public class JudgeConfig {

    /**
     * 时间限制 (ms)
     */
    private Long timeLimit;
    /**
     * 内存限制 (KB)
     */
    private Long memoryLimit;
    /**
     * 堆栈限制 (KB)
     */
    private Long stackLimit;

}
