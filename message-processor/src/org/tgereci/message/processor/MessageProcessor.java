package org.tgereci.message.processor;

import javax.jms.JMSException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.tgereci.message.model.base.MessageBase;
import org.tgereci.message.processor.print.PrintMessageProcessor;
import org.tgereci.message.processor.print.impl.PrintMessageProcessorImpl;

/**
 * The Class MessageProcessor.
 */
public class MessageProcessor {

	/** The Constant log. */
	static final Logger log = LogManager.getLogger(MessageProcessor.class);

	/** The print message processor. */
	private PrintMessageProcessor printMessageProcessor;

	/** The message receiver. */
	private MessageReceiver messageReceiver;

	/**
	 * Instantiates a new message processor.
	 *
	 * @throws JMSException
	 *             the JMS exception
	 */
	public MessageProcessor() throws JMSException {
		super();
		init();
	}

	/**
	 * Initialize message processor.
	 *
	 * @throws JMSException
	 *             the JMS exception
	 */
	private void init() throws JMSException {
		// create print message processor
		String processorClass = Configuration.getInstance().getProcessorClass();
		try {
			Class<?> clazz = Class.forName(processorClass);
			printMessageProcessor = (PrintMessageProcessor) clazz.newInstance();
		} catch (ClassNotFoundException e) {
			log.error("Class " + processorClass
					+ " not found, using default message processor class");
			printMessageProcessor = new PrintMessageProcessorImpl();
		} catch (InstantiationException | IllegalAccessException e) {
			log.error("Class "
					+ processorClass
					+ " could not be instantiated, using default message processor class");
			printMessageProcessor = new PrintMessageProcessorImpl();
		}
		// create message receiver
		messageReceiver = new MessageReceiver();
	}

	/**
	 * Close message processor.
	 */
	public void close() {
		if (messageReceiver != null) {
			messageReceiver.close();
		}
	}

	/**
	 * Pulls message from queue and prints it.
	 *
	 * @return true, if successful
	 */
	public void processMessage() {
		// pull message from queue
		MessageBase message = messageReceiver.pullMessageFromQueue();
		if (message != null) {
			log.debug("Message received, sending to printer: " + message);
			// print message
			printMessageProcessor.printMessage(message);
		}
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		log.debug("Message processor started");
		MessageProcessor messageProcessor = null;
		try {
			messageProcessor = new MessageProcessor();
			while (true) {
				messageProcessor.processMessage();
			}
		} catch (JMSException e) {
			log.error("Exception while creating message processor");
			return;
		} finally {
			if (messageProcessor != null) {
				messageProcessor.close();
			}
		}
	}
}
