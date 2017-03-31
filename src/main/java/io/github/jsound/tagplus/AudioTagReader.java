/*
 * 
 */
package io.github.jsound.tagplus;

import io.github.jsound.tagplus.bean.Tag;

import java.io.File;

/**
 * Audio tag reader
 * 
 * @author Myron Geng
 * @since 0.0.1
 */
public class AudioTagReader {

	private File audioFile;
	private Tag audioTag;

	/**
	 * Create a new {@link AudioTagReader} for the given audio file
	 * 
	 * @param audioFile
	 *            the given audio file
	 */
	public AudioTagReader(String audioFile) {
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
	}

	public AudioTagReader read() {

		return this;
	}

	public Tag get() {
		return this.audioTag;
	}

}
