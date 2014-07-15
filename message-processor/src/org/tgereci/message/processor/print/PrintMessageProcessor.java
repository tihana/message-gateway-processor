package org.tgereci.message.processor.print;

import org.tgereci.message.model.base.MessageBase;

/**
 * The Interface PrintMessageProcessor.
 */
public interface PrintMessageProcessor {

	/**
	 * Prints the message.
	 *
	 * @param message
	 *            the message
	 */
	public void printMessage(MessageBase message);
}
