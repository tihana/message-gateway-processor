package org.tgereci.message.model.base;

import java.io.Serializable;

/**
 * The Class MessageBase.
 */
public abstract class MessageBase implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4202599293565097357L;

	/** The message id. */
	private Integer messageId;

	/** The timestamp. */
	private Integer timestamp;

	/** The protocol version. */
	private String protocolVersion;

	/**
	 * Gets the message id.
	 *
	 * @return the message id
	 */
	public Integer getMessageId() {
		return messageId;
	}

	/**
	 * Sets the message id.
	 *
	 * @param messageId
	 *            the new message id
	 */
	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	/**
	 * Gets the timestamp.
	 *
	 * @return the timestamp
	 */
	public Integer getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the timestamp.
	 *
	 * @param timestamp
	 *            the new timestamp
	 */
	public void setTimestamp(Integer timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Gets the protocol version.
	 *
	 * @return the protocol version
	 */
	public String getProtocolVersion() {
		return protocolVersion;
	}

	/**
	 * Sets the protocol version.
	 *
	 * @param protocolVersion
	 *            the new protocol version
	 */
	public void setProtocolVersion(String protocolVersion) {
		this.protocolVersion = protocolVersion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getName() + " [messageId=" + messageId
				+ ", timestamp=" + timestamp + ", protocolVersion="
				+ protocolVersion + "]";
	}

}
