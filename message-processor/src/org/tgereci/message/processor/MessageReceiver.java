package org.tgereci.message.processor;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.tgereci.message.model.base.MessageBase;

/**
 * The Class MessageReceiver.
 */
public class MessageReceiver {

	/** The Constant log. */
	static final Logger log = LogManager.getLogger(MessageReceiver.class);

	/** The connection. */
	private Connection connection;

	/** The session. */
	private Session session;

	/** The consumer. */
	private MessageConsumer consumer;

	/**
	 * Instantiates a new message receiver.
	 *
	 * @throws JMSException
	 *             the JMS exception
	 */
	public MessageReceiver() throws JMSException {
		super();
		init();
	}

	/**
	 * Initialize message receiver.
	 *
	 * @throws JMSException
	 *             the JMS exception
	 */
	public void init() throws JMSException {
		// create connection factory
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				Configuration.getInstance().getQueueUrl());
		// create connection
		connection = connectionFactory.createConnection();
		connection.start();
		// create session
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// create destination (queue)
		Destination destination = session.createQueue(Configuration
				.getInstance().getQueueName());
		// create message consumer from session to queue
		consumer = session.createConsumer(destination);
	}

	/**
	 * Close message receiver.
	 */
	public void close() {
		try {
			if (consumer != null) {
				consumer.close();
			}
		} catch (JMSException e) {
			log.error("Exception while closing consumer", e);
		}
		try {
			if (session != null) {
				session.close();
			}
		} catch (JMSException e) {
			log.error("Exception while closing session", e);
		}
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (JMSException e) {
			log.error("Exception while closing connection", e);
		}
	}

	/**
	 * Pull message from queue.
	 *
	 * @return the message base
	 */
	public MessageBase pullMessageFromQueue() {
		MessageBase messageBase = null;
		try {
			// wait for a message
			Message message = consumer.receive();
			// deserialize message
			if (message instanceof ObjectMessage) {
				Serializable messageObject = ((ObjectMessage) message)
						.getObject();
				if (messageObject instanceof MessageBase) {
					messageBase = (MessageBase) messageObject;
				} else {
					log.error("Invalid message in queue: " + message);
				}
			} else {
				log.error("Invalid message in queue: " + message);
			}
		} catch (JMSException e) {
			log.error("Exception while pulling message from queue", e);
		}
		return messageBase;
	}
}
