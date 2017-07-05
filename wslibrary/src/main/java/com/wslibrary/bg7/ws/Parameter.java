package com.wslibrary.bg7.ws;

import java.io.Serializable;

/**
 * Class who wraps the fields x values and trasnfor to an item of json to sent to request
 */
@SuppressWarnings("serial")
public class Parameter<K, V> implements Serializable {
	private K key;
	private V value;
	private static final String OPEN_BRACE = "{";
	private static final String COMMA = ",";
	private static final String PTO = ":";
	private static final String CLOSE_BRACE = "}";

	public Parameter() {
	}

	public Parameter(final K key, final V value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * @return this
	 */
	public Parameter<K, V> setPair(final K key, final V value) {
		this.key = key;
		this.value = value;
		return this;
	}

	/**
	 * @return key
	 */
	public K getKey() {
		return key;
	}

	/**
	 * @param key
	 *            - key a ser setada
	 * @return this
	 */
	public Parameter<K, V> setKey(final K key) {
		this.key = key;
		return this;
	}

	/**
	 * @return value
	 */
	public V getValue() {
		return value;
	}

	/**
	 * @param value
	 *            - valor a ser setado
	 * @return this
	 */
	public Parameter<K, V> setValue(final V value) {
		this.value = value;
		return this;
	}

	/**
	 * Get a json representation of parameter
	 * @return
	 */
	public String toString() {

		StringBuffer str = new StringBuffer("");

		if (value instanceof String) {
			str.append(OPEN_BRACE).append("\"").append(key).append("\"").append(PTO).append("\"").append(value).append("\"").append(CLOSE_BRACE);
		} else {
			str.append(OPEN_BRACE).append("\"").append(key).append("\"").append(PTO).append(value).append(CLOSE_BRACE);
		}

		return str.toString();
	}

	/**
	 * @return {key,val}
	 */
	public String toTuple() {
		return new StringBuffer().append(OPEN_BRACE).append(key).append(COMMA).append(value).append(CLOSE_BRACE).toString();
	}

	/**
	 * Compara somente a key
	 * 
	 * @param o
	 * @return
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		@SuppressWarnings("rawtypes")
		Parameter pair = (Parameter) o;

		if (key != null ? !key.equals(pair.key) : pair.key != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return key != null ? key.hashCode() : 0;
	}
}