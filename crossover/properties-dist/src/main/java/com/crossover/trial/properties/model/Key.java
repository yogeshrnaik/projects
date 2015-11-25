package com.crossover.trial.properties.model;

public class Key implements Comparable<Key> {

	private final String key;
	private final String keyWithoutDotUnderscore;

	public Key(String key) {
		this.key = key;
		this.keyWithoutDotUnderscore = key.toLowerCase().replace(".", "#").replace("_", "#");
	}

	public String getKey() {
		return key;
	}

	@Override
	public int compareTo(Key k) {
		return keyWithoutDotUnderscore.toLowerCase().compareTo(k.keyWithoutDotUnderscore.toLowerCase());
	}

	@Override
	public String toString() {
		return "Key [key=" + key + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((keyWithoutDotUnderscore == null) ? 0 : keyWithoutDotUnderscore.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Key other = (Key) obj;
		if (keyWithoutDotUnderscore == null) {
			if (other.keyWithoutDotUnderscore != null)
				return false;
		} else if (!keyWithoutDotUnderscore.equalsIgnoreCase(other.keyWithoutDotUnderscore))
			return false;
		return true;
	}

}
