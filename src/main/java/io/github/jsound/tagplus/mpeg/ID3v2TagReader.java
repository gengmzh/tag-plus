/*
 * 
 */
package io.github.jsound.tagplus.mpeg;

import io.github.jsound.tagplus.bean.ID3v2Header;
import io.github.jsound.tagplus.spi.TagException;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ID3v2 tag reader
 * 
 * @author Myron Geng
 * @since 0.0.1
 */
public class ID3v2TagReader {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	private FileChannel audioChannel;

	private boolean have = false;
	private ID3v2Header header;

	/**
	 * Create new {@link ID3v2TagReader} with the specified audio {@link FileChannel}
	 */
	public ID3v2TagReader(FileChannel audioChannel) {
		if (audioChannel == null) {
			throw new NullPointerException("audioChannel is null");
		}
		this.audioChannel = audioChannel;
	}

	public ID3v2TagReader read() throws IOException, TagException {
		// read head
		have = this.readHeader();
		if (!have) {
			return this;
		}

		// read extended header
		if(header.hasExtendedHeader()){
			this.readExtendedHeader();
		}
		
		// read frames

		return this;
	}

	/**
	 * read ID3v2 header
	 */
	private boolean readHeader() throws IOException, TagException {
		// read
		ByteBuffer buf = ByteBuffer.allocate(ID3v2Header.HEADER_SIZE);
		audioChannel.position(0);
		int num = audioChannel.read(buf);
		if (num < ID3v2Header.HEADER_SIZE) {
			log.debug("no enough room for ID3v2 header");
			return false;
		}

		// parse
		buf.rewind();
		// ID3v2/file identifier
		byte[] id = new byte[3];
		buf.get(id, 0, 3);
		if (!Arrays.equals(id, ID3v2Header.ID3V2_IDENTIFIER)) {
			log.debug("no ID3v2/file identifier found");
			return false;
		}
		// ID3v2 version
		short version = buf.getShort();
		short major = (short) (version >> 8);
		if (major != 2 && major != 3 && major != 4) {
			throw new TagException("unknown ID3v2 major version " + major);
		}
		// ID3v2 flags
		byte flag = buf.get();
		// ID3v2 size
		byte[] bs = new byte[4];
		buf.get(bs, 0, 4);
		int bit7 = (bs[0] & 0x80) | (bs[1] & 0x80) | (bs[2] & 0x80) | (bs[3] & 0x80);
		if (bit7 != 0) {
			log.debug("bit 7 of ID3v2 size isn't zero");
		}
		int size = ((bs[0] & 0x7F) << 21) + ((bs[1] & 0x7F) << 14) + ((bs[2] & 0x7F) << 7) + (bs[3] & 0x7F);

		this.header = new ID3v2Header().setId(new String(id, "ISO-8859-1")).setVersion(version).setFlag(flag)
				.setSize(size);
		return true;
	}

	/**
	 * read ID3v2 extended header
	 */
	private void readExtendedHeader() throws IOException, TagException {
		if (!header.hasExtendedHeader()) {
			return;
		}
		// read
		ByteBuffer buffer = ByteBuffer.allocate(10);
		int num = audioChannel.position(ID3v2Header.HEADER_SIZE).read(buffer);
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
			CRC = buffer.getInt();
		}

		header.setExtSize(size).setExtFlag(flag).setExtPaddingSize(paddingSize).setExtCRC32Data(CRC);
	}

	/**
	 * @return true if the audio has ID3V2 tag
	 */
	public boolean hasID3V2Tag() {
		return have;
	}

	/**
	 * @return the ID3v2 header
	 */
	public ID3v2Header getID3V2Header() {
		return header;
	}

}
