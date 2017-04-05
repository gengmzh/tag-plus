/*
 * 
 */
package io.github.jsound.tagplus.mpeg.id3v2;

import io.github.jsound.tagplus.base.Charsets;
import io.github.jsound.tagplus.bean.ID3v2TagFrame;
import io.github.jsound.tagplus.spi.TagException;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ID3v2 Frame Reader
 * 
 * @author Myron Geng
 * @since 0.1.0
 */
public class ID3v2TagFrameReader {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	// body readers
	private ID3v2TagTextualFrameBodyReader textualFrameBodyReader = new ID3v2TagTextualFrameBodyReader();

	/**
	 * Create new {@link ID3v2TagFrameReader}
	 */
	public ID3v2TagFrameReader() {
	}

	/**
	 * read a ID3v2 frame from the buffered data, return null if no frame found.
	 */
	public ID3v2TagFrame read(ByteBuffer data) throws TagException {
		// check
		if (data == null) {
			throw new NullPointerException("buffer is null");
		}
		if (data.remaining() < ID3v2TagFrame.HEADER_SIZE) {
			log.warn("no more frames, since remaining data is lt {}", ID3v2TagFrame.HEADER_SIZE);
			return null;
		}

		// read
		// frame header
		ID3v2TagFrame frame = this.readHeader(data);
		if (frame == null) {
			return null;
		}
		// TODO check the frame id and size

		// frame body
		this.readBody(data, frame);

		return frame;
	}

	private ID3v2TagFrame readHeader(ByteBuffer buffer) {
		// Frame ID
		byte[] bs = new byte[4];
		buffer.get(bs, 0, 4);
		String id = Charsets.decode(bs);
		// Frame Size
		int size = buffer.getInt();
		// Frame Flag
		short flag = buffer.getShort();

		ID3v2TagFrame frame = new ID3v2TagFrame().setId(id).setSize(size).setFlag(flag);

		// header additions
		int additionSize = 0;
		// decompressed size
		if (frame.isCompressed()) {
			additionSize += 4;
			frame.setDecompressedSize(buffer.getInt());
		}
		// encryption method
		if (frame.isEncrypted()) {
			additionSize += 1;
			frame.setEncryptionMethod(buffer.get());
		}
		// group identifier
		if (frame.isGrouping()) {
			additionSize += 1;
			frame.setGroupIdentifier(buffer.get());
		}
		frame.setAdditionSize(additionSize);

		return frame;
	}

	private void readBody(ByteBuffer buffer, ID3v2TagFrame frame) throws TagException {
		String frameId = frame.getId();
		int size = frame.getSize() - frame.getAdditionSize();

		// read
		// textual
		if (textualFrameBodyReader.isSupported(frameId)) {
			ID3v2TagFrame.TextualFrameBody body = textualFrameBodyReader.readFrameBody(buffer, size);
			frame.setFrameBody(body);
		}
		// otherwise
		else {
			throw new TagException("no suitable frame body reader found for " + frameId);
		}
	}

}
