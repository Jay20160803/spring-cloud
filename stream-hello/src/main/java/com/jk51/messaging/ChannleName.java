package com.jk51.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.SubscribableChannel;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:  定义通道名称
 * 作者: gaojie
 * 创建日期: 2018-02-28
 * 修改记录:
 */
public interface ChannleName {

    String channleName = "testChannleName";

    @Input(ChannleName.channleName)
    SubscribableChannel channleName();
}
