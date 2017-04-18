/*
 * 
 */
package io.github.jsound.tagplus.base;

import java.nio.charset.Charset;

/**
 * Charset utils
 * 
 * @author Myron Geng
 * @since 0.1.0
 */
public final class Charsets {

	private Charsets() {
	}

	public static final Charset CHARSET_ISO_8859_1 = Charset.forName("ISO-8859-1");
	public static final Charset CHARSET_UTF_16 = Charset.forName("UTF-16");

	/**
	 * get Charset by the type.
	 * 
	 * @param type
	 *            charset type, 0 indicating ISO-8859-1, 1 indicating UTF-16
	 */
	public static Charset getCharset(short type) {
		if (type == 0) {
			return CHARSET_ISO_8859_1;
		} else if (type == 1) {
			return CHARSET_UTF_16;
		} else {
			throw new IllegalArgumentException("unknown charset type " + type);
		}
	}

	/**
	 * decode bytes using ISO-8859-1.
	 */
	public static String decode(byte[] bytes) {
		return new String(bytes, CHARSET_ISO_8859_1);
	}

	/**
	 * decode bytes using the specified Charset type.
	 * 
	 * @param bytes
	 * @param type
	 *            charset type, 0 indicating ISO-8859-1, 1 indicating UTF-16
	 */
	public static String decode(byte[] bytes, short type) {
		return decode(bytes, getCharset(type));
	}

	/**
	 * decode bytes using the specified {@link java.nio.charset.Charset Charset}.
	 * 
	 * @param bytes
	 * @param encoding
	 */
	public static String decode(byte[] bytes, Charset encoding) {
		return new String(bytes, encoding);
	}

}
