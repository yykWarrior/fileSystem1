package com.rb.fs.mq;

//import com.rb.service.MailService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rb.fs.entity.User;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * @version v1.0
 * @ClassName: Receiver
 * @Description: TODO
 * @Author: yyk
 * @Date: 2020/4/13 9:28
 */

@Component
public class Receiver {

    //@Autowired
    //private MailService mailService;
    @RabbitHandler
    @RabbitListener(queues = "immediate_queue_test1")
    public void immediateProcess(String message) {
        //int b=1/0;
        //mailService.sendAttachmentsMail("3178797104@qq.com","入库成功","恭喜入库成功","src/main/webapp/code/cu.png");
        System.out.println("Receiver" + message);
    }



}


