package io.github.jsound.tagplus.mpeg.id3v2;

/*
 * 
 */
import io.github.jsound.tagplus.base.Charsets;
import io.github.jsound.tagplus.bean.ID3v2TagFrame;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * ID3v2 frame body reader
 * 
 * @author Myron Geng
 * @since 0.1.0
 */
public abstract class ID3v2TagFrameBodyReader<B extends ID3v2TagFrame.FrameBody<T>, T> {

	/**
	 * @return true if the specified frame id is supported, otherwise false.
	 */
	abstract boolean isSupported(String frameId);

	/**
	 * @return charset encoding of the frame
	 */
	abstract Charset getCharset(ByteBuffer data);

	/**
	 * read ID3v2 Frame body
	 * 
	 * @param data
	 *            the buffered frame data
	 * @param size
	 *            the frame body size, excluding the header and additions.
	 */
	abstract B readFrameBody(ByteBuffer data, int size);

	protected byte[] readRawBody(ByteBuffer data, int size) {
		// TODO
		return null;
	}

	/**
	 * read a terminated string.<br/>
	 * Terminated strings are terminated with $00 if encoded with ISO-8859-1 and $00 00 if encoded as unicode.
	 */
	protected String readTerminatedString(ByteBuffer data, int size) {
		Charset encoding = getCharset(data);
		boolean isOneByteNULL = (encoding == Charsets.CHARSET_ISO_8859_1);
		// read
		data.mark();
		int start = data.position();
		int end = -1;
		// one byte termination, $00
		if (isOneByteNULL) {
			while (data.hasRemaining()) {
				if (data.get() == 0x00) {
					end = data.position();
					break;
				}
			}
		}
		// tow bytes termination, $0000
		else {
			boolean flag = false;
			while (data.hasRemaining()) {
				if (data.get() == 0x00) {
					if (flag) {
						end = data.position() - 1;
						break;
					} else {
						flag = true;
					}
				} else {
					flag = false;
				}
			}
		}
		// no termination found, read all the content
		if (end < 0) {
			end = start + size;
		}

		// parse
		if (start > end) {
			return "";
		}
		byte[] bs = new byte[end - start];
		data.reset();
		data.get(bs, 0, bs.length);
		return Charsets.decode(bs, encoding);
	}

}