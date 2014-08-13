package org.tgereci.message.gateway;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import mockit.Mock;
import mockit.MockUp;

import org.apache.activemq.pool.PooledConnectionFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tgereci.message.model.base.MessageBase;

/**
 * The Class MessageSenderTest.
 */
public class MessageSenderTest {

	/** The message base. */
	private static MessageBase messageBase;

	/**
	 * Sets the up before class.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// instantiate mocked pooled connection factory
		new MockedPooledConnectionFactory();
	}

	/**
	 * Returns message for protocol version 1.0.0.
	 *
	 * @return the message100
	 */
	private static org.tgereci.message.model.v100.Message getMessage100() {
		org.tgereci.message.model.v100.Message message100 = new org.tgereci.message.model.v100.Message();
		message100.setMessageId(123);
		message100.setTimestamp(123456789);
		message100.setProtocolVersion("1.0.0");
		message100
				.setMessageData(new org.tgereci.message.model.v100.MessageData());
		message100.getMessageData().setmMX(322556);
		message100.getMessageData().setmPermGen(324682);
		return message100;
	}

	/**
	 * The Class MockedPooledConnectionFactory.
	 */
	public static class MockedPooledConnectionFactory extends
			MockUp<PooledConnectionFactory> {

		/**
		 * Creates the connection.
		 *
		 * @return the connection
		 * @throws JMSException
		 *             the JMS exception
		 */
		@Mock
		public Connection createConnection() throws JMSException {
			return new MockedConnection().getMockInstance();
		}
	}

	/**
	 * The Class MockedConnection.
	 */
	public static class MockedConnection extends MockUp<Connection> {

		/**
		 * Start.
		 *
		 * @throws JMSException
		 *             the JMS exception
		 */
		@Mock
		public void start() throws JMSException {
		}

		/**
		 * Creates the session.
		 *
		 * @param transacted
		 *            the transacted
		 * @param acknowledgeMode
		 *            the acknowledge mode
		 * @return the session
		 * @throws JMSException
		 *             the JMS exception
		 */
		@Mock
		public Session createSession(boolean transacted, int acknowledgeMode)
				throws JMSException {
			return new MockedSession().getMockInstance();
		}
	}

	/**
	 * The Class MockedSession.
	 */
	public static class MockedSession extends MockUp<Session> {

		/**
		 * Creates the queue.
		 *
		 * @param queueName
		 *            the queue name
		 * @return the queue
		 * @throws JMSException
		 *             the JMS exception
		 */
		@Mock
		public Queue createQueue(String queueName) throws JMSException {
			return null;
		}

		/**
		 * Creates the producer.
		 *
		 * @param destination
		 *            the destination
		 * @return the message producer
		 * @throws JMSException
		 *             the JMS exception
		 */
		@Mock
		public MessageProducer createProducer(Destination destination)
				throws JMSException {
			return new MockedMessageProducer().getMockInstance();
		}

		/**
		 * Creates the object message.
		 *
		 * @param object
		 *            the object
		 * @return the object message
		 * @throws JMSException
		 *             the JMS exception
		 */
		@Mock(invocations = 1)
		public ObjectMessage createObjectMessage(final Serializable object)
				throws JMSException {
			assertEquals(messageBase, object);
			MockUp<ObjectMessage> mockedMessage = new MockUp<ObjectMessage>() {
				@Mock
				public Serializable getObject() throws JMSException {
					return object;
				}
			};
			return mockedMessage.getMockInstance();
		}
	}

	/**
	 * The Class MockedMessageProducer.
	 */
	public static class MockedMessageProducer extends MockUp<MessageProducer> {

		/**
		 * Send.
		 *
		 * @param message
		 *            the message
		 * @throws JMSException
		 *             the JMS exception
		 */
		@Mock(invocations = 1)
		public void send(Message message) throws JMSException {
			assertNotNull(message);
			assertTrue(message instanceof ObjectMessage);
			assertEquals(messageBase, ((ObjectMessage) message).getObject());
		}
	}

	/**
	 * Test method for
	 * {@link org.tgereci.message.gateway.MessageSender#addMessageToQueue(org.tgereci.message.model.base.MessageBase)}
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testAddMessageToQueue() throws Exception {
		messageBase = getMessage100();
		MessageSender.getInstance().addMessageToQueue(messageBase);
	}
}
