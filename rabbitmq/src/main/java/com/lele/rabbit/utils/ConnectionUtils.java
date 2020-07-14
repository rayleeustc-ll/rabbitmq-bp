package com.lele.rabbit.utils;


import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtils {
    public static Connection  getConnections() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.146.128");
        factory.setPort(5672);
        factory.setVirtualHost("/vhost_lele");
        factory.setUsername("lelewang");
        factory.setPassword("lelewang");
        Connection connection =factory.newConnection();
        return connection;
    }
}
