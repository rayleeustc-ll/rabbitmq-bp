package com.lele.rabbit.confirm;

import com.lele.rabbit.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SendBatch {
    private static String QUEUE_NAME = "test_queue_confirm1";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnections();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //生产者调用将channel设置成confirm模式
        channel.confirmSelect();

        String msg = "hello confirm message";
        for(int i =0 ;i<10;i++){
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        }

        if(!channel.waitForConfirms()) {
            System.out.println("message send failed ");
        } else {
            System.out.println("message send ok");
        }
        channel.close();
        connection.close();
    }
}
