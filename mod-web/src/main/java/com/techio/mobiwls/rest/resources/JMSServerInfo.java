/**
 * 
 */
package com.techio.mobiwls.rest.resources;

/**
 * @author slavikos
 *
 */
public class JMSServerInfo {
	
	/**
	 * An alphanumeric name for this jms server instance. (Spaces are not valid.)
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
		return "JMSServerInfo [name=" + name + "]";
	}

	
	
}
