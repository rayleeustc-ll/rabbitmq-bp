package com.lele.rabbit.confirm;

import com.lele.rabbit.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Recv2 {

    private static String QUEUE_NAME = "test_queue_confirm1";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnections();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.basicConsume(QUEUE_NAME,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("recv [tx]  message : "+ new String(body,"utf-8"));
            }
        });
    }
}
