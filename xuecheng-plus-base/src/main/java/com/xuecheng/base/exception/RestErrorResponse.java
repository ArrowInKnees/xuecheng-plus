package com.xuecheng.base.exception;

import java.io.Serializable;

/**
 * @ClassName RestErrorResponse
 * @Description 错误响应参数包装    和前端约定返回的异常信息模型
 * @Author sdy
 * @Date 2023/8/18 9:42
 */
public class RestErrorResponse implements Serializable {
    private String errMessage;

    public RestErrorResponse(String errMessage){
        this.errMessage= errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}
