package com.lele.rabbit.tx;

import com.lele.rabbit.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TxSender {
    private static String QUEUE_NAME = "test_queue_tx";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnections();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        String msg = "hello tx message";
        try {
            channel.txSelect();
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
     //       int n = 1/0;
            channel.txCommit();
            System.out.println("send tx message : "+msg);
        }catch (Exception e){
            channel.txRollback();
            System.out.println("send message rollback ");
        }
        channel.close();
        connection.close();
    }
}
