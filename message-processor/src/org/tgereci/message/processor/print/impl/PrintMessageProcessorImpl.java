package org.tgereci.message.processor.print.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.tgereci.message.model.base.MessageBase;
import org.tgereci.message.processor.print.PrintMessageProcessor;

/**
 * The Class PrintMessageProcessorImpl.
 */
public class PrintMessageProcessorImpl implements PrintMessageProcessor {

	/** The Constant log. */
	static final Logger log = LogManager
			.getLogger(PrintMessageProcessorImpl.class);

	/**
	 * Prints the message to standard output.
	 *
	 * @param message
	 *            the message
	 */
	@Override
	public void printMessage(MessageBase message) {
		log.trace("Message received:");
		log.trace(message);
	}

}
