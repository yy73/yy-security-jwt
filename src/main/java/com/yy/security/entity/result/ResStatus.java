package com.yy.security.entity.result;

public enum ResStatus {

    SYS_100("100", "缺少参数%s"),
    SYS_420("420", "参数格式不对"),
    SYS_200("200", "操作成功"),
    SYS_500("500", "Internal Server Error"),
    SYS_504("504", "请求超时");

    ResStatus(String code, String message){
        this.code = code;
        this.message = message;
    }

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public ResStatus setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResStatus setMessage(String message) {
        this.message = message;
        return this;
    }
}
