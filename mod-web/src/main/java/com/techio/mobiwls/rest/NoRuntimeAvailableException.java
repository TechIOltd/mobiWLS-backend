/**
 * 
 */
package com.techio.mobiwls.rest;

import javax.ws.rs.core.Response.Status;

/**
 * @author slavikos
 *
 */
public class NoRuntimeAvailableException extends AbstractGenericRestException {

	/**
	 * 
	 */
	public NoRuntimeAvailableException() {
		super(Status.NO_CONTENT);
	}

	/**
	 * @param message
	 * @param status
	 */
	public NoRuntimeAvailableException(String message) {
		super(message, Status.NO_CONTENT);
	}

}
