package com.yy.security.entity.result;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author ywl
 * @Date 2021/4/28 10:19
 * @Description 统一返回类型
 */
@Data
@AllArgsConstructor
public class Response {
    //@ApiModelProperty(value = "响应编码", name = "code", example = "200")
    private String code;
    //@ApiModelProperty(value = "返回数据", name = "data", example = "hello world")
    private Object data;
    //@ApiModelProperty(value = "提示信息", name = "message", example = "操作成功")
    private String message;

    /**
     * 自定义编码、返回信息和数据
     *
     * @param code
     * @param object
     * @param object
     * @return message
     */
    public static Response success(String code, Object object, String message) {
        return new Response(code, object, message);
    }

    /**
     * 返回成功信息及结果
     *
     * @param resStatus
     * @param data
     * @return Response
     */
    public static Response success(ResStatus resStatus, Object data) {
        return success(resStatus.getCode(), data, resStatus.getMessage());
    }

    /**
     * 返回成功信息及结果
     *
     * @param resStatus
     * @param data
     * @return Response
     */
    public static Response success(ResStatus resStatus, Object data,String message) {
        return success(resStatus.getCode(), data, message);
    }

    /**
     * 返回成功消息
     *
     * @param data    返回数据
     * @param message 提示信息
     * @return
     */
    public static Response success(Object data, String message) {
        return success(ResStatus.SYS_200, data, message);
    }

    /**
     * 返回成功消息
     *
     * @param data 返回数据
     * @return
     */
    public static Response success(Object data) {
        return success(ResStatus.SYS_200,data);
    }

    /**
     * 返回成功消息，仅编码
     *
     * @return
     */
    public static Response success() {
        return success(new String());
    }

    /**
     * 返回失败消息
     *
     * @param code    编码
     * @param data    返回数据
     * @param message 提示信息
     * @return
     */
    public static Response error(String code, Object data, String message) {
        return new Response(code, data, message);
    }

    /**
     * 返回失败消息
     *
     * @param resStatus
     * @param data
     * @return Response
     */
    public static Response error(ResStatus resStatus, Object data) {
        return error(resStatus.getCode(), data, resStatus.getMessage());
    }

    /**
     * 返回失败消息
     *
     * @param resStatus
     * @param data
     * @return Response
     */
    public static Response error(ResStatus resStatus, Object data,String message) {
        return error(resStatus.getCode(), data, message);
    }

    /**
     * 返回失败消息
     *
     * @param data    返回数据
     * @param message 提示信息
     * @return
     */
    public static Response error(Object data, String message) {
        return error(ResStatus.SYS_500, data, message);
    }

    /**
     * 返回失败信息
     * @param code
     * @param message
     * @return
     */
    public static Response error(String code,String message) {
        return error(code, null, message);
    }

    /**
     * 返回失败信息
     *
     * @param message
     * @return
     */
    public static Response error(String message) {
        return error(ResStatus.SYS_500, null, message);
    }
}
