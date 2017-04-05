/**
 * 
 */
package io.github.jsound.tagplus.spi;

import io.github.jsound.tagplus.bean.Tag;
import io.github.jsound.tagplus.bean.TagFrame;
import io.github.jsound.tagplus.bean.TagHeader;

import java.io.File;

/**
 * Abstract tag reader
 * 
 * @author Myron Geng
 * @since 0.0.1
 */
public abstract class TagReader<T extends Tag<?, ?>> {

	protected File audioFile;

	/**
	 * Create a new {@link TagReader} for the given audio file
	 * 
	 * @param audioFile
	 *            the given audio file
	 */
	public TagReader(String audioFile) {
		if (audioFile == null) {
			throw new IllegalArgumentException("arg audioFile is null");
		}
		this.audioFile = new File(audioFile);
		if (!this.audioFile.exists()) {
			throw new IllegalStateException("audio file " + audioFile + " doesn't exist");
		}
		if (!this.audioFile.isFile()) {
			throw new IllegalStateException("audio file " + audioFile + " isn't a real file");
		}
		if (!this.audioFile.canRead()) {
			throw new IllegalStateException("audio file " + audioFile + " cann't be read");
		}
	}

	public abstract TagReader read() throws TagException;

	public abstract Tag get();

}
