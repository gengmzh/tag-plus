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
class ID3v2TagTextualFrameBodyReader extends ID3v2TagFrameBodyReader<ID3v2TagFrame.TextualFrameBody, String> {

	@Override
	public boolean isSupported(String frameId) {
		if (frameId == null) {
			throw new NullPointerException("frameId is null");
		}
		// Header for 'Text information frame', ID: "T000" - "TZZZ", excluding "TXXX"
		if (frameId.charAt(0) == 'T' && !"TXXX".equals(frameId)) {
			return true;
		}
		return false;
	}

	/**
	 * @return Text encoding of this textual frame.
	 */
	@Override
	Charset getCharset(ByteBuffer data) {
		// TODO decompress, decrypt
		// Text encoding
		data.mark();
		byte encoding = data.get();
		data.reset();
		return Charsets.getCharset((short) encoding);
	}

	@Override
	public ID3v2TagFrame.TextualFrameBody readFrameBody(ByteBuffer data, int size) {
		// TODO decompress, decrypt
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