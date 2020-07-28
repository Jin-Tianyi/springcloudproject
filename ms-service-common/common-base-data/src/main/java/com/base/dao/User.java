package com.base.dao;

import lombok.*;

import java.util.Date;

/**
 * @author :jty
 * @date :20-7-28
 * @description :
 */
@Setter
@Getter
public class User extends BaseDao {
    /**
     * 主键
     */
    private Integer userId;

    /**
     * 用户账号
     */
    private String userNumber;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话号码
     */
    private String phoneNumber;

    /**
     * 性别
     */
    private String gender;

    /**
     * 头像路径
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 上次修改密码
     */
    private String lastPassword;

    /**
     * 加盐
     */
    private String salt;

    /**
     * 登录ip
     */
    private String loginIp;

    /**
     * 登录时间
     */
    private Date loginTime;
}
