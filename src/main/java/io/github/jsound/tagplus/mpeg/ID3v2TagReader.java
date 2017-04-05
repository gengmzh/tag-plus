/*
 * 
 */
package io.github.jsound.tagplus.mpeg;

import io.github.jsound.tagplus.bean.ID3v2Tag;
import io.github.jsound.tagplus.bean.ID3v2TagFrame;
import io.github.jsound.tagplus.bean.ID3v2TagHeader;
import io.github.jsound.tagplus.mpeg.id3v2.ID3v2TagFrameReader;
import io.github.jsound.tagplus.mpeg.id3v2.ID3v2TagHeaderReader;
import io.github.jsound.tagplus.spi.TagException;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ID3v2 tag reader
 * 
 * @author Myron Geng
 * @since 0.0.1
 */
public class ID3v2TagReader {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	private ID3v2TagHeaderReader headerReader = new ID3v2TagHeaderReader();
	private ID3v2TagFrameReader frameReader = new ID3v2TagFrameReader();

	private String audioName;
	private FileChannel audioChannel;

	private ID3v2Tag tag;

	/**
	 * Create new {@link ID3v2TagReader} with the specified audio {@link FileChannel}
	 */
	public ID3v2TagReader(String audioName, FileChannel audioChannel) {
		if (audioName == null) {
			throw new NullPointerException("audioName is null");
		}
		if (audioChannel == null) {
			throw new NullPointerException("audioChannel is null");
		}
		this.audioName = audioName;
		this.audioChannel = audioChannel;
	}

	/**
	 * read ID3v2 tag
	 */
	public ID3v2TagReader read() throws IOException, TagException {
		log.debug("[{}] start reading ID3v2 tag", audioName);
		// read header
		this.readHeader();

		// read frames
		if (tag != null) {
			this.readFrames();
		}

		log.debug("[{}] finish reading ID3v2 tag", audioName);
		return this;
	}

	/**
	 * read ID3v2 tag header
	 */
	private void readHeader() throws IOException, TagException {
		audioChannel.position(0);
		ID3v2TagHeader header = headerReader.read(audioChannel);
		if (header != null) {
			tag = new ID3v2Tag();
			tag.setHeader(header);
			log.info("[{}] found ID3v2 tag header {}", audioName, header);
		}
	}

	/**
	 * read ID3v2 tag frames
	 */
	private void readFrames() throws IOException, TagException {
		// read
		ID3v2TagHeader header = tag.getHeader();
		int length = header.getSize();
		int position = ID3v2TagHeader.HEADER_SIZE;
		if (header.hasExtendedHeader()) {
			length -= header.getExtSize();
			position += header.getExtSize();
			// fix by adding CRC-32 data size
			if (header.getExtSize() == 10 && header.hasCRC32()) {
				length -= 4;
				position += 4;
			}
		}
		ByteBuffer buffer = ByteBuffer.allocate(length);
		int num = audioChannel.position(position).read(buffer);
		if (num < length) {
			throw new TagException("no enough room for ID3v2 extended header, while it's expected");
		}

		// parse
		buffer.rewind();
		List<ID3v2TagFrame> frames = new ArrayList<ID3v2TagFrame>();
		do {
			ID3v2TagFrame frame = frameReader.read(buffer);
			if (frame != null) {
				frames.add(frame);
			} else {
				break;
			}
		} while (true);
		tag.setFrames(frames.toArray(new ID3v2TagFrame[frames.size()]));
		log.info("[{}] has total {} ID3v2 tag frames", audioName, frames.size());
	}

	/**
	 * @return true if the audio has ID3V2 tag
	 */
	public boolean hasID3v2Tag() {
		return (tag != null);
	}

	/**
	 * @return the ID3v2 tag
	 */
	public ID3v2Tag getID3v2Tag() {
		return tag;
	}

}
