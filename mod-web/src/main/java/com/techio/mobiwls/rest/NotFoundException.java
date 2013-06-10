/**
 * 
 */
package com.techio.mobiwls.rest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * @author slavikos
 *
 */
public class NotFoundException extends WebApplicationException {

	public NotFoundException(String message, Throwable cause) {
		super(cause, Response.Status.NOT_FOUND);
		
	}

	public NotFoundException() {
		super(Response.Status.NOT_FOUND);
	}

}
