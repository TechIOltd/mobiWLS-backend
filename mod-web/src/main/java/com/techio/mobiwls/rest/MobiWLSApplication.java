/**
 * 
 */
package com.techio.mobiwls.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.techio.mobiwls.rest.resources.DomainResource;

/**
 * @author slavikos
 * 
 */
public class MobiWLSApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> s = new HashSet<Class<?>>();
		s.add(DomainResource.class);
		return s;
	}
}