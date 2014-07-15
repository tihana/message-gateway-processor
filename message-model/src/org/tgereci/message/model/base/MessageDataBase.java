package org.tgereci.message.model.base;

import java.io.Serializable;

/**
 * The Class MessageDataBase.
 */
public abstract class MessageDataBase implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6588209692253239109L;

	/** The MX. */
	private Integer mMX;

	/** The PermGen. */
	private Integer mPermGen;

	/**
	 * Gets the MX.
	 *
	 * @return the MX
	 */
	public Integer getmMX() {
		return mMX;
	}

	/**
	 * Sets the MX.
	 *
	 * @param mMX
	 *            the new MX
	 */
	public void setmMX(Integer mMX) {
		this.mMX = mMX;
	}

	/**
	 * Gets the PermGen.
	 *
	 * @return the PermGen
	 */
	public Integer getmPermGen() {
		return mPermGen;
	}

	/**
	 * Sets the PermGen.
	 *
	 * @param mPermGen
	 *            the new PermGen
	 */
	public void setmPermGen(Integer mPermGen) {
		this.mPermGen = mPermGen;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getName() + " [mMX=" + mMX + ", mPermGen=" + mPermGen
				+ "]";
	}

}
