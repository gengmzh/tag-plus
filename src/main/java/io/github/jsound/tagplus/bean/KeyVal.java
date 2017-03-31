/*
 * 
 */
package io.github.jsound.tagplus.bean;

import java.io.Serializable;

/**
 * @author Myron Geng
 * @since 0.1.0
 */
public class KeyVal<K, V> implements Serializable {

	private static final long serialVersionUID = -2756655443599614820L;

	private K key;
	private V value;

	/**
	 * Create new {@link KeyVal}.
	 */
	public KeyVal() {
	}

	/**
	 * Create new {@link KeyVal} with specified key and value.
	 */
	public KeyVal(K key, V value) {
		super();
		this.key = key;
		this.value = value;
	}

	/**
	 * @return the key
	 */
	public K getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public KeyVal<K, V> setKey(K key) {
		this.key = key;
		return this;
	}

	/**
	 * @return the value
	 */
	public V getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public KeyVal<K, V> setValue(V value) {
		this.value = value;
		return this;
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(getClass().getSimpleName()).append("[key=").append(getKey()).append(", value=").append(getValue())
				.append("]");
		return buf.toString();
	}

}
