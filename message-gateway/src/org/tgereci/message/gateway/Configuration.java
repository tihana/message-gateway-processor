package org.tgereci.message.gateway;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * The Class Configuration.
 */
public class Configuration {

	/** The Constant log. */
	static final Logger log = LogManager.getLogger(Configuration.class);

	/** The queue URL. */
	private String queueUrl;

	/** The queue name. */
	private String queueName;

	/** The instance. */
	private static Configuration instance = null;

	/**
	 * Instantiates a new configuration.
	 */
	private Configuration() {
		loadProperties();
	}

	/**
	 * Gets the single instance of Configuration.
	 *
	 * @return single instance of Configuration
	 */
	public static Configuration getInstance() {
		if (instance == null) {
			instance = new Configuration();
		}
		return instance;
	}

	/**
	 * Load properties.
	 */
	private void loadProperties() {
		InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream("config.properties");
		Properties properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			log.error("Error while reading config.properties file", e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					log.error("Error while closing config.properties file", e);
				}
			}
		}

		// set queue URL
		queueUrl = properties.getProperty("queue.url");
		if (queueUrl == null || queueUrl.trim().isEmpty()) {
			// set to default if empty
			queueUrl = ActiveMQConnectionFactory.DEFAULT_BROKER_URL;
		}
		// set queue name
		queueName = properties.getProperty("queue.name");
		if (queueName == null || queueName.trim().isEmpty()) {
			// set to default if empty
			queueName = "messageGatewayQueue";
		}
	}

	/**
	 * Gets the queue URL.
	 *
	 * @return the queue URL
	 */
	public String getQueueUrl() {
		return queueUrl;
	}

	/**
	 * Gets the queue name.
	 *
	 * @return the queue name
	 */
	public String getQueueName() {
		return queueName;
	}

}
