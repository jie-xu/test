package com.xgh.common;

/**
 * Created by DELL on 2018/9/17.
 */
public class Response {

    private String status;
    private String msg;
    private Object data;


    public Response(){ }

    public Response(String status, String msg, Object data) {
        super();
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 获取数据成功
     **/
    public static Response successResponse(Object data) {
        return new Response(BaseConstants.CommonCode.SUCCESS_CODE, BaseConstants.CommonMessage.SUCCESS_MESSAGE, data);
    }

    public static Response successResponse() {
        return new Response(BaseConstants.CommonCode.SUCCESS_CODE, BaseConstants.CommonMessage.SUCCESS_MESSAGE, "");
    }

    public static Response response(String status, String msg, Object data) {
        return new Response(status, msg, data);
    }

    public static Response response(String status) {
        return new Response(status, "", "");
    }

    public static Response response(String status, String msg) {
        return new Response(status, msg, "");
    }

    public static Response fail(String msg) {
        return new Response(BaseConstants.CommonCode.FAILED_CODE, msg, null);
    }

    public static Response success(String msg) {
        return new Response(BaseConstants.CommonCode.SUCCESS_CODE, msg, null);
    }

    public static Response fail(String msg, Object data) {
        return new Response(BaseConstants.CommonCode.FAILED_CODE, msg, data);
    }

    public static Response success(String msg, Object data) {
        return new Response(BaseConstants.CommonCode.SUCCESS_CODE, msg, data);
    }

}
