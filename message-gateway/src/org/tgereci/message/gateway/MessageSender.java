package org.tgereci.message.gateway;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.tgereci.message.model.base.MessageBase;

/**
 * The Class MessageSender.
 */
public class MessageSender {

	/** The Constant log. */
	static final Logger log = LogManager.getLogger(MessageSender.class);

	/** The pooled connection factory. */
	private PooledConnectionFactory pooledConnectionFactory;

	/** The instance. */
	private static MessageSender instance = null;

	/**
	 * Instantiates a new message sender.
	 *
	 * @throws JMSException
	 *             the JMS exception
	 */
	private MessageSender() throws JMSException {
		super();
		init();
	}

	/**
	 * Gets the single instance of MessageSender.
	 *
	 * @return single instance of MessageSender
	 * @throws JMSException
	 */
	public static MessageSender getInstance() throws JMSException {
		if (instance == null) {
			instance = new MessageSender();
		}
		return instance;
	}

	/**
	 * Initialize message sender.
	 *
	 * @throws JMSException
	 *             the JMS exception
	 */
	private void init() throws JMSException {
		// create pooled connection factory
		pooledConnectionFactory = new PooledConnectionFactory(
				new ActiveMQConnectionFactory(Configuration.getInstance()
						.getQueueUrl()));
	}

	/**
	 * Adds message to queue.
	 *
	 * @param message
	 *            the message
	 */
	public void addMessageToQueue(MessageBase message) {
		Connection connection = null;
		Session session = null;
		MessageProducer producer = null;
		try {
			// create connection
			connection = pooledConnectionFactory.createConnection();
			connection.start();
			// create session
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			// create destination (queue)
			Destination destination = session.createQueue(Configuration
					.getInstance().getQueueName());
			// create message producer from session to queue
			producer = session.createProducer(destination);
			// create a message
			ObjectMessage objectMessage = session.createObjectMessage(message);
			// tell the producer to send the message
			producer.send(objectMessage);
		} catch (JMSException e) {
			log.error("Exception while adding message to queue", e);
		} finally {
			// clean up
			try {
				if (producer != null) {
					producer.close();
				}
			} catch (JMSException e) {
				log.error("Exception while closing message producer", e);
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
	}
}
