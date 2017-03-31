/**
 * 
 */
package io.github.jsound.tagplus.mpeg;

import io.github.jsound.tagplus.bean.Tag;
import io.github.jsound.tagplus.spi.TagReader;

import java.io.FileInputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Myron Geng
 * @since 0.0.1
 */
public class MpegTagReader extends TagReader {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	public MpegTagReader(String audioFile) {
		super(audioFile);
	}

	@Override
	public TagReader read() {
		FileInputStream inputStream = null;
		FileChannel inputChannel = null;

		// read
		try {
			// init
			inputStream = new FileInputStream(this.audioFile);
			inputChannel = inputStream.getChannel();

			// read ID3V2
			this.readID3V2Tag(inputChannel);

		} catch (Exception ex) {
			log.error("read MPEG tag failed, audioFile={}", this.audioFile.getPath(), ex);
		} finally {
			try {
				inputChannel.close();
			} catch (Exception e1) {
				log.error("close file channel failed", e1);
			}
			try {
				inputStream.close();
			} catch (Exception e2) {
				log.error("close file inputStream failed", e2);
			}
		}
		return this;
	}

	private void readID3V2Tag(FileChannel inputChannel) {
		

	}

	@Override
	public Tag get() {
		// TODO Auto-generated method stub
		return null;
	}

}
