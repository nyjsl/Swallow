package org.nyjsl.swallow.http.okhttp;

/**
 * Created by weix01 on 2015-07-20.
 */
public class HttpException extends  Exception {
    private String status ;
    private String error;
    private String msg;

    public String getError() {
        return error;
    }

    public void setErro(String error) {
        this.error = error;
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

    @Override
    public String toString() {
        return "HttpException{" +
                "status='" + status + '\'' +
                ", erros='" + error + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
