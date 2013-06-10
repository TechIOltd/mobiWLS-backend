/**
 * 
 */
package com.techio.mobiwls.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.techio.mobiwls.rest.resources.DomainResource;
import com.techio.mobiwls.rest.resources.ServerResource;

/**
 * @author slavikos
 * 
 */
@ApplicationPath("/*")
public class MobiWLSApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> s = new HashSet<Class<?>>();
		s.add(DomainResource.class);
		s.add(ServerResource.class);
		return s;
	}
}
