/**
 * 
 */
package com.techio.mobiwls.rest.resources;

/**
 * @author slavikos
 *
 */
public class JDBCResourceInfo {
	
	/**
	 * An alphanumeric name for this jdbc resource instance. (Spaces are not valid.)
	 */
	private String name;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "JDBCResourceInfo [name=" + name + "]";
	}

	
	
}
