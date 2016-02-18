package ue.entity;

import java.util.HashMap;
import java.util.Map;

public class Node {
	String name;
	Map<String, String> properties;
	//Map<String, Relation> relations;

	public Node() {
		//relations = new HashMap<String, Relation>();
		properties = new HashMap<String, String>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(HashMap<String, String> properties) {
		this.properties = properties;
	}

	public void addProperty(String key, String value) {
		this.properties.put(key, value);
	}

	public String getProperty(String key) {
		return this.properties.getOrDefault(key, "");
	}


}
