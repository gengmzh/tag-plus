package io.github.jsound.tagplus.mpeg.id3v2;

/*
 * 
 */
import io.github.jsound.tagplus.bean.ID3v2TagFrame;

import java.nio.ByteBuffer;

/**
 * ID3v2 frame body reader
 * 
 * @author Myron Geng
 * @since 0.1.0
 */
public interface ID3v2TagFrameBodyReader<B extends ID3v2TagFrame.FrameBody<T>, T> {

	/**
	 * @return true if the specified frame id is supported, otherwise false.
	 */
	abstract boolean isSupported(String frameId);

	/**
	 * read ID3v2 Frame body
	 * 
	 * @param data
	 *            the buffered frame data
	 * @param length
	 *            the frame body size, excluding the header and additions.
	 */
	abstract B readFrameBody(ByteBuffer data, int size);

}