package ue.entity;

public class Relation {
	private String name;
	// HashMap<String,String> properties;
	private String headNodeType;
	private String tailNodeType;
	private String headNodeProName;
	private String headNodeProValue;
	private String tailNodeProName;
	private String tailNodeProValue;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeadNodeType() {
		return headNodeType;
	}

	public void setHeadNodeType(String headNodeType) {
		this.headNodeType = headNodeType;
	}

	public String getTailNodeType() {
		return tailNodeType;
	}

	public void setTailNodeType(String tailNodeType) {
		this.tailNodeType = tailNodeType;
	}

	public String getHeadNodeProName() {
		return headNodeProName;
	}

	public void setHeadNodeProName(String headNodeProName) {
		this.headNodeProName = headNodeProName;
	}

	public String getHeadNodeProValue() {
		return headNodeProValue;
	}

	public void setHeadNodeProValue(String headNodeProValue) {
		this.headNodeProValue = headNodeProValue;
	}

	public String getTailNodeProName() {
		return tailNodeProName;
	}

	public void setTailNodeProName(String tailNodeProName) {
		this.tailNodeProName = tailNodeProName;
	}

	public String getTailNodeProValue() {
		return tailNodeProValue;
	}

	public void setTailNodeProValue(String tailNodeProValue) {
		this.tailNodeProValue = tailNodeProValue;
	}

}
