package com.techio.mobiwls.rest.resources;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.management.AttributeNotFoundException;
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
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import com.sun.jersey.spi.resource.Singleton;

@Path("/domain")
@Singleton
public class DomainResource {

	private CacheManager cacheManager = null;

	private static final ObjectName service;

	private static final String MEMORY_CACHE = "default-cache";

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

	protected byte[] computeHash(Object object) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(String.valueOf(object).getBytes());
		return md.digest();
	}

	protected String convertByteToHexString(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		return sb.toString();
	}

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

	@PostConstruct
	protected void initialize() {
		cacheManager = CacheManager.getInstance();
		Cache memoryCache = new Cache(MEMORY_CACHE, 5000, false, false, 5, 5);
		cacheManager.addCache(memoryCache);
		System.err.println("post construct!!!!!");
	}

	@PreDestroy
	protected void destroy() {
		if (cacheManager != null) {
			cacheManager.shutdown();
		}
		System.err.println("pre destroy!!!!!");
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

	protected DomainInfo constructDomainInfo() {

		try {
		MBeanServer domainRuntimeServer = null;

			domainRuntimeServer = lookupDomainRuntimeMBean();

		
			DomainInfo returnValue = new DomainInfo();

			ObjectName domainMBean = (ObjectName) domainRuntimeServer
					.getAttribute(service, "DomainConfiguration");

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
						getStringAttribute(domainRuntimeServer, mbean,
								"Name"));
			}

			// fetch domain clusters
			ObjectName clusterMBeans[] = (ObjectName[]) domainRuntimeServer
					.getAttribute(domainMBean, "Clusters");
			for (ObjectName mbean : clusterMBeans) {
				returnValue.getClusters().add(
						getStringAttribute(domainRuntimeServer, mbean,
								"Name"));
			}

			// fetch domain jms servers
			ObjectName jmsMBeans[] = (ObjectName[]) domainRuntimeServer
					.getAttribute(domainMBean, "JMSServers");
			for (ObjectName mbean : jmsMBeans) {
				returnValue.getJmsServers().add(
						getStringAttribute(domainRuntimeServer, mbean,
								"Name"));
			}

			// fetch domain datasources
			ObjectName datasourceMBeans[] = (ObjectName[]) domainRuntimeServer
					.getAttribute(domainMBean, "JDBCSystemResources");
			for (ObjectName mbean : datasourceMBeans) {
				returnValue.getDataSources().add(
						getStringAttribute(domainRuntimeServer, mbean,
								"Name"));
			}

			// fetch domain datasources
			ObjectName appDeploymentsMBeans[] = (ObjectName[]) domainRuntimeServer
					.getAttribute(domainMBean, "AppDeployments");
			for (ObjectName mbean : appDeploymentsMBeans) {
				returnValue.getDeployments().add(
						getStringAttribute(domainRuntimeServer, mbean,
								"Name"));
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
		} catch(Exception ex) {
			throw new RuntimeException("failed to construct domainInfo instance", ex);
		}
		
	
	}
	
	/**
	 * MWLS-2 : Return the domain configuration version
	 * @return
	 */
	@GET
	@Produces({ JSON_CONTENT_TYPE })
	@Path("/version")
	public ResourceVersion getDomainInfoVersion() {
		DomainInfo info = getDomainInfo();
		return new ResourceVersion(DomainInfo.class.getName(), info.getVersion());
	}
	
	/**
	 * MWLS-2 : Returns a high level view of the domain.
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
}
