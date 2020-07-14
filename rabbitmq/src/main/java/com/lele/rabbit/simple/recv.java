package com.lele.rabbit.simple;
import com.lele.rabbit.utils.ConnectionUtils;
import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class recv {
    private static final String QUEUE_NAME="test_simple_queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        //建立链接、创建通道
        Connection connections = ConnectionUtils.getConnections();
        
        Channel channel = connections.createChannel();
        //队列声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null)	;
        //定义消费者
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            //获取到达的消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg=new String(body,"utf-8");
                System.out.println("--- recieve the massage --- "+msg);
            }
        };
        //消费队列
        channel.basicConsume(QUEUE_NAME, true, defaultConsumer);
    }
}
