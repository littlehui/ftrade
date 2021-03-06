package com.littlehui.ftrade.dto.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Response<T> implements Serializable {

    //成功
    public static final String RESULT_SUCCESS = "success";
    //未登录, 或者roleId获取失败, 典型的处理方式是显示完信息跳转到登录页
    public static final String RESULT_LOGIN = "login";
    //业务规则失败或者业务异常(自己在自己的程序各层抛出的异常), 比如扣款时余额不足
    public static final String RESULT_FAILURE = "failure";
    //表单格式验证失败, 表单业务规则验证失败, 典型的处理方式是显示完信息跳转回表单页
    public static final String RESULT_INPUT = "input";
    //可以预见但是不能处理的异常, 如SQLException, IOException等等
    public static final String RESULT_ERROR = "error";

    private static final long serialVersionUID = -2049439550666128636L;

    // 标识变量
    private String result;
    // 数组, 存放业务失败提示
    private List<String> messages;
    // 数组, 存放系统错误消息
    private List<String> errors;

    private T data;


    /**
     * 获取成功的返回
     *
     * @return
     */
    public static <M> Response<M> getSuccessResponse() {
        Response<M> response = new Response<M>();
        response.setResult(RESULT_SUCCESS);
        return response;
    }

    public static <M> Response<M> getSuccessResponse(M data) {
        Response<M> response = new Response<M>();
        response.setData(data);
        response.setResult(RESULT_SUCCESS);
        return response;
    }


    public static <M> Response<M> getInputFailedResponse() {
        Response<M> response = new Response<M>();
        response.setResult(RESULT_INPUT);
        return response;
    }

    public static <M> Response<M> getInputFailedResponse(String message) {
        Response<M> resp = Response.getInputFailedResponse();
        resp.setMessage(message);
        return resp;
    }

    public static <M> Response<M> getFailedResponse(M data) {
        Response<M> response = new Response<M>();
        response.setData(data);
        response.setResult(RESULT_FAILURE);
        return response;
    }

    public static <M> Response<M> getFailedResponse(String msg) {
        Response<M> resp = getFailedResponse();
        resp.setMessage(msg);
        return resp;
    }

    public static <M> Response<M> getFailedResponse() {
        Response<M> response = new Response<M>();
        response.setResult(RESULT_FAILURE);
        return response;
    }

    public Response() {
        messages = new ArrayList<String>(1);
        errors = new ArrayList<String>(1);
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<String> getMessages() {
        return messages;
    }

    /**
     * 设置业务错误信息
     *
     * @param message
     */
    public void setMessage(String message) {
        this.messages.clear();
        this.messages.add(message);
    }


    public List<String> getErrors() {
        return errors;
    }

    /**
     * 设置系统错误信息
     *
     * @param error
     */
    public void setError(String error) {
        this.errors.clear();
        this.errors.add(error);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
