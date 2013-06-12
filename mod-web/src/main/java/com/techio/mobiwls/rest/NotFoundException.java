/**
 * 
 */
package com.techio.mobiwls.rest;

import javax.ws.rs.core.Response.Status;

/**
 * @author slavikos
 *
 */
public class NotFoundException extends AbstractGenericRestException {

	public NotFoundException() {
		super(Status.NOT_FOUND);
	}

	public NotFoundException(String message) {
		super(message, Status.NOT_FOUND);
	}

	

}
