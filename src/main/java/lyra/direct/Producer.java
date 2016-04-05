package lyra.direct;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import net.jodah.lyra.Connections;
import net.jodah.lyra.config.Config;
import net.jodah.lyra.config.ConnectionConfig;
import net.jodah.lyra.config.RecoveryPolicies;
import net.jodah.lyra.config.RetryPolicies;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    public static final String AA_Q = "AA_Q";
    public static final String BB_Q = "BB_Q";
    public static final String CC_Q = "CC_Q";
    public static final String DD_Q = "DD_Q";

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("publishing  message With Lyra rabbitmq client");

        Config config = new Config().withRecoveryPolicy(RecoveryPolicies.recoverAlways()).withRetryPolicy(RetryPolicies.retryAlways());

        Connection connection= Connections.create(config);

        Channel channel=connection.createChannel();

        channel.exchangeDeclare("directExchane", "direct", true);

        channel.queueDeclare(AA_Q, true, false, false, null);
        channel.queueBind(AA_Q, "directExchane", "AA");

        channel.queueDeclare(BB_Q, true, false, false, null);
        channel.queueBind(BB_Q, "directExchane", "BB");

        channel.queueDeclare(CC_Q, true, false, false, null);
        channel.queueBind(CC_Q, "directExchane", "CC");

        channel.queueDeclare(DD_Q, true, false, false, null);
        channel.queueBind(DD_Q, "directExchane","DD");

        ObjectMapper objectMapper = new ObjectMapper();
        Offer offer=new Offer("ABC","test Offer","test merchant","Dummy Offer","N/A");


       byte[] jsonByteArray= objectMapper.writeValueAsBytes(offer);


        channel.basicPublish("directExchane", "AA", MessageProperties.PERSISTENT_TEXT_PLAIN, jsonByteArray);
        channel.basicPublish("directExchane", "BB", MessageProperties.PERSISTENT_TEXT_PLAIN,jsonByteArray);
        channel.basicPublish("directExchane", "CC", MessageProperties.PERSISTENT_TEXT_PLAIN, jsonByteArray);
        channel.basicPublish("directExchane", "DD", MessageProperties.PERSISTENT_TEXT_PLAIN, jsonByteArray);

        System.out.println("publishing  message done ....");

        channel.close();
        connection.close();


    }
}
