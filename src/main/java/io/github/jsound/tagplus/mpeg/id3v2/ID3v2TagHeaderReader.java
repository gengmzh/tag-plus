/*
 * 
 */
package io.github.jsound.tagplus.mpeg.id3v2;

import io.github.jsound.tagplus.base.Charsets;
import io.github.jsound.tagplus.bean.ID3v2TagHeader;
import io.github.jsound.tagplus.spi.TagException;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ID3v2 tag header reader
 * 
 * @author Myron Geng
 * @since 0.1.0
 */
public class ID3v2TagHeaderReader {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * Create new {@link ID3v2TagHeaderReader}
	 */
	public ID3v2TagHeaderReader() {
	}

	/**
	 * read ID3v2 tag header if it's found, return null if no ID3v2 tag.
	 */
	public ID3v2TagHeader read(FileChannel audioChannel) throws IOException, TagException {
		if (audioChannel == null) {
			throw new NullPointerException("audioChannel is null");
		}

		// read header
		ID3v2TagHeader header = this.readHeader(audioChannel);
		if (header == null) {
			return null;
		}

		// read extended header
		if (header.hasExtendedHeader()) {
			this.readExtendedHeader(audioChannel, header);
		}

		return header;
	}

	/**
	 * read ID3v2 header
	 */
	private ID3v2TagHeader readHeader(FileChannel audioChannel) throws IOException, TagException {
		// read
		ByteBuffer buffer = ByteBuffer.allocate(ID3v2TagHeader.HEADER_SIZE);
		audioChannel.position(0);
		int num = audioChannel.read(buffer);
		if (num < ID3v2TagHeader.HEADER_SIZE) {
			log.debug("no enough room for ID3v2 header");
			return null;
		}

		// parse
		buffer.rewind();
		// ID3v2/file identifier
		byte[] bs = new byte[3];
		buffer.get(bs, 0, 3);
		if (!Arrays.equals(bs, ID3v2TagHeader.ID3V2_IDENTIFIER)) {
			log.debug("no ID3v2/file identifier found");
			return null;
		}
		String id = Charsets.decode(bs);
		// ID3v2 version
		short version = buffer.getShort();
		short major = (short) (version >> 8);
		if (major != 2 && major != 3 && major != 4) {
			throw new TagException("unknown ID3v2 major version " + major);
		}
		// ID3v2 flags
		byte flag = buffer.get();
		// ID3v2 size
		bs = new byte[4];
		buffer.get(bs, 0, 4);
		int bit7 = (bs[0] & 0x80) | (bs[1] & 0x80) | (bs[2] & 0x80) | (bs[3] & 0x80);
		if (bit7 != 0) {
			log.debug("bit 7 of ID3v2 size isn't zero");
		}
		int size = ((bs[0] & 0x7F) << 21) + ((bs[1] & 0x7F) << 14) + ((bs[2] & 0x7F) << 7) + (bs[3] & 0x7F);

		ID3v2TagHeader header = new ID3v2TagHeader().setId(id).setVersion(version).setFlag(flag).setSize(size);
		return header;
	}

	/**
	 * read ID3v2 extended header
	 */
	private void readExtendedHeader(FileChannel audioChannel, ID3v2TagHeader header) throws IOException, TagException {
		if (!header.hasExtendedHeader()) {
			return;
		}
		// read
		ByteBuffer buffer = ByteBuffer.allocate(10);
		int num = audioChannel.position(ID3v2TagHeader.HEADER_SIZE).read(buffer);
		if (num < 10) {
			throw new TagException("no enough room for ID3v2 extended header, while it's expected");
		}

		// parse
		buffer.rewind();
		// Extended header size
		int size = buffer.getInt();
		// Extended Flags
		short flag = buffer.getShort();
		// Size of padding
		int paddingSize = buffer.getInt();
		// CRC
		int CRC = 0;
		boolean hasCRC = ((flag >> 8) & 0x80) > 0;
		if (hasCRC) {
			buffer = ByteBuffer.allocate(4);
			num = audioChannel.read(buffer);
			if (num < 4) {
				throw new TagException("CRC-32 data is missing, while it's expected");
			}
			buffer.rewind();
			CRC = buffer.getInt();
		}

		header.setExtSize(size).setExtFlag(flag).setExtPaddingSize(paddingSize).setExtCRC32Data(CRC);
	}

}
