/*
 * 
 */
package io.github.jsound.tagplus.bean;

/**
 * ID3v2 header
 * 
 * @author Myron Geng
 * @since 0.1.0
 */
public class ID3v2Header extends TagHeader {

	private static final long serialVersionUID = 664245993523840449L;

	public static final String TYPE = "ID3v2";
	public static final int HEADER_SIZE = 10;

	public static final byte[] ID3V2_IDENTIFIER = new byte[] { 'I', 'D', '3' };

	private String id;
	private short version;
	private byte flag;
	private int size;

	private int extSize;
	private short extFlag;
	private int extPaddingSize;
	private int extCRC32Data;

	public ID3v2Header() {
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public String getIdentifier() {
		return TYPE + "." + getMajorVersion() + "." + getMinorVersion();
	}

	/**
	 * @return size of ID3v2 header, which should be 10 bytes.
	 */
	@Override
	public int getHeaderSize() {
		return HEADER_SIZE;
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
	public ID3v2Header setId(String id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the version
	 */
	public short getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public ID3v2Header setVersion(short version) {
		this.version = version;
		return this;
	}

	/**
	 * @return the flag
	 */
	public byte getFlag() {
		return flag;
	}

	/**
	 * @param flag
	 *            the flag to set
	 */
	public ID3v2Header setFlag(byte flag) {
		this.flag = flag;
		return this;
	}

	/**
	 * @return the size of ID3v2 tag, including the extended header, tag frames and the padding, but excluding the
	 *         header, so it's total tag size -10.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public ID3v2Header setSize(int size) {
		this.size = size;
		return this;
	}

	/**
	 * @return the major version
	 */
	public short getMajorVersion() {
		return (short) (version >> 8);
	}

	/**
	 * @return the minor version
	 */
	public short getMinorVersion() {
		return (short) (version & 0xFF);
	}

	/**
	 * @return true if unsynchronisation is used, otherwise false
	 */
	public boolean isUnsynchronisation() {
		return (flag & 0x80) > 0;
	}

	/**
	 * @return true if the header is followed by an extended header, otherwise false
	 */
	public boolean hasExtendedHeader() {
		return (flag & 0x40) > 0;
	}

	/**
	 * @return true if the tag is in an experimental stage, otherwise false
	 */
	public boolean isExperimental() {
		return (flag & 0x20) > 0;
	}

	/**
	 * @return the extended header size
	 */
	public int getExtSize() {
		return extSize;
	}

	/**
	 * @param extSize
	 *            the extSize to set
	 */
	public ID3v2Header setExtSize(int extSize) {
		this.extSize = extSize;
		return this;
	}

	/**
	 * @return the extFlag
	 */
	public short getExtFlag() {
		return extFlag;
	}

	/**
	 * @param extFlag
	 *            the extFlag to set
	 */
	public ID3v2Header setExtFlag(short extFlag) {
		this.extFlag = extFlag;
		return this;
	}

	/**
	 * @return the extPaddingSize
	 */
	public int getExtPaddingSize() {
		return extPaddingSize;
	}

	/**
	 * @param extPaddingSize
	 *            the extPaddingSize to set
	 */
	public ID3v2Header setExtPaddingSize(int extPaddingSize) {
		this.extPaddingSize = extPaddingSize;
		return this;
	}

	/**
	 * @return true if four bytes of CRC-32 data is appended to the extended header, otherwise false.
	 */
	public boolean hasCRC32() {
		return ((extFlag >> 8) & 0x80) > 0;
	}

	/**
	 * @return the extCRC32Data
	 */
	public int getExtCRC32Data() {
		return extCRC32Data;
	}

	/**
	 * @param extCRC32Data
	 *            the extCRC32Data to set
	 */
	public ID3v2Header setExtCRC32Data(int extCRC32Data) {
		this.extCRC32Data = extCRC32Data;
		return this;
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(getClass().getSimpleName()).append("[");
		buf.append(getId()).append(" ").append(getMajorVersion()).append(".").append(getMinorVersion()).append(" ")
				.append(getFlag()).append(" ").append(getSize());
		buf.append("]");
		return buf.toString();
	}

}
