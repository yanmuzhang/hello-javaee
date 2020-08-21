package com;


/**
 * @Author: caojunhao
 * @Date: 2019/6/19 17:44
 * @Version: v1.0
 */
public interface AuthTokenService {

    /**
     *  密码格式验证
     * @param newPassword
     * @return
     */
    Boolean verifyPassword(String newPassword);

    /**
     * 修改密码
     * @param oldPassword 现密码
     * @param newPassword 新密码
     */
    void updatePassword(String oldPassword, String newPassword);


    /**
     * 密码定时修改验证
     * @param username
     * @return
     */
    Boolean verifyPasswordTime(String username);

    /**
     * 记录登陆该系统的账号
     * @param username
     */
    void recordAccount(String username,String cIp);

    /**
     * 判断是否第一次登录该系统
     * @param username
     * @return
     */
    Boolean verifyPasswordFirst(String username);

}
