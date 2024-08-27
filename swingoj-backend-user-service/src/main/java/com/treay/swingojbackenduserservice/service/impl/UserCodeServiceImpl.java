package com.treay.swingojbackenduserservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.treay.swingojbackendcommon.common.ErrorCode;
import com.treay.swingojbackendcommon.exception.BusinessException;
import com.treay.swingojbackendcommon.exception.ThrowUtils;
import com.treay.swingojbackendmodel.model.entity.UserCode;
import com.treay.swingojbackenduserservice.mapper.UserCodeMapper;
import com.treay.swingojbackenduserservice.service.UserCodeService;
import org.springframework.stereotype.Service;

/**
 * @author Treay
 * @description 针对表【user_code(用户)】的数据库操作Service实现
 */
@Service
public class UserCodeServiceImpl extends ServiceImpl<UserCodeMapper, UserCode>
        implements UserCodeService {

    @Override
    public UserCode getUserCodeByUserId(long userId) {
        if (userId < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<UserCode> wrapper = new QueryWrapper<>();
        wrapper.eq("userId", userId);
        UserCode userCode = this.getOne(wrapper);
        ThrowUtils.throwIf(userCode == null, ErrorCode.NULL_ERROR, "此用户不存在");
        return userCode;
    }
}




