package com.stylefeng.guns.core.common.constant.state;

/**
 * 员工在职状态的枚举
 *
 * @author fengshuonan
 * @date 2017年6月1日22:50:11
 */
public enum IsAdmin {

    NOTADMIN(0, "普通员工"),
	ISADMIN(1, "管理员");
    int code;
    String message;

    IsAdmin(int code, String message) {
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
            for (IsAdmin s : IsAdmin.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
