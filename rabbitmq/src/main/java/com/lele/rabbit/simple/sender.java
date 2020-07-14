package com.lele.rabbit.simple;

import com.lele.rabbit.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class sender {
    private static String QUEUE_NAME ="test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connections = ConnectionUtils.getConnections();
        Channel channel = connections.createChannel();
        channel.queueDeclare(QUEUE_NAME,false, false, false, null);
        String msg="hello simple";
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        System.out.println(msg);
        channel.close();
        connections.close();
    }
}
