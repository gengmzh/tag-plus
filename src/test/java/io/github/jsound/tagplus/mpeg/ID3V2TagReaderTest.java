/*
 * 
 */
package io.github.jsound.tagplus.mpeg;

import io.github.jsound.tagplus.BaseTest;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.nio.channels.FileChannel;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Myron Geng
 * @since 0.0.1
 */
public class ID3V2TagReaderTest extends BaseTest {

	@Test
	public void test_read() throws Exception {
		String audioFile = "/audio/jimolieche.mp3";
		URL url = getClass().getResource(audioFile);

		ID3v2TagReader tagReader = null;
		FileInputStream audioStream = null;
		FileChannel audioChannel = null;
		try {
			File file = new File(url.getPath());
			audioStream = new FileInputStream(file);
			audioChannel = audioStream.getChannel();

			tagReader = new ID3v2TagReader(file.getName(), audioChannel);
			tagReader.read();
		} finally {
			try {
				audioChannel.close();
			} catch (Exception e1) {
				log.error("close file channel failed", e1);
			}
			try {
				audioStream.close();
			} catch (Exception e2) {
				log.error("close file inputStream failed", e2);
			}
		}

		if (tagReader.hasID3v2Tag()) {
			Assert.assertNotNull(tagReader.getID3v2Tag());
		}
		log.info("{}, {}", tagReader.hasID3v2Tag(), tagReader.getID3v2Tag());
	}

}
