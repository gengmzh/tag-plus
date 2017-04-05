/*
 * 
 */
package io.github.jsound.tagplus.bean;

/**
 * ID3v2 frame
 * 
 * @author Myron Geng
 * @since 0.1.0
 */
public class ID3v2TagFrame extends TagFrame {

	private static final long serialVersionUID = -8851699404663971233L;

	/**
	 * ID3v2 Frame header size
	 */
	public static final int HEADER_SIZE = 10;

	// frame header fields
	private String id;
	private int size;
	private short flag;

	private int decompressedSize;
	private byte encryptionMethod;
	private byte groupIdentifier;

	private int additionSize;

	// frame body
	private FrameBody<?> frameBody;

	/**
	 * Create new {@link ID3v2TagFrame}
	 */
	public ID3v2TagFrame() {
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public ID3v2TagFrame setId(String id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the size of the frame, excluding frame header, so it should be total frame size - 10 bytes.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public ID3v2TagFrame setSize(int size) {
		this.size = size;
		return this;
	}

	/**
	 * @return the flag
	 */
	public short getFlag() {
		return flag;
	}

	/**
	 * @param flag
	 *            the flag to set
	 */
	public ID3v2TagFrame setFlag(short flag) {
		this.flag = flag;
		return this;
	}

	/**
	 * @return true if the Frame should be discarded, and false indicating it should be preserved.
	 */
	public boolean isTagAltered() {
		return ((flag >> 8) & 0x80) > 0;
	}

	/**
	 * @return true if the Frame should be discarded, and false indicating it should be preserved.
	 */
	public boolean isFileAltered() {
		return ((flag >> 8) & 0x40) > 0;
	}

	/**
	 * @return true if the frame is intended to be read only, otherwise false.
	 */
	public boolean isReadonly() {
		return ((flag >> 8) & 0x20) > 0;
	}

	/**
	 * @return true if the frame is compressed, otherwise false.
	 */
	public boolean isCompressed() {
		return (flag & 0x80) > 0;
	}

	/**
	 * @return true if the frame is encrypted, otherwise false.
	 */
	public boolean isEncrypted() {
		return (flag & 0x40) > 0;
	}

	/**
	 * @return true if the frame contains group information, otherwise false.
	 */
	public boolean isGrouping() {
		return (flag & 0x20) > 0;
	}

	/**
	 * @return the addition size, including 'decompressed size', 'encryption method' and 'group identifier' fields.
	 */
	public int getAdditionSize() {
		return additionSize;
	}

	/**
	 * @param additionSize
	 *            the additionSize to set
	 */
	public ID3v2TagFrame setAdditionSize(int additionSize) {
		this.additionSize = additionSize;
		return this;
	}

	/**
	 * @return the decompressedSize
	 */
	public int getDecompressedSize() {
		return decompressedSize;
	}

	/**
	 * @param decompressedSize
	 *            the decompressedSize to set
	 */
	public ID3v2TagFrame setDecompressedSize(int decompressedSize) {
		this.decompressedSize = decompressedSize;
		return this;
	}

	/**
	 * @return the encryptionMethod
	 */
	public byte getEncryptionMethod() {
		return encryptionMethod;
	}

	/**
	 * @param encryptionMethod
	 *            the encryptionMethod to set
	 */
	public ID3v2TagFrame setEncryptionMethod(byte encryptionMethod) {
		this.encryptionMethod = encryptionMethod;
		return this;
	}

	/**
	 * @return the groupIdentifier
	 */
	public byte getGroupIdentifier() {
		return groupIdentifier;
	}

	/**
	 * @param groupIdentifier
	 *            the groupIdentifier to set
	 */
	public ID3v2TagFrame setGroupIdentifier(byte groupIdentifier) {
		this.groupIdentifier = groupIdentifier;
		return this;
	}

	// frame body
	/**
	 * @return the frameBody
	 */
	public FrameBody<?> getFrameBody() {
		return frameBody;
	}

	/**
	 * @param frameBody
	 *            the frameBody to set
	 */
	public ID3v2TagFrame setFrameBody(FrameBody<?> frameBody) {
		this.frameBody = frameBody;
		return this;
	}

	/**
	 * Abstract ID3v2 Frame body
	 */
	public static abstract class FrameBody<T> {

		/**
		 * @return
		 */
		abstract T getBody();

	}

	/**
	 * Text information frame body
	 * 
	 * @author Myron Geng
	 * @since 0.1.0
	 */
	public static class TextualFrameBody extends FrameBody<String> {

		private byte encoding;
		private String content;

		public TextualFrameBody() {
		}

		@Override
		String getBody() {
			return content;
		}

		/**
		 * @return the encoding
		 */
		public byte getEncoding() {
			return encoding;
		}

		/**
		 * @param encoding
		 *            the encoding to set
		 */
		public TextualFrameBody setEncoding(byte encoding) {
			this.encoding = encoding;
			return this;
		}

		/**
		 * @return the content
		 */
		public String getContent() {
			return content;
		}

		/**
		 * @param content
		 *            the content to set
		 */
		public TextualFrameBody setContent(String content) {
			this.content = content;
			return this;
		}

	}

}
