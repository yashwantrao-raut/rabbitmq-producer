package fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static java.time.ZonedDateTime.now;

public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("publishing  message");
        ConnectionFactory connectionFactory=new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection=connectionFactory.newConnection();
        Channel channel=connection.createChannel();

        channel.exchangeDeclare("fanoutExchane", "fanout", true);

        String message="Fanout  : "+now().toEpochSecond();
        channel.basicPublish("fanoutExchane", "", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());

        System.out.println("publishing  message done ....");

        channel.close();
        connection.close();


    }
}
