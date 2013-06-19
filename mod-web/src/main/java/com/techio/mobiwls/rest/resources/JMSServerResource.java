/*
 * Copyright (c) 2013 TechIO Ltd (http://techio.com)
 * 
 * (http://techio.com/portfolio/mobile-applications)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.techio.mobiwls.rest.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.naming.InitialContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import com.sun.jersey.spi.resource.Singleton;
import com.techio.mobiwls.datasets.MetricDataSet;
import com.techio.mobiwls.datasets.MetricDataSetHolder;
import com.techio.mobiwls.datasets.MetricDataSetInfo;
import com.techio.mobiwls.datasets.MetricDataSetType;
import com.techio.mobiwls.datasets.MetricSample;
import com.techio.mobiwls.jmx.DomainRuntimeServiceMBeanWrapper;
import com.techio.mobiwls.jmx.JMSServerMBeanWrapper;
import com.techio.mobiwls.jmx.JMSServerRuntimeMBeanWrapper;
import com.techio.mobiwls.rest.NotFoundException;
import com.techio.mobiwls.rest.infoObjects.JMSServerInfo;
import com.techio.mobiwls.rest.infoObjects.JMSServerRuntimeInfo;
import com.techio.mobiwls.rest.infoObjects.MetricsInfo;
import com.techio.mobiwls.rest.infoObjects.ResourceVersion;
import commonj.timers.Timer;
import commonj.timers.TimerListener;
import commonj.timers.TimerManager;

/**
 * @author <a href="mailto:filip@techio.com">Filip Slavik</a>
 * 
 */

@Path("/jmsserver")
@Singleton
public class JMSServerResource extends BaseResource implements TimerListener {

	public static class ServerMetrics {

		/**
		 * The number of bytes received on this JMS server since the last
		 * reset.
		 */
		private MetricDataSetHolder bytesReceivedCountMetrics;
		/**
		 * The current number of bytes stored on this JMS server
		 */
		private MetricDataSetHolder bytesCurrentCountMetrics;
		
		/**
		 * The current number of messages stored on this JMS server
		 */
		private MetricDataSetHolder messagesCurrentCountMetrics;

		/**
		 * The number of messages received on this JMS server since the last
		 * reset.
		 */
		private MetricDataSetHolder messagesReceivedCountMetrics;

		protected Map<String, MetricDataSetHolder> metricIndex = new HashMap<String, MetricDataSetHolder>();
		protected ServerMetrics() {
			super();

			messagesCurrentCountMetrics = new MetricDataSetHolder(
					"messagesCurrentCount", "Current Messages",
					"The current number of messages stored on this JMS server",
					"Time", "Messages", MetricDataSetType.COUNTER_TYPE, 288);
			metricIndex.put(messagesCurrentCountMetrics.getInfo().getId(),
					messagesCurrentCountMetrics);

			messagesReceivedCountMetrics = new MetricDataSetHolder(
					"messagesCurrentCount",
					"Received Messages",
					"The number of messages received on this JMS server since the last reset.",
					"Time", "Messages", MetricDataSetType.COUNTER_TYPE, 288);
			metricIndex.put(messagesReceivedCountMetrics.getInfo().getId(),
					messagesReceivedCountMetrics);
			
			bytesCurrentCountMetrics = new MetricDataSetHolder(
					"bytesCurrentCount", "Current Bytes",
					"The current number of bytes stored on this JMS server",
					"Time", "Bytes", MetricDataSetType.COUNTER_TYPE, 288);
			metricIndex.put(bytesCurrentCountMetrics.getInfo().getId(),
					bytesCurrentCountMetrics);

			bytesReceivedCountMetrics = new MetricDataSetHolder(
					"bytesCurrentCount",
					"Received Bytes",
					"The number of bytes received on this JMS server since the last reset.",
					"Time", "Bytes", MetricDataSetType.COUNTER_TYPE, 288);
			metricIndex.put(bytesReceivedCountMetrics.getInfo().getId(),
					bytesReceivedCountMetrics);

		}

		public MetricDataSetHolder getByteReceivedCountMetrics() {
			return bytesReceivedCountMetrics;
		}

		public MetricDataSetHolder getBytesCurrentCountMetrics() {
			return bytesCurrentCountMetrics;
		}

		public MetricDataSetHolder getMessagesCurrentCountMetrics() {
			return messagesCurrentCountMetrics;
		}

		public MetricDataSetHolder getMessagesReceivedCountMetrics() {
			return messagesReceivedCountMetrics;
		}

		public MetricDataSetHolder getMetricById(String metricId) {
			return metricIndex.get(metricId);
		}

	}

	private Timer analyticsTimer = null;

	protected DomainRuntimeServiceMBeanWrapper domainRuntime;

	protected Map<String, ServerMetrics> serverMetricSet = new HashMap<String, JMSServerResource.ServerMetrics>();

	protected JMSServerRuntimeInfo constructJMSServerRuntimeInfo(
			JMSServerRuntimeMBeanWrapper jmsServerRuntime) {

		/* jms server runtime info */
		JMSServerRuntimeInfo runtimeInfo = new JMSServerRuntimeInfo();
		runtimeInfo.setBytesCurrentCount(jmsServerRuntime
				.getBytesCurrentCount());
		runtimeInfo.setBytesPendingCount(jmsServerRuntime
				.getBytesPendingCount());
		runtimeInfo.setBytesReceivedCount(jmsServerRuntime
				.getBytesReceivedCount());
		runtimeInfo
				.setConsumptionPaused(jmsServerRuntime.isConsumptionPaused());
		runtimeInfo.setInsertionPaused(jmsServerRuntime.isInsertionPaused());
		runtimeInfo.setMessagesCurrentCount(jmsServerRuntime
				.getMessagesCurrentCount());
		runtimeInfo.setMessagesPendingCount(jmsServerRuntime
				.getMessagesPendingCount());
		runtimeInfo.setMessagesReceivedCount(jmsServerRuntime
				.getMessagesReceivedCount());
		runtimeInfo.setProductionPaused(jmsServerRuntime.isProductionPaused());
		runtimeInfo.setHealthState(jmsServerRuntime.getHealthState());
		return runtimeInfo;
	}

	protected MetricsInfo constructMetricsInfo(String serverName) {
		ServerMetrics serverMetrics = serverMetricSet.get(serverName);
		if (serverMetrics == null) {
			throw new NotFoundException(String.format(
					"No metrics found for server '%s'", serverName));
		}
		try {
			MetricsInfo returnValue = new MetricsInfo();
			List<MetricDataSetInfo> metrics = new ArrayList<MetricDataSetInfo>();
			metrics.add(serverMetrics.getMessagesCurrentCountMetrics()
					.getInfo());
			metrics.add(serverMetrics.getMessagesReceivedCountMetrics()
					.getInfo());
			metrics.add(serverMetrics.getBytesCurrentCountMetrics()
					.getInfo());
			metrics.add(serverMetrics.getByteReceivedCountMetrics()
					.getInfo());

			returnValue.getMetrics().addAll(metrics);

			/* compute the hash from the toString */
			returnValue
					.setVersion(convertByteToHexString(computeHash(returnValue
							.toString())));

			return returnValue;

		} catch (Exception ex) {
			throw new WebApplicationException(ex);
		}
	}

	@Override
	@PreDestroy
	protected void destroy() {
		if (analyticsTimer != null)
			analyticsTimer.cancel();
		super.destroy();
	}

	/**
	 * Returns a cache key for the metricinfo for a given server
	 * 
	 * @param serverName
	 *            The name of the server
	 * @return a cache key
	 */
	protected String getCacheKeyForServerMetrics(String serverName) {
		return String.format("%s-%s", MetricsInfo.CACHE_KEY, serverName);
	}

	public DomainRuntimeServiceMBeanWrapper getDomainRuntime() {
		return domainRuntime;
	}

	@GET
	@Produces({ JSON_CONTENT_TYPE })
	@Path("/{jmsServerName}/metric")
	public MetricsInfo getJMSServerAvailableMetrics(
			@PathParam("jmsServerName") String jmsServerName) {

		/* the return value */
		MetricsInfo returnValue = null;

		/* check if we have the metrics info in cache */
		String cacheKey = getCacheKeyForServerMetrics(jmsServerName);
		Cache cache = cacheManager.getCache(MEMORY_CACHE);
		Element element = cache.get(cacheKey);
		if (element != null) {
			returnValue = (MetricsInfo) element.getObjectValue();
		} else {
			/* cache miss */
			try {
				returnValue = constructMetricsInfo(jmsServerName);

				/* store the domain info into the cache */
				cache.put(new Element(cacheKey, returnValue));
			} catch (Exception ex) {
				throw new WebApplicationException(ex);
			}
		}
		return returnValue;
	}

	@GET
	@Produces({ JSON_CONTENT_TYPE })
	@Path("/{jmsServerName}/metric/version")
	public ResourceVersion getJMSServerAvailableMetricsVersion(
			@PathParam("jmsServerName") String jmsServerName) {
		MetricsInfo info = getJMSServerAvailableMetrics(jmsServerName);
		return new ResourceVersion(MetricsInfo.class.getName(),
				info.getVersion());
	}

	@GET
	@Produces({ JSON_CONTENT_TYPE })
	@Path("/{jmsServerName}")
	public JMSServerInfo getJMSServerInfo(
			@PathParam("jmsServerName") String jmsServerName) {
		JMSServerMBeanWrapper serverMBean = domainRuntime
				.getDomainConfiguration().getJMSServer(jmsServerName);
		if (serverMBean == null) {
			throw new NotFoundException();
		}
		try {
			JMSServerInfo returnValue = constructMinimalJMSServerInfo(serverMBean);

			JMSServerRuntimeMBeanWrapper jmsServerRuntime = domainRuntime
					.getJMSServerRuntime(jmsServerName);
			if (jmsServerRuntime != null) {
				returnValue
						.setRuntimeInfo(constructJMSServerRuntimeInfo(jmsServerRuntime));
			}
			return returnValue;
		} catch (Exception ex) {
			throw new WebApplicationException(ex);
		}

	}

	@GET
	@Produces({ JSON_CONTENT_TYPE })
	public List<JMSServerInfo> getJMSServerNames() {
		List<JMSServerInfo> _serversInfo = new ArrayList<JMSServerInfo>();
		try {
			List<JMSServerMBeanWrapper> serverMBeans = domainRuntime
					.getDomainConfiguration().getJMSServers();
			for (JMSServerMBeanWrapper mbean : serverMBeans) {
				_serversInfo.add(constructMinimalJMSServerInfo(mbean));
			}
			return _serversInfo;
		} catch (Exception ex) {
			throw new WebApplicationException(ex);
		}

	}

	@GET
	@Produces({ JSON_CONTENT_TYPE })
	@Path("/{jmsServerName}/metric/{metricId}")
	public MetricDataSet getServerMetrics(
			@PathParam("jmsServerName") String jmsServerName,
			@PathParam("metricId") String metricId) {

		ServerMetrics serverMetrics = serverMetricSet.get(jmsServerName);
		if (serverMetrics == null) {
			throw new NotFoundException(String.format(
					"No metrics found for jms server '%s'", jmsServerName));
		}

		MetricDataSetHolder metric = serverMetrics.getMetricById(metricId);
		if (metric == null) {
			throw new NotFoundException(String.format(
					"Metric '%s' not found for jms server '%s'", metricId,
					jmsServerName));
		}
		return metric.getDataset();

	}

	public Map<String, ServerMetrics> getServerMetricSet() {
		return serverMetricSet;
	}

	@Override
	@PostConstruct
	protected void initialize() {
		super.initialize();

		try {

			domainRuntime = new DomainRuntimeServiceMBeanWrapper(
					lookupDomainRuntimeServiceMBean(),
					domainRuntimeServiceMBeanObjectName);

			InitialContext inctxt = new InitialContext();
			TimerManager mgr = (TimerManager) inctxt
					.lookup("java:comp/env/tm/default");
			analyticsTimer = mgr.scheduleAtFixedRate(this, 0, 500);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	@Override
	public void timerExpired(Timer timer) {
		List<JMSServerInfo> servers = getJMSServerNames();
		for (JMSServerInfo serverInfo : servers) {

			/* fetch or create a server metrics instance for the given server */
			ServerMetrics serverMetrics = serverMetricSet.get(serverInfo
					.getName());
			if (serverMetrics == null) {
				serverMetrics = new ServerMetrics();
				serverMetricSet.put(serverInfo.getName(), serverMetrics);
			}

			/* set default values */
			Long serverReceivedMessages = 0L;
			Long serverCurrentMessages = 0L;
			Long serverReceivedBytes = 0L;
			Long serverCurrentBytes = 0L;

			Date sampleDate = new Date();

			/* try to fetch the servers runtime info */

			JMSServerRuntimeMBeanWrapper jmsServerRuntime = domainRuntime
					.getJMSServerRuntime(serverInfo.getName());
			if (jmsServerRuntime != null) {
				serverCurrentMessages = jmsServerRuntime
						.getMessagesCurrentCount();
				serverReceivedMessages = jmsServerRuntime
						.getMessagesReceivedCount();
				serverCurrentBytes = jmsServerRuntime
						.getBytesCurrentCount();
				serverReceivedBytes = jmsServerRuntime
						.getBytesReceivedCount();
			}

			/*
			 * now we have the samples (default or fetched from the server
			 * runtime
			 */
			serverMetrics.getMessagesCurrentCountMetrics().addSample(
					new MetricSample(sampleDate, serverCurrentMessages));
			serverMetrics.getMessagesReceivedCountMetrics().addSample(
					new MetricSample(sampleDate, serverReceivedMessages));
			serverMetrics.getBytesCurrentCountMetrics().addSample(
					new MetricSample(sampleDate, serverCurrentBytes));
			serverMetrics.getByteReceivedCountMetrics().addSample(
					new MetricSample(sampleDate, serverReceivedBytes));
		}

	}

}
