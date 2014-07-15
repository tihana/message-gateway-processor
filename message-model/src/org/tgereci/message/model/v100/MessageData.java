package org.tgereci.message.model.v100;

import org.tgereci.message.model.base.MessageDataBase;

/**
 * The Class MessageData.
 */
public class MessageData extends MessageDataBase {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7537086095640336010L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tgereci.message.model.base.MessageDataBase#toString()
	 */
	@Override
	public String toString() {
		return getClass().getName() + " [mMX=" + getmMX() + ", mPermGen="
				+ getmPermGen() + "]";
	}
}
