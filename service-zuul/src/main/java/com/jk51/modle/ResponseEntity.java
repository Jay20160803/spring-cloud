package com.jk51.modle;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-02-04
 * 修改记录:
 */
public class ResponseEntity {

    private int code;
    private String mesage;
    private Object data;


    private ResponseEntity(){

    }

    public static ResponseEntity success(){
        return success(null);
    }


    public static ResponseEntity success(Object data){
        ResponseEntity result = new ResponseEntity();
        result.setCode(200);
        result.setMesage("success");
        result.setData(data);
        return result;
    }

    public static ResponseEntity fail(int statusCode,String mesage){
        ResponseEntity result = new ResponseEntity();
        result.setCode(statusCode);
        result.setMesage(mesage);
        return result;
    }


    private int getCode() {
        return code;
    }

    private void setCode(int code) {
        this.code = code;
    }

    private String getMesage() {
        return mesage;
    }

    private void setMesage(String mesage) {
        this.mesage = mesage;
    }

    private Object getData() {
        return data;
    }

    private void setData(Object data) {
        this.data = data;
    }
}
