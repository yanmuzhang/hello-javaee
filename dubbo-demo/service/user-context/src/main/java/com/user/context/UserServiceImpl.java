package com.user.context;

import com.alibaba.dubbo.config.annotation.Service;
import com.user.interfaces.UserInterface;

/**
 * @author: zhangchao
 * @time: 2018-12-28 16:10
 **/
@Service(timeout = 5000)
public class UserServiceImpl implements UserInterface {


    @Override
    public String getUserInfo(String id) {
        return String.format("userinfo[%s]",id);
    }
}
