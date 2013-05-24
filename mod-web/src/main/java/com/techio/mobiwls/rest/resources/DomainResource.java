package com.techio.mobiwls.rest.resources;

import javax.management.AttributeNotFoundException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.directory.NoSuchAttributeException;
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

	public static final String EMPTY_STRING = "";

	public static final String JSON_CONTENT_TYPE = "application/json";

	protected MBeanServer lookupDomainRuntimeMBean() throws NamingException {
		InitialContext ctx = new InitialContext();
		return (MBeanServer) ctx.lookup("java:comp/env/jmx/domainRuntime");
	}

	protected Object getAttribute(MBeanServer mbeanServer,
			ObjectName objectName, String attributeName) {

		try {
			return mbeanServer.getAttribute(objectName, attributeName);
		} catch (AttributeNotFoundException nsa) {
			return null;
		} catch (Exception ex) {
			throw new RuntimeException(String.format(
					"failed to retrieve attribute '%s' from objectName '%s'",
					attributeName, objectName), ex);
		}

	}

	protected String getStringAttribute(MBeanServer mbeanServer,
			ObjectName objectName, String attributeName) {
		Object attribute = getAttribute(mbeanServer, objectName, attributeName);
		if (attribute != null)
			return String.valueOf(attribute);
		else
			return null;

	}

	protected ServerInfo retrieveServerInfo(MBeanServer mbeanServer,
			ObjectName serverMBean) {
		try {
			ServerInfo info = new ServerInfo();
			info.setListenAddress(getStringAttribute(mbeanServer, serverMBean,
					"ListenAddress"));
			info.setName(getStringAttribute(mbeanServer, serverMBean, "Name"));
			return info;
		} catch (Exception ex) {
			throw new RuntimeException("failed to construct server info", ex);
		}
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

			ObjectName domainMBean = (ObjectName) domainRuntimeServer
					.getAttribute(service, "DomainConfiguration");

			info.setDomainVersion(String.valueOf(domainRuntimeServer
					.getAttribute(domainMBean, "DomainVersion")));
			info.setConfigurationVersion(String.valueOf(domainRuntimeServer
					.getAttribute(domainMBean, "ConfigurationVersion")));
			info.setConsoleEnabled(Boolean
					.valueOf((Boolean) domainRuntimeServer.getAttribute(
							domainMBean, "ConsoleEnabled")));
			info.setConsolePath(String.valueOf(domainRuntimeServer
					.getAttribute(domainMBean, "ConsoleContextPath")));
			info.setLastModificationTime(Long
					.valueOf((Long) domainRuntimeServer.getAttribute(
							domainMBean, "LastModificationTime")));
			info.setName(String.valueOf(domainRuntimeServer.getAttribute(
					domainMBean, "Name")));

			// fetch domain server
			ObjectName serverMBeans[] = (ObjectName[]) domainRuntimeServer
					.getAttribute(domainMBean, "Servers");
			for (ObjectName mbean : serverMBeans) {
				info.getServers().add(
						getStringAttribute(domainRuntimeServer, mbean, "Name"));
			}

			// fetch domain clusters
			ObjectName clusterMBeans[] = (ObjectName[]) domainRuntimeServer
					.getAttribute(domainMBean, "Clusters");
			for (ObjectName mbean : clusterMBeans) {
				info.getClusters().add(
						getStringAttribute(domainRuntimeServer, mbean, "Name"));
			}

			// fetch domain jms servers
			ObjectName jmsMBeans[] = (ObjectName[]) domainRuntimeServer
					.getAttribute(domainMBean, "JMSServers");
			for (ObjectName mbean : jmsMBeans) {
				info.getJmsServers().add(
						getStringAttribute(domainRuntimeServer, mbean, "Name"));
			}

			// fetch domain datasources
			ObjectName datasourceMBeans[] = (ObjectName[]) domainRuntimeServer
					.getAttribute(domainMBean, "JDBCSystemResources");
			for (ObjectName mbean : datasourceMBeans) {
				info.getDataSources().add(
						getStringAttribute(domainRuntimeServer, mbean, "Name"));
			}

			// fetch domain datasources
			ObjectName appDeploymentsMBeans[] = (ObjectName[]) domainRuntimeServer
					.getAttribute(domainMBean, "AppDeployments");
			for (ObjectName mbean : appDeploymentsMBeans) {
				info.getDeployments().add(
						getStringAttribute(domainRuntimeServer, mbean, "Name"));
			}

			// fetch domain's servers
			// ObjectName serverMBeans[] = (ObjectName[]) domainRuntimeServer
			// .getAttribute(domainMBean, "Servers");
			// for(ObjectName serverMBean : serverMBeans) {
			// info.addServerInfo(retrieveServerInfo(domainRuntimeServer,
			// serverMBean));
			// }
			return info;
		} catch (Exception ex) {
			throw new WebApplicationException(ex);
		}
	}
}
