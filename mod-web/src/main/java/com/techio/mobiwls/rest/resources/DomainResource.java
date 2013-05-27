package com.techio.mobiwls.rest.resources;

import java.lang.reflect.Method;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import com.sun.jersey.spi.resource.Singleton;

@Path("/domain")
@Singleton
public class DomainResource extends BaseResource {

	private static final ObjectName domainRuntimeServiceMBeanObjectName;

	private static final ObjectName runtimeServiceMBeanObjectName;

	// Initializing the object name for DomainRuntimeServiceMBean
	// so it can be used throughout the class.
	static {
		try {
			domainRuntimeServiceMBeanObjectName = new ObjectName(
					"com.bea:Name=DomainRuntimeService,Type=weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean");
		} catch (MalformedObjectNameException e) {
			throw new AssertionError(e.getMessage());
		}

		try {
			runtimeServiceMBeanObjectName = new ObjectName(
					"com.bea:Name=RuntimeService,Type=weblogic.management.mbeanservers.runtime.RuntimeServiceMBean");
		} catch (MalformedObjectNameException e) {
			throw new AssertionError(e.getMessage());
		}
	}

	protected MBeanServer lookupDomainRuntimeServiceMBean()
			throws NamingException {
		return (MBeanServer) InitialContext
				.doLookup("java:comp/env/jmx/domainRuntime");
	}

	protected MBeanServer lookupRuntimeServiceMBean() throws NamingException {
		return (MBeanServer) InitialContext
				.doLookup("java:comp/env/jmx/runtime");
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

	protected HealthStatusOverview constructDomainHealthOverview() {
		try {
			MBeanServer domainRuntimeServiceMBean = lookupDomainRuntimeServiceMBean();
			ObjectName[] serverRuntimeMBeans = (ObjectName[]) domainRuntimeServiceMBean
					.getAttribute(domainRuntimeServiceMBeanObjectName,
							"ServerRuntimes");
			HealthStatusOverview returnValue = new HealthStatusOverview();
			/*
			 * to avoid linking with weblogic.jar (at least for now) use
			 * reflection to get the value of the weblogic.health.HealthState
			 * instance
			 */
			@SuppressWarnings("rawtypes")
			Class clazz = Class.forName("weblogic.health.HealthState");
			@SuppressWarnings("unchecked")
			Method getStateMethod = clazz.getMethod("getState");
			for (ObjectName serverMBean : serverRuntimeMBeans) {
				Object healthStatus = domainRuntimeServiceMBean.getAttribute(
						serverMBean, "HealthState");
				String serverName = getStringAttribute(
						domainRuntimeServiceMBean, serverMBean, "Name");
				Integer state = (Integer) getStateMethod.invoke(healthStatus);
				switch (state.intValue()) {
				case HealthState.HEALTH_CRITICAL:
					returnValue.getCriticalServers().add(serverName);
					break;
				case HealthState.HEALTH_FAILED:
					returnValue.getFailedServers().add(serverName);
					break;
				case HealthState.HEALTH_OK:
					returnValue.getHealthyServers().add(serverName);
					break;
				case HealthState.HEALTH_OVERLOADED:
					returnValue.getOverloadedServers().add(serverName);
					break;
				case HealthState.HEALTH_WARN:
					returnValue.getWarningServers().add(serverName);
					break;
				}
			}

			return returnValue;

		} catch (Exception ex) {
			throw new RuntimeException(
					"failed to construct health status overview instance", ex);
		}
	}

	protected DomainInfo constructDomainInfo() {

		try {
			MBeanServer domainRuntimeServer = lookupDomainRuntimeServiceMBean();
			ObjectName domainMBean = (ObjectName) domainRuntimeServer
					.getAttribute(domainRuntimeServiceMBeanObjectName,
							"DomainConfiguration");

			DomainInfo returnValue = new DomainInfo();
			returnValue.setDomainVersion(String.valueOf(domainRuntimeServer
					.getAttribute(domainMBean, "DomainVersion")));
			returnValue.setConfigurationVersion(String
					.valueOf(domainRuntimeServer.getAttribute(domainMBean,
							"ConfigurationVersion")));
			returnValue.setConsoleEnabled(Boolean
					.valueOf((Boolean) domainRuntimeServer.getAttribute(
							domainMBean, "ConsoleEnabled")));
			returnValue.setConsolePath(String.valueOf(domainRuntimeServer
					.getAttribute(domainMBean, "ConsoleContextPath")));

			returnValue.setLastModificationTime(Long
					.valueOf((Long) domainRuntimeServer.getAttribute(
							domainMBean, "LastModificationTime")));
			returnValue.setName(String.valueOf(domainRuntimeServer
					.getAttribute(domainMBean, "Name")));

			// fetch domain server
			ObjectName serverMBeans[] = (ObjectName[]) domainRuntimeServer
					.getAttribute(domainMBean, "Servers");
			for (ObjectName mbean : serverMBeans) {
				returnValue.getServers().add(
						getStringAttribute(domainRuntimeServer, mbean, "Name"));
			}

			// fetch domain clusters
			ObjectName clusterMBeans[] = (ObjectName[]) domainRuntimeServer
					.getAttribute(domainMBean, "Clusters");
			for (ObjectName mbean : clusterMBeans) {
				returnValue.getClusters().add(
						getStringAttribute(domainRuntimeServer, mbean, "Name"));
			}

			// fetch domain jms servers
			ObjectName jmsMBeans[] = (ObjectName[]) domainRuntimeServer
					.getAttribute(domainMBean, "JMSServers");
			for (ObjectName mbean : jmsMBeans) {
				returnValue.getJmsServers().add(
						getStringAttribute(domainRuntimeServer, mbean, "Name"));
			}

			// fetch domain datasources
			ObjectName datasourceMBeans[] = (ObjectName[]) domainRuntimeServer
					.getAttribute(domainMBean, "JDBCSystemResources");
			for (ObjectName mbean : datasourceMBeans) {
				returnValue.getDataSources().add(
						getStringAttribute(domainRuntimeServer, mbean, "Name"));
			}

			// fetch domain datasources
			ObjectName appDeploymentsMBeans[] = (ObjectName[]) domainRuntimeServer
					.getAttribute(domainMBean, "AppDeployments");
			for (ObjectName mbean : appDeploymentsMBeans) {
				returnValue.getDeployments().add(
						getStringAttribute(domainRuntimeServer, mbean, "Name"));
			}

			// fetch domain's servers
			// ObjectName serverMBeans[] = (ObjectName[])
			// domainRuntimeServer
			// .getAttribute(domainMBean, "Servers");
			// for(ObjectName serverMBean : serverMBeans) {
			// info.addServerInfo(retrieveServerInfo(domainRuntimeServer,
			// serverMBean));
			// }

			/* compute the hash from the toString */
			returnValue
					.setVersion(convertByteToHexString(computeHash(returnValue
							.toString())));
			return returnValue;
		} catch (Exception ex) {
			throw new RuntimeException(
					"failed to construct domainInfo instance", ex);
		}

	}

	/**
	 * MWLS-2 : Return the domain configuration version
	 * 
	 * @return
	 */
	@GET
	@Produces({ JSON_CONTENT_TYPE })
	@Path("/version")
	public ResourceVersion getDomainInfoVersion() {
		DomainInfo info = getDomainInfo();
		return new ResourceVersion(DomainInfo.class.getName(),
				info.getVersion());
	}

	/**
	 * MWLS-2 : Returns a high level view of the domain.
	 * 
	 * @return
	 */
	@GET
	@Produces({ JSON_CONTENT_TYPE })
	public DomainInfo getDomainInfo() {

		/* the return value */
		DomainInfo returnValue = null;

		/* check if we have the domain info in cache */

		Cache cache = cacheManager.getCache(MEMORY_CACHE);
		Element element = cache.get(DomainInfo.CACHE_KEY);
		if (element != null) {
			returnValue = (DomainInfo) element.getObjectValue();
		} else {
			/* cache miss */
			try {
				returnValue = constructDomainInfo();

				/* store the domain info into the cache */
				cache.put(new Element(DomainInfo.CACHE_KEY, returnValue));
			} catch (Exception ex) {
				throw new WebApplicationException(ex);
			}
		}

		return returnValue;
	}

	/**
	 * MWLS-1 : Retrieve domain health overview
	 * 
	 * @return
	 */
	@GET
	@Produces({ JSON_CONTENT_TYPE })
	@Path("/health")
	public HealthStatusOverview getHealthOverview() {
		/* the return value */
		HealthStatusOverview returnValue = null;

		/* check if we have the HealthStatusOverview in cache */

		Cache cache = cacheManager.getCache(MEMORY_CACHE);
		Element element = cache.get(HealthStatusOverview.CACHE_KEY);
		if (element != null) {
			returnValue = (HealthStatusOverview) element.getObjectValue();
		} else {
			/* cache miss */
			try {
				returnValue = constructDomainHealthOverview();

				/* store the domain info into the cache */
				cache.put(new Element(HealthStatusOverview.CACHE_KEY,
						returnValue));
			} catch (Exception ex) {
				throw new WebApplicationException(ex);
			}
		}

		return returnValue;
	}
}
