package org.tgereci.message.gateway;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.tgereci.message.model.base.MessageBase;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.google.common.base.Stopwatch;

/**
 * The Class MessageProcessor.
 */
public class MessageParser {

	/** The Constant log. */
	static final Logger log = LogManager.getLogger(MessageParser.class);

	/**
	 * Deserialize JSON message.
	 *
	 * @param json
	 *            JSON message
	 */
	public static MessageBase deserializeMessage(String json) {
		if (json == null || json.trim().isEmpty()) {
			log.error("Empty JSON message received, skipping processing.");
			return null;
		}
		Stopwatch stopwatch = Stopwatch.createStarted();
		log.debug("Started processing JSON message: " + json);
		try {
			// read JSON message
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonMessage;
			try {
				jsonMessage = mapper.readValue(json, JsonNode.class);
			} catch (IOException e) {
				log.error("Exception while reading JSON message", e);
				return null;
			}
			// get protocol version from JSON message
			String protocolVersion = getProtocolVersion(jsonMessage);
			if (protocolVersion == null) {
				return null;
			}
			// get Message class for specified protocol version
			Class<?> clazz;
			try {
				clazz = Class.forName("org.tgereci.message.model.v"
						+ protocolVersion.replaceAll("\\.", "") + ".Message");
			} catch (ClassNotFoundException e) {
				log.error("Protocol version not supported: " + protocolVersion);
				return null;
			}
			// get JSON schema for specified protocol version
			String schema = getSchema(clazz);
			if (schema == null) {
				log.error("JSON schema not found for protocol version: "
						+ protocolVersion);
				return null;
			}
			// read JSON schema
			JsonNode jsonSchema;
			try {
				jsonSchema = mapper.readValue(schema, JsonNode.class);
			} catch (IOException e) {
				log.error("Exception while reading JSON schema", e);
				return null;
			}
			// validate JSON message using specified JSON schema
			JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
			ProcessingReport report;
			try {
				report = factory.getValidator().validate(jsonSchema,
						jsonMessage);
			} catch (ProcessingException e) {
				log.error(
						"Exception while validating JSON message using specified JSON schema",
						e);
				return null;
			}
			if (report.isSuccess()) {
				// schema validation successful, read JSON message into Message
				// object
				try {
					return (MessageBase) mapper.readValue(json, clazz);
				} catch (IOException e) {
					log.error(
							"Exception while reading JSON message into Message object",
							e);
					return null;
				}
			} else {
				// schema validation unsuccessful, log errors
				log.error("JSON message does not conform to schema. The following errors were found: "
						+ report);
			}
			return null;
		} finally {
			stopwatch = stopwatch.stop();
			log.debug("Finished processing JSON message (duration " + stopwatch
					+ "): " + json);
		}
	}

	/**
	 * Checks and returns protocol version from JSON message. Format for
	 * protocol version is ##.#.#, where # stays for one numeric character.
	 * 
	 * @param jsonMessage
	 *            JSON message
	 * @return protocol version
	 */
	private static String getProtocolVersion(JsonNode jsonMessage) {
		// check if JSON message contains protocol version
		if (jsonMessage == null || !jsonMessage.has("protocolVersion")) {
			log.error("Missing protocol version.");
			return null;
		}
		// check protocol version format
		String protocolVersion = jsonMessage.get("protocolVersion").asText();
		if (protocolVersion == null
				|| !protocolVersion.matches("\\d{1,2}\\.\\d{1}.\\d{1}")) {
			log.error("Invalid protocol version: " + protocolVersion);
			return null;
		}
		return protocolVersion;
	}

	/**
	 * Returns JSON schema (named "schema.json") located in the same package as
	 * given class
	 *
	 * @param clazz
	 *            class for protocol version specified in JSON message
	 * @return JSON schema
	 */
	private static String getSchema(Class<?> clazz) {
		if (clazz == null) {
			return null;
		}
		InputStream inputStream = clazz.getClassLoader().getResourceAsStream(
				clazz.getPackage().getName().replaceAll("\\.", "/")
						+ "/schema.json");
		if (inputStream == null) {
			return null;
		}
		try {
			return IOUtils.toString(inputStream);
		} catch (IOException e) {
			log.error("Exception while reading JSON schema from file", e);
		}
		return null;
	}

}