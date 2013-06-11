package com.techio.mobiwls.rest.resources;

import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import com.sun.jersey.spi.resource.Singleton;
import com.techio.mobiwls.jmx.AppDeploymentMBeanWrapper;
import com.techio.mobiwls.jmx.ClusterMBeanWrapper;
import com.techio.mobiwls.jmx.DomainMBeanWrapper;
import com.techio.mobiwls.jmx.DomainRuntimeServiceMBeanWrapper;
import com.techio.mobiwls.jmx.JDBCSystemResourceMBeanWrapper;
import com.techio.mobiwls.jmx.JMSServerMBeanWrapper;
import com.techio.mobiwls.jmx.ServerMBeanWrapper;

@Path("/domain")
@Singleton
public class DomainResource extends BaseResource {

	protected DomainRuntimeServiceMBeanWrapper domainRuntime;

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
			
			DomainMBeanWrapper _domainMBean = domainRuntime.getDomainConfiguration();
			
			DomainInfo returnValue = new DomainInfo();
			returnValue.setDomainVersion(_domainMBean.getDomainVersion());
			returnValue.setConfigurationVersion(_domainMBean.getConfigurationVersion());
			returnValue.setConsoleEnabled(_domainMBean.isConsoleEnabled());
			returnValue.setConsolePath(_domainMBean.getConsoleContextPath());

			returnValue.setLastModificationTime(_domainMBean.getLastModificationTime());
			returnValue.setName(_domainMBean.getName());

			// fetch domain server
			List<ServerMBeanWrapper> serverMBeans = _domainMBean.getServers();
			List<ServerInfo> _serversInfo = returnValue.getServers();
			for (ServerMBeanWrapper mbean : serverMBeans) {
				_serversInfo.add(constructMinimalServerInfo(mbean));
			}

			// fetch domain clusters
			List<ClusterMBeanWrapper> clusterMBeans = _domainMBean.getClusters();
			List<ClusterInfo> _clusterInfo = returnValue.getClusters();
			for (ClusterMBeanWrapper mbean : clusterMBeans) {
				ClusterInfo info = new ClusterInfo();
				info.setName(mbean.getName());
				_clusterInfo.add(info);
			}

			// fetch domain jms servers
			List<JMSServerMBeanWrapper> jmsMBeans = _domainMBean.getJMSServers();
			List<JMSServerInfo> _jmsServerInfo = returnValue.getJmsServers();
			for (JMSServerMBeanWrapper mbean : jmsMBeans) {
				JMSServerInfo info = new JMSServerInfo();
				info.setName(mbean.getName());
				_jmsServerInfo.add(info);
			}

			// fetch domain datasources
			List<JDBCSystemResourceMBeanWrapper> datasourceMBeans = _domainMBean.getJDBCSystemResources();
			List<JDBCResourceInfo> _jdbcResourceInfo = returnValue
					.getDataSources();
			for (JDBCSystemResourceMBeanWrapper mbean : datasourceMBeans) {
				JDBCResourceInfo info = new JDBCResourceInfo();
				info.setName(mbean.getName());
				_jdbcResourceInfo.add(info);
			}

			// fetch domain deployments
			List<AppDeploymentMBeanWrapper> appDeploymentsMBeans = _domainMBean.getAppDeployments();
			List<DeploymentInfo> _deploymentInfo = returnValue.getDeployments();
			for (AppDeploymentMBeanWrapper mbean : appDeploymentsMBeans) {
				DeploymentInfo info = new DeploymentInfo();
				info.setName(mbean.getName());
				_deploymentInfo.add(info);
			}

			
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

	@Override
	@PreDestroy
	protected void destroy() {
		super.destroy();
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

	@Override
	@PostConstruct
	protected void initialize() {
		super.initialize();
		try {
			domainRuntime = new DomainRuntimeServiceMBeanWrapper(
					lookupDomainRuntimeServiceMBean(),
					domainRuntimeServiceMBeanObjectName);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
