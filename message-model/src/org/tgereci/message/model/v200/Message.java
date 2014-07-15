package org.tgereci.message.model.v200;

import org.tgereci.message.model.base.MessageBase;

/**
 * The Class Message.
 */
public class Message extends MessageBase {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3685642873296311102L;

	/** The payload. */
	private MessageData payload;

	/**
	 * Gets the payload.
	 *
	 * @return the payload
	 */
	public MessageData getPayload() {
		return payload;
	}

	/**
	 * Sets the payload.
	 *
	 * @param payload
	 *            the new payload
	 */
	public void setPayload(MessageData payload) {
		this.payload = payload;
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
				+ getProtocolVersion() + ", payload=" + payload + "]";
	}
}
