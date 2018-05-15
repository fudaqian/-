package com.stylefeng.guns.core.common.constant.state;

/**
 * 员工在职状态的枚举
 *
 * @author fengshuonan
 * @date 2017年6月1日22:50:11
 */
public enum UserIsFollow {

    FOLLOW(1, "关注"),
    UNFOLLOW(0, "未关注");
    int code;
    String message;

    UserIsFollow(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String valueOf(Integer status) {
        if (status == null) {
            return "";
        } else {
            for (UserIsFollow s : UserIsFollow.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
