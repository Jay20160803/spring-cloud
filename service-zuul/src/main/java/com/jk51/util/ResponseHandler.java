package com.jk51.util;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:  API返回数据处理接口，提供返回状态码和返回的内容
 * 作者: gaojie
 * 创建日期: 2018-02-04
 * 修改记录:
 */
public interface ResponseHandler {


    /**
     * 获取返回的http状态码
     *
     * @return
     */
    int getResponseCode();

    /**
     * 获取返回的内容
     *
     * @param originMessage
     *            默认返回的消息
     * @param e
     *            错误信息
     * @return 处理后返回的信息
     */
    String getResponseBody(String originMessage, Throwable e);

}
