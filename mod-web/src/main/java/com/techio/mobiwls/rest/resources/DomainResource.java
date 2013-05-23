package com.techio.mobiwls.rest.resources;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

import com.sun.jersey.spi.resource.Singleton;

@Path("/domain")
@Singleton
public class DomainResource {

	private static final ObjectName service;

	// Initializing the object name for DomainRuntimeServiceMBean
	// so it can be used throughout the class.
	static {
		try {
			service = new ObjectName(
					"com.bea:Name=DomainRuntimeService,Type=weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean");
		} catch (MalformedObjectNameException e) {
			throw new AssertionError(e.getMessage());
		}
	}

	public static final String JSON_CONTENT_TYPE = "application/json";

	protected MBeanServer lookupDomainRuntimeMBean() throws NamingException {
		InitialContext ctx = new InitialContext();
		return (MBeanServer) ctx.lookup("java:comp/env/jmx/domainRuntime");
	}

	@GET
	@Produces({ JSON_CONTENT_TYPE })
	public DomainInfo getDomainInfo() {
		MBeanServer domainRuntimeServer = null;
		try {
			domainRuntimeServer = lookupDomainRuntimeMBean();
		} catch (NamingException ex) {
			throw new WebApplicationException();
		}

		try {
			DomainInfo info = new DomainInfo();
			
			ObjectName domainMBean = (ObjectName)domainRuntimeServer.getAttribute(service, "DomainConfiguration");
			info.setDomainVersion(String.valueOf(domainRuntimeServer.getAttribute(domainMBean, "DomainVersion")));
			info.setConfigurationVersion(String.valueOf(domainRuntimeServer.getAttribute(domainMBean, "ConfigurationVersion")));
			info.setConsoleEnabled(Boolean.valueOf((Boolean)domainRuntimeServer.getAttribute(domainMBean, "ConsoleEnabled")));
			info.setConsolePath(String.valueOf(domainRuntimeServer.getAttribute(domainMBean, "ConsoleContextPath")));
			info.setLastModificationTime(Long.valueOf((Long)domainRuntimeServer.getAttribute(domainMBean, "LastModificationTime")));
			info.setName(String.valueOf(domainRuntimeServer.getAttribute(domainMBean, "Name")));
			return info;
		} catch (Exception ex) {
			throw new WebApplicationException(ex);
		}
	}
}
