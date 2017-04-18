/*
 * 
 */
package io.github.jsound.tagplus.mpeg.id3v2;

import io.github.jsound.tagplus.base.Charsets;
import io.github.jsound.tagplus.bean.ID3v2TagFrame;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * ID3v2 tag textual frame body reader
 * 
 * @author Myron Geng
 * @since 0.1.0
 */
class ID3v2TagUFIDFrameBodyReader extends ID3v2TagFrameBodyReader<ID3v2TagFrame.UFIDFrameBody, byte[]> {

	@Override
	public boolean isSupported(String frameId) {
		if (frameId == null) {
			throw new NullPointerException("frameId is null");
		}
		return "UFID".equals(frameId);
	}

	@Override
	Charset getCharset(ByteBuffer data) {
		return Charsets.CHARSET_ISO_8859_1;
	}

	@Override
	public ID3v2TagFrame.UFIDFrameBody readFrameBody(ByteBuffer data, int size) {
		// Owner identifier
		String owner = readTerminatedString(data, size);

		// Text encoding
		byte encoding = data.get();
		// Information
		byte[] bs = new byte[size - 1];
		data.get(bs);
		String content = Charsets.decode(bs, (short) encoding);

		ID3v2TagFrame.TextualFrameBody body = new ID3v2TagFrame.TextualFrameBody();
		body.setEncoding(encoding).setContent(content);
		return body;
	}

}