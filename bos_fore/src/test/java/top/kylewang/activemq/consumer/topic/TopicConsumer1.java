package top.kylewang.activemq.consumer.topic;

import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@Service
public class TopicConsumer1 implements MessageListener {

	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		try {
			System.out
					.println("TopicConsumer1:" + textMessage.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
