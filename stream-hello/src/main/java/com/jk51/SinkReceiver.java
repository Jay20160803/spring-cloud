package com.jk51;

import com.jk51.messaging.ChannleName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018-02-28
 * 修改记录:
 */
@EnableBinding(ChannleName.class)   //绑定通道
public class SinkReceiver {

    private Logger logger = LoggerFactory.getLogger(SinkReceiver.class);


    @StreamListener(ChannleName.channleName)  //监听通道
    public void receive(Object payload){

        logger.info("Receive: "+payload);
    }
}
