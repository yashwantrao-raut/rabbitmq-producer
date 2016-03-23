package workq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static java.time.ZonedDateTime.now;

public class Producer {

    private static String QNAME="hello";
    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("publishing  message");
        ConnectionFactory connectionFactory=new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection=connectionFactory.newConnection();
        Channel channel=connection.createChannel();
        channel.queueDeclare(QNAME,true,false,false,null);

        String message="Say Hello"+now().toString();
        channel.basicPublish("", "hello", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());

        System.out.println("publishing  message done ....");

        channel.close();
        connection.close();


    }
}
