/*
 * 
 */
package io.github.jsound.tagplus.bean;

import java.io.Serializable;

/**
 * Abstract tag header
 * 
 * @author Myron Geng
 * @since 0.1.0
 */
public abstract class TagHeader implements Serializable/* , Iterable<KeyVal<String, Object>> */{

	private static final long serialVersionUID = 5264224187073876775L;

	/**
	 * Create new {@link TagHeader}
	 */
	public TagHeader() {
	}

	/**
	 * @return type of the tag.
	 */
	public abstract String getType();

	/**
	 * @return identifier of the tag, like ID3v1, ID3v2.2, ID3v2.3 etc.
	 */
	public abstract String getIdentifier();

	/**
	 * @return size of the tag header, NOT the tag size.
	 */
	public abstract int getHeaderSize();

}
