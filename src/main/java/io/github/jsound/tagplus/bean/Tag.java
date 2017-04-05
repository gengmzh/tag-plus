/**
 * 
 */
package io.github.jsound.tagplus.bean;

import java.io.Serializable;

/**
 * Abstract tag
 * 
 * @author Myron Geng
 * @since 0.1.0
 */
public abstract class Tag<H extends TagHeader, F extends TagFrame> implements Serializable/* , Iterable<T> */{

	private static final long serialVersionUID = -7012897046180999467L;

	private H header;
	private F[] frames;

	/**
	 * Create new {@link Tag}
	 */
	public Tag() {
	}

	/**
	 * @return the header
	 */
	public H getHeader() {
		return header;
	}

	/**
	 * @param header
	 *            the header to set
	 */
	public Tag<H, F> setHeader(H header) {
		this.header = header;
		return this;
	}

	/**
	 * @return the frames
	 */
	public F[] getFrames() {
		return frames;
	}

	/**
	 * @param frames
	 *            the frames to set
	 */
	public Tag<H, F> setFrames(F[] frames) {
		this.frames = frames;
		return this;
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("{\"header\": ").append(getHeader()).append(", \"frames\": ").append(getFrames()).append("}");
		return buf.toString();
	}

}
