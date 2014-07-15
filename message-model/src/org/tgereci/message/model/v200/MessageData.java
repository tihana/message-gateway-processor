package org.tgereci.message.model.v200;

import org.tgereci.message.model.base.MessageDataBase;

/**
 * The Class MessageData.
 */
public class MessageData extends MessageDataBase {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7537086095640336010L;

	/** The OldGen. */
	private Integer mOldGen;

	/** The YoungGen. */
	private Integer mYoungGen;

	/**
	 * Gets the OldGen.
	 *
	 * @return the OldGen
	 */
	public Integer getmOldGen() {
		return mOldGen;
	}

	/**
	 * Sets the OldGen.
	 *
	 * @param mOldGen
	 *            the new OldGen
	 */
	public void setmOldGen(Integer mOldGen) {
		this.mOldGen = mOldGen;
	}

	/**
	 * Gets the YoungGen.
	 *
	 * @return the YoungGen
	 */
	public Integer getmYoungGen() {
		return mYoungGen;
	}

	/**
	 * Sets the YoungGen.
	 *
	 * @param mYoungGen
	 *            the new YoungGen
	 */
	public void setmYoungGen(Integer mYoungGen) {
		this.mYoungGen = mYoungGen;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tgereci.message.model.base.MessageDataBase#toString()
	 */
	@Override
	public String toString() {
		return getClass().getName() + " [mMX=" + getmMX() + ", mPermGen="
				+ getmPermGen() + ", mOldGen=" + mOldGen + ", mYoungGen="
				+ mYoungGen + "]";
	}

}
