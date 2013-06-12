/**
 * 
 */
package com.techio.mobiwls.rest;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * @author slavikos
 *
 */
public abstract class AbstractGenericRestException extends
		WebApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	protected AbstractGenericRestException() {
		this(Status.INTERNAL_SERVER_ERROR);
	}

	protected AbstractGenericRestException(Status status) {
		super(status);
	}
	
	protected AbstractGenericRestException(String message, Status status) {
		super(Response.status(status).entity(message).type(MediaType.TEXT_PLAIN).build());
	}

}
