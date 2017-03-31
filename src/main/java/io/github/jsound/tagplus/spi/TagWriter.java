/**
 * 
 */
package io.github.jsound.tagplus.spi;

import io.github.jsound.tagplus.bean.Tag;

/**
 * Aduio tag writer
 * 
 * @author Myron Geng
 * @since 0.0.1
 */
public interface TagWriter {

	public void write(String audioFile, Tag tag) throws TagException;

}
