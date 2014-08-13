package org.tgereci.message.processor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import mockit.Mock;
import mockit.MockUp;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tgereci.message.model.base.MessageBase;

/**
 * The Class MessageReceiverTest.
 */
public class MessageReceiverTest {

	/** The mocked object. */
	private static Serializable mockedObject;

	/**
	 * Sets the up before class.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// instantiate mocked connection factory
		new MockedConnectionFactory();
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
	 * Returns message for protocol version 1.0.1.
	 *
	 * @return the message101
	 */
	private static org.tgereci.message.model.v101.Message getMessage101() {
		org.tgereci.message.model.v101.Message message101 = new org.tgereci.message.model.v101.Message();
		message101.setMessageId(234);
		message101.setTimestamp(234567890);
		message101.setProtocolVersion("1.0.1");
		message101
				.setMessageData(new org.tgereci.message.model.v101.MessageData());
		message101.getMessageData().setmMX(451956);
		message101.getMessageData().setmPermGen(514233);
		message101.getMessageData().setmOldGen(254789);
		return message101;
	}

	/**
	 * Returns message for protocol version 2.0.0.
	 *
	 * @return the message200
	 */
	private static org.tgereci.message.model.v200.Message getMessage200() {
		org.tgereci.message.model.v200.Message message200 = new org.tgereci.message.model.v200.Message();
		message200.setMessageId(234);
		message200.setTimestamp(345678901);
		message200.setProtocolVersion("2.0.0");
		message200.setPayload(new org.tgereci.message.model.v200.MessageData());
		message200.getPayload().setmMX(451236);
		message200.getPayload().setmPermGen(628974);
		message200.getPayload().setmOldGen(354865);
		message200.getPayload().setmYoungGen(514978);
		return message200;
	}

	/**
	 * The Class MockedConnectionFactory.
	 */
	public static class MockedConnectionFactory extends
			MockUp<ActiveMQConnectionFactory> {

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
		 * Creates the consumer.
		 *
		 * @param destination
		 *            the destination
		 * @return the message consumer
		 * @throws JMSException
		 *             the JMS exception
		 */
		@Mock
		public MessageConsumer createConsumer(Destination destination)
				throws JMSException {
			return new MockedConsumer().getMockInstance();
		}
	}

	/**
	 * The Class MockedConsumer.
	 */
	public static class MockedConsumer extends MockUp<MessageConsumer> {

		/**
		 * Receive.
		 *
		 * @return the message
		 */
		@Mock(invocations = 1)
		public Message receive() {
			MockUp<ObjectMessage> mockedMessage = new MockUp<ObjectMessage>() {
				@Mock
				public Serializable getObject() throws JMSException {
					return mockedObject;
				}
			};
			return mockedMessage.getMockInstance();
		}
	};

	/**
	 * Test method for
	 * {@link org.tgereci.message.processor.MessageReceiver.MessageReceiver#pullMessageFromQueue()}
	 * <br>
	 * message for protocol version 1.0.0
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testPullMessage100FromQueue() throws Exception {
		mockedObject = getMessage100();
		MessageBase pulledMessage = new MessageReceiver()
				.pullMessageFromQueue();
		assertEquals(mockedObject, pulledMessage);
	}

	/**
	 * Test method for
	 * {@link org.tgereci.message.processor.MessageReceiver.MessageReceiver#pullMessageFromQueue()}
	 * <br>
	 * message for protocol version 1.0.1
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testPullMessage101FromQueue() throws Exception {
		mockedObject = getMessage101();
		MessageBase pulledMessage = new MessageReceiver()
				.pullMessageFromQueue();
		assertEquals(mockedObject, pulledMessage);
	}

	/**
	 * Test method for
	 * {@link org.tgereci.message.processor.MessageReceiver.MessageReceiver#pullMessageFromQueue()}
	 * <br>
	 * message for protocol version 2.0.0
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testPullMessage200FromQueue() throws Exception {
		mockedObject = getMessage200();
		MessageBase pulledMessage = new MessageReceiver()
				.pullMessageFromQueue();
		assertEquals(mockedObject, pulledMessage);
	}

	/**
	 * Test method for
	 * {@link org.tgereci.message.processor.MessageReceiver.MessageReceiver#pullMessageFromQueue()}
	 * <br>
	 * invalid message object
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testPullInvalidMessageFromQueue() throws Exception {
		mockedObject = "Invalid message";
		MessageBase pulledMessage = new MessageReceiver()
				.pullMessageFromQueue();
		assertNull(pulledMessage);
	}
}
