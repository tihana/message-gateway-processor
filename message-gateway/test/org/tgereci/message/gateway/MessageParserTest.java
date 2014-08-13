package org.tgereci.message.gateway;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.tgereci.message.model.base.MessageBase;

/**
 * The Class MessageParserTest.
 */
public class MessageParserTest {

	/**
	 * Test method for
	 * {@link org.tgereci.message.gateway.MessageParser#deserializeMessage(java.lang.String)}
	 * <br>
	 * JSON input is null or number
	 */
	@Test
	public void testNullOrEmptyMessage() {
		MessageBase message = MessageParser.deserializeMessage(null);
		assertNull("Null JSON input must return null", message);
		message = MessageParser.deserializeMessage("  ");
		assertNull("Empty JSON input must return null", message);
	}

	/**
	 * Test method for
	 * {@link org.tgereci.message.gateway.MessageParser#deserializeMessage(java.lang.String)}
	 * <br>
	 * JSON input doesn't contain protocol version
	 */
	@Test
	public void testNoProtocolVersion() {
		String json = "{\"messageId\":202, \"timestamp\":123456789}";
		MessageBase message = MessageParser.deserializeMessage(json);
		assertNull("JSON input without protocol version must return null",
				message);
	}

	/**
	 * Test method for
	 * {@link org.tgereci.message.gateway.MessageParser#deserializeMessage(java.lang.String)}
	 * <br>
	 * JSON input contains invalid protocol version
	 */
	@Test
	public void testInvalidProtocolVersion() {
		String json = "{\"messageId\":202, \"timestamp\":123456789, \"protocolVersion\":\"1.0\"}";
		MessageBase message = MessageParser.deserializeMessage(json);
		assertNull("JSON input with invalid protocol version must return null",
				message);
	}

	/**
	 * Test method for
	 * {@link org.tgereci.message.gateway.MessageParser#deserializeMessage(java.lang.String)}
	 * <br>
	 * JSON input contains unsupported protocol version
	 */
	@Test
	public void testUnsupportedProtocolVersion() {
		String json = "{\"messageId\":202, \"timestamp\":123456789, \"protocolVersion\":\"1.1.0\"}";
		MessageBase message = MessageParser.deserializeMessage(json);
		assertNull(
				"JSON input with unsupported protocol version must return null",
				message);
	}

	/**
	 * Test method for
	 * {@link org.tgereci.message.gateway.MessageParser#deserializeMessage(java.lang.String)}
	 * <br>
	 * JSON input doesn't conform to schema
	 */
	@Test
	public void testJsonSchema() {
		String json = "{\"timestamp\":125656789, \"protocolVersion\":\"1.0.0\"}";
		MessageBase message = MessageParser.deserializeMessage(json);
		assertNull(
				"JSON input that doesn't conform to schema must return null",
				message);
	}

	/**
	 * Test method for
	 * {@link org.tgereci.message.gateway.MessageParser#deserializeMessage(java.lang.String)}
	 * <br>
	 * JSON input contains invalid fields for specified protocol version
	 */
	@Test
	public void testMessageFields() {
		String json = "{\"messageId\":202, \"timestamp\":125656789, \"protocolVersion\":\"2.0.0\","
				+ " \"messageData\":{\"mMX\":212234, \"mPermGen\":552232}}";
		MessageBase message = MessageParser.deserializeMessage(json);
		assertNull(
				"JSON input with invalid fields for specified protocol version must return null",
				message);
	}

	/**
	 * Test method for
	 * {@link org.tgereci.message.gateway.MessageParser#deserializeMessage(java.lang.String)}
	 * <br>
	 * valid JSON input for protocol version 1.0.0
	 */
	@Test()
	public void testValid100Message() {
		String json = "{\"messageId\":202, \"timestamp\":123456789, \"protocolVersion\":\"1.0.0\","
				+ " \"messageData\":{\"mMX\":212234, \"mPermGen\":552232}}";
		MessageBase messageBase = MessageParser.deserializeMessage(json);
		assertNotNull("Valid JSON input must not return null", messageBase);
		assertTrue(
				"Valid JSON input for protocol version 1.0.0 must return an instance of "
						+ org.tgereci.message.model.v100.Message.class
								.getName(),
				messageBase instanceof org.tgereci.message.model.v100.Message);
		org.tgereci.message.model.v100.Message message = (org.tgereci.message.model.v100.Message) messageBase;
		assertEquals("Message ID must be equal to value set in JSON input",
				Integer.valueOf(202), message.getMessageId());
		assertEquals("Timestamp must be equal to value set in JSON input",
				Integer.valueOf(123456789), message.getTimestamp());
		assertEquals(
				"Protocol version must be equal to value set in JSON input",
				"1.0.0", message.getProtocolVersion());
		org.tgereci.message.model.v100.MessageData messageData = message
				.getMessageData();
		assertNotNull("Message data must not be null", messageData);
		assertTrue(
				"Message data must be an instance of "
						+ org.tgereci.message.model.v100.MessageData.class.getName(),
				messageData instanceof org.tgereci.message.model.v100.MessageData);
		assertEquals("MX must be equal to value set in JSON input",
				Integer.valueOf(212234), messageData.getmMX());
		assertEquals("PermGen must be equal to value set in JSON input",
				Integer.valueOf(552232), messageData.getmPermGen());
	}

	/**
	 * Test method for
	 * {@link org.tgereci.message.gateway.MessageParser#deserializeMessage(java.lang.String)}
	 * <br>
	 * valid JSON input for protocol version 1.0.1
	 */
	@Test()
	public void testValid101Message() {
		String json = "{\"messageId\":367, \"timestamp\":234567890, \"protocolVersion\":\"1.0.1\","
				+ " \"messageData\":{\"mMX\":212234, \"mPermGen\":552232, \"mOldGen\":2567000}}";
		MessageBase messageBase = MessageParser.deserializeMessage(json);
		assertNotNull("Valid JSON input must not return null", messageBase);
		assertTrue(
				"Valid JSON input for protocol version 1.0.1 must return an instance of "
						+ org.tgereci.message.model.v101.Message.class
								.getName(),
				messageBase instanceof org.tgereci.message.model.v101.Message);
		org.tgereci.message.model.v101.Message message = (org.tgereci.message.model.v101.Message) messageBase;
		assertEquals("Message ID must be equal to value set in JSON input",
				Integer.valueOf(367), message.getMessageId());
		assertEquals("Timestamp must be equal to value set in JSON input",
				Integer.valueOf(234567890), message.getTimestamp());
		assertEquals(
				"Protocol version must be equal to value set in JSON input",
				"1.0.1", message.getProtocolVersion());
		org.tgereci.message.model.v101.MessageData messageData = message
				.getMessageData();
		assertNotNull("Message data must not be null", messageData);
		assertTrue(
				"Message data must be an instance of "
						+ org.tgereci.message.model.v101.MessageData.class.getName(),
				messageData instanceof org.tgereci.message.model.v101.MessageData);
		assertEquals("MX must be equal to value set in JSON input",
				Integer.valueOf(212234), messageData.getmMX());
		assertEquals("PermGen must be equal to value set in JSON input",
				Integer.valueOf(552232), messageData.getmPermGen());
		assertEquals("OldGen must be equal to value set in JSON input",
				Integer.valueOf(2567000), messageData.getmOldGen());
	}

	/**
	 * Test method for
	 * {@link org.tgereci.message.gateway.MessageParser#deserializeMessage(java.lang.String)}
	 * <br>
	 * valid JSON input for protocol version 2.0.0
	 */
	@Test()
	public void testValid200Message() {
		String json = "{\"messageId\":915, \"timestamp\":345678901, \"protocolVersion\":\"2.0.0\","
				+ " \"payload\":{\"mMX\":212234, \"mPermGen\":552232, \"mOldGen\":2567000, \"mYoungGen\":145600}}";
		MessageBase messageBase = MessageParser.deserializeMessage(json);
		assertNotNull("Valid JSON input must not return null", messageBase);
		assertTrue(
				"Valid JSON input for protocol version 2.0.0 must return an instance of "
						+ org.tgereci.message.model.v200.Message.class
								.getName(),
				messageBase instanceof org.tgereci.message.model.v200.Message);
		org.tgereci.message.model.v200.Message message = (org.tgereci.message.model.v200.Message) messageBase;
		assertEquals("Message ID must be equal to value set in JSON input",
				Integer.valueOf(915), message.getMessageId());
		assertEquals("Timestamp must be equal to value set in JSON input",
				Integer.valueOf(345678901), message.getTimestamp());
		assertEquals(
				"Protocol version must be equal to value set in JSON input",
				"2.0.0", message.getProtocolVersion());
		org.tgereci.message.model.v200.MessageData payload = message
				.getPayload();
		assertNotNull("Payload must not be null", payload);
		assertTrue("Payload must be an instance of "
				+ org.tgereci.message.model.v200.MessageData.class.getName(),
				payload instanceof org.tgereci.message.model.v200.MessageData);
		assertEquals("MX must be equal to value set in JSON input",
				Integer.valueOf(212234), payload.getmMX());
		assertEquals("PermGen must be equal to value set in JSON input",
				Integer.valueOf(552232), payload.getmPermGen());
		assertEquals("OldGen must be equal to value set in JSON input",
				Integer.valueOf(2567000), payload.getmOldGen());
		assertEquals("YoungGen must be equal to value set in JSON input",
				Integer.valueOf(145600), payload.getmYoungGen());
	}
}
