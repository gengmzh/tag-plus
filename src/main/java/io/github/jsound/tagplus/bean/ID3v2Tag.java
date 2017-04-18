/*
 * 
 */
package io.github.jsound.tagplus.bean;

/**
 * ID3v2 tag
 * 
 * @author Myron Geng
 * @since 0.1.0
 */
public class ID3v2Tag extends Tag<ID3v2TagHeader, ID3v2TagFrame> {

	private static final long serialVersionUID = 579667065005518845L;

	/**
	 * Declared ID3v2 frame identifiers
	 * 
	 * @author Myron Geng
	 * @since 0.1.0
	 */
	public static enum FRAME_IDENTIFIER {

		/**
		 * Unique file identifier
		 */
		UFID,
		//
		TPE1;
	};

}
