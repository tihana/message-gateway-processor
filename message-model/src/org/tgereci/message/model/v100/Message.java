package org.tgereci.message.model.v100;

import org.tgereci.message.model.base.MessageBase;

/**
 * The Class Message.
 */
public class Message extends MessageBase {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3685642873296311102L;

	/** The message data. */
	private MessageData messageData;

	/**
	 * Gets the message data.
	 *
	 * @return the message data
	 */
	public MessageData getMessageData() {
		return messageData;
	}

	/**
	 * Sets the message data.
	 *
	 * @param messageData
	 *            the new message data
	 */
	public void setMessageData(MessageData messageData) {
		this.messageData = messageData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tgereci.message.model.base.MessageBase#toString()
	 */
	@Override
	public String toString() {
		return getClass().getName() + " [messageId=" + getMessageId()
				+ ", timestamp=" + getTimestamp() + ", protocolVersion="
				+ getProtocolVersion() + ", messageData=" + messageData + "]";
	}

}
