package com.lele.rabbit.asynConfirm;

import com.lele.rabbit.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

public class Sender {
    private static String QUEUE_NAME = "test_queue_confirm1";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnections();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.confirmSelect();
        //final SortedSet<Long> confirmSet = Collections.synchronizedNavigableSet(new TreeSet<Long>());
        final SortedSet<Long> confirmSet =  Collections.synchronizedNavigableSet(new TreeSet<Long>());
        channel.addConfirmListener(new ConfirmListener(){
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                if (multiple) {
                    System.out.println("---handleAck-------mutiple----");
                    confirmSet.headSet(deliveryTag + 1).clear();
                } else {
                    System.out.println("---handleAck-------mutiple---false");
                    confirmSet.remove(deliveryTag);
                }

            }
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                if (multiple) {
                    System.out.println("---handleNack-------mutiple----");
                    confirmSet.headSet(deliveryTag + 1).clear();
                } else {
                    System.out.println("---handleNack-------mutiple---false");
                    confirmSet.remove(deliveryTag);
                }
            }
        });

        String msg = "hello confirm msg";
        while (true) {
            long seqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            confirmSet.add(seqNo);
        }

    }
}
