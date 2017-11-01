package com.akash.Jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;

import com.akash.service.EmailService;

public class JmsConsumer {

	@Autowired
	private EmailService emailService;

	private static Logger log = Logger.getLogger("JmsConsumer.class");

	Connection connection = null;
	Session session = null;

	public JmsConsumer() {
	}

	public void init() {
		try {
			System.out.println("In INIT");
			log.warn("In INIT");
			this.jmsMailConsumer();
		} catch (Exception ex) {
			System.out.println(ex);
			log.warn(ex);
		}
	}

	public void jmsMailConsumer() throws JMSException {
		Connection connection = null;
		Session session = null;

		System.out.println("in side consumer");
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		try {
			Queue queue = session.createQueue("customerMaildetails");
			MessageConsumer consumer = session.createConsumer(queue);

			consumer.setMessageListener(new MessageListener() {
				public void onMessage(Message msg) {
					try {
						if (!(msg instanceof TextMessage))
							throw new RuntimeException("no text message");

						TextMessage tm = (TextMessage) msg;

						log.warn("TextMessage from ActiveMq" + tm.getText());
						JSONObject jsonObject = new JSONObject(tm.getText());

						log.warn("received email id >>" + jsonObject.getString("user_email"));

						SimpleMailMessage welcomeEmail = new SimpleMailMessage();
						welcomeEmail.setFrom("akashkumarsingh57@gmail.com");
						welcomeEmail.setTo(jsonObject.getString("user_email"));
						welcomeEmail.setSubject("Welcome mail");
						welcomeEmail.setText("hii " + jsonObject.getString("user_name") + "Welcome to fundoonote");
						emailService.sendEmail(welcomeEmail);
					} catch (JMSException e) {
						System.err.println("Error reading message");
					}
				}

			});
			MessageListener ml = consumer.getMessageListener();
			log.warn("Message Listener " + ml);

		} catch (JMSException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void stop() throws Throwable {
		System.out.println("In Stop Block....");
		if (session != null) {
			session.close();
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	protected void finalize() throws Throwable {
		this.stop();
	}

}
