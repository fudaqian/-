package com.stylefeng.guns.core.common.constant.state;

/**
 * 员工在职状态的枚举
 *
 * @author fengshuonan
 * @date 2017年6月1日22:50:11
 */
public enum StaffState {

    JOB(1, "在职"),
    QUIT(0, "离职");

	int code;
    String message;

    StaffState(int code, String message) {
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
            for (StaffState s : StaffState.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
