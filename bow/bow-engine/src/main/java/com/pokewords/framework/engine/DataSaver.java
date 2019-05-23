package com.pokewords.framework.engine;


import java.io.Serializable;

public interface DataSaver {

	void set(String key, String value);

	void set(String key, Serializable serializableObject);

	String get(String key);

}
