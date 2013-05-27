package com.techio.mobiwls.rest.resources;

public class ResourceVersion {
	
	public ResourceVersion(String name, String version) {
		super();
		this.name = name;
		this.version = version;
	}

	private String name;
	
	private String version;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
