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
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.management.MBeanServer;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.naming.InitialContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

import com.sun.jersey.spi.resource.Singleton;
import com.techio.mobiwls.datasets.MetricDataSetHolder;
import com.techio.mobiwls.datasets.MetricDataSetInfo;
import com.techio.mobiwls.datasets.MetricSample;
import com.techio.mobiwls.jmx.DomainRuntimeServiceMBeanWrapper;
import com.techio.mobiwls.jmx.ServerMBeanWrapper;
import com.techio.mobiwls.jmx.ServerRuntimeMBeanWrapper;
import com.techio.mobiwls.jmx.ThreadPoolRuntimeWrapper;
import com.techio.mobiwls.rest.NoRuntimeAvailableException;
import com.techio.mobiwls.rest.NotFoundException;

import commonj.timers.Timer;
import commonj.timers.TimerListener;
import commonj.timers.TimerManager;

/**
 * @author <a href="mailto:filip@techio.com">Filip Slavik</a>
 * 
 */
@Path("/server")
@Singleton
public class ServerResource extends BaseResource implements TimerListener {

	public static class ServerMetrics {

		protected Map<String, MetricDataSetHolder> metricIndex = new HashMap<String, MetricDataSetHolder>();
		/**
		 * The number of completed requests in the priority queue
		 */
		private MetricDataSetHolder completedRequest;

		/**
		 * The mean number of requests completed per second.
		 */
		private MetricDataSetHolder throughput;

		protected ServerMetrics() {
			super();
			completedRequest = new MetricDataSetHolder("ServerCompletedRequest", "Completed Request",
					"The number of completed requests in the priority queue",
					10);
			
			metricIndex.put(completedRequest.getInfo().getId(), completedRequest);
			throughput = new MetricDataSetHolder("ServerThroughput", "Server Throughput",
					"The mean number of requests completed per second", 10);
			
			metricIndex.put(throughput.getInfo().getId(), throughput);

		}
		
		public MetricDataSetHolder getMetricById(String metricId) {
			return metricIndex.get(metricId);
		}

		public MetricDataSetHolder getCompletedRequest() {
			return completedRequest;
		}

		public MetricDataSetHolder getThroughput() {
			return throughput;
		}
	}

	private Timer analyticsTimer = null;

	protected DomainRuntimeServiceMBeanWrapper domainRuntime;

	protected Map<String, ServerMetrics> serverMetricSet = new HashMap<String, ServerResource.ServerMetrics>();

	protected ServerRuntimeInfo constructServerRuntimeInfo(
			ServerRuntimeMBeanWrapper serverRuntime) {

		/* server runtime info */
		ServerRuntimeInfo runtimeInfo = new ServerRuntimeInfo();
		runtimeInfo.setActivationTime(serverRuntime.getActivationTime());
		runtimeInfo.setCurrentDirectory(serverRuntime.getCurrentDirectory());
		runtimeInfo.setCurrentMachine(serverRuntime.getCurrentMachine());
		runtimeInfo.setOpenSocketsCurrentCount(serverRuntime
				.getOpenSocketsCurrentCount());
		runtimeInfo.setRestartRequired(serverRuntime.isRestartRequired());
		runtimeInfo.setServerClasspath(serverRuntime.getServerClasspath());
		runtimeInfo.setState(serverRuntime.getState());
		runtimeInfo.setWeblogicVersion(serverRuntime.getWeblogicVersion());

		return runtimeInfo;
	}

	protected ServerThreadPoolRuntimeInfo constructServerThreadPoolRuntimeInfo(
			ThreadPoolRuntimeWrapper threadpoolRuntime) {
		ServerThreadPoolRuntimeInfo runtimeInfo = new ServerThreadPoolRuntimeInfo();

		runtimeInfo.setCompletedRequestCount(threadpoolRuntime
				.getCompletedRequestCount());
		runtimeInfo.setExecuteThreadIdleCount(threadpoolRuntime
				.getExecuteThreadIdleCount());
		runtimeInfo.setExecuteThreadTotalCount(threadpoolRuntime
				.getExecuteThreadTotalCount());
		runtimeInfo.setHoggingThreadCount(threadpoolRuntime
				.getHoggingThreadCount());
		runtimeInfo.setPendingUserRequestCount(threadpoolRuntime
				.getPendingUserRequestCount());
		runtimeInfo.setQueueLength(threadpoolRuntime.getQueueLength());
		runtimeInfo.setStandbyThreadCount(threadpoolRuntime
				.getStandbyThreadCount());
		runtimeInfo.setThroughput(threadpoolRuntime.getThroughput());

		return runtimeInfo;
	}

	@Override
	@PreDestroy
	protected void destroy() {
		if (analyticsTimer != null)
			analyticsTimer.cancel();
		super.destroy();
	}

	@GET
	@Produces({ JSON_CONTENT_TYPE })
	@Path("/{serverName}/metric")
	public List<MetricDataSetInfo> getServerAvailableMetrics(
			@PathParam("serverName") String serverName) {

		ServerMetrics serverMetrics = serverMetricSet.get(serverName);
		if (serverMetrics == null) {
			throw new NotFoundException(String.format(
					"No metrics found for server '%s'", serverName));
		}
		try {
			List<MetricDataSetInfo> returnValue = new ArrayList<MetricDataSetInfo>();
			returnValue.add(serverMetrics.completedRequest.getInfo());
			returnValue.add(serverMetrics.throughput.getInfo());

			return returnValue;

		} catch (Exception ex) {
			throw new WebApplicationException(ex);
		}
	}
	
	@GET
	@Produces({ JSON_CONTENT_TYPE })
	@Path("/{serverName}/metric/{metricId}")
	public MetricDataSetHolder getServerMetrics(
			@PathParam("serverName") String serverName, @PathParam("metricId")String metricId) {

		ServerMetrics serverMetrics = serverMetricSet.get(serverName);
		if (serverMetrics == null) {
			throw new NotFoundException(String.format(
					"No metrics found for server '%s'", serverName));
		}
		
		MetricDataSetHolder metric = serverMetrics.getMetricById(metricId);
		if(metric == null) {
			throw new NotFoundException(String.format(
					"Metric '%s' not found for server '%s'", metricId, serverName));
		}
		return metric;
		
	}

	@GET
	@Produces({ JSON_CONTENT_TYPE })
	@Path("/{serverName}")
	public ServerInfo getServerInfo(@PathParam("serverName") String serverName) {
		ServerMBeanWrapper serverMBean = domainRuntime.getDomainConfiguration()
				.getServers(serverName);
		if (serverMBean == null) {
			throw new NotFoundException();
		}
		try {
			ServerInfo returnValue = constructMinimalServerInfo(serverMBean);
			ServerRuntimeMBeanWrapper serverRuntime = domainRuntime
					.getServerRuntime(serverName);
			if (serverRuntime != null) {
				returnValue
						.setRuntimeInfo(constructServerRuntimeInfo(serverRuntime));
			}
			return returnValue;
		} catch (Exception ex) {
			throw new WebApplicationException(ex);
		}

	}

	@GET
	@Produces({ JSON_CONTENT_TYPE })
	public List<ServerInfo> getServerNames() {
		List<ServerInfo> _serversInfo = new ArrayList<ServerInfo>();
		try {
			List<ServerMBeanWrapper> serverMBeans = domainRuntime
					.getDomainConfiguration().getServers();
			for (ServerMBeanWrapper mbean : serverMBeans) {
				_serversInfo.add(constructMinimalServerInfo(mbean));
			}
			return _serversInfo;
		} catch (Exception ex) {
			throw new WebApplicationException(ex);
		}

	}

	@GET
	@Produces({ JSON_CONTENT_TYPE })
	@Path("/{serverName}/threadpool")
	public ServerThreadPoolRuntimeInfo getThreadPoolRuntimeInfo(
			@PathParam("serverName") String serverName) {
		ServerRuntimeMBeanWrapper serverRuntime = domainRuntime
				.getServerRuntime(serverName);
		if (serverRuntime == null) {
			throw new NoRuntimeAvailableException(String.format(
					"No server runtime for server '%s' available.", serverName));
		}
		try {
			return constructServerThreadPoolRuntimeInfo(serverRuntime
					.getThreadPoolRuntime());
		} catch (Exception ex) {
			throw new WebApplicationException(ex);
		}
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
			analyticsTimer = mgr.scheduleAtFixedRate(this, (long) 0,
					(long) 10000);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	@Override
	public void timerExpired(Timer timer) {
		List<ServerInfo> servers = getServerNames();
		for (ServerInfo serverInfo : servers) {

			/* fetch or create a server metrics instance for the given server */
			ServerMetrics serverMetrics = serverMetricSet.get(serverInfo
					.getName());
			if (serverMetrics == null) {
				serverMetrics = new ServerMetrics();
				serverMetricSet.put(serverInfo.getName(), serverMetrics);
			}

			/* set default values */
			Long serverCompletedRequestCount = 0L;
			Double serverThroughput = 0.0;
			Date sampleDate = new Date();

			/* try to fetch the servers runtime info */

			ServerRuntimeMBeanWrapper serverRuntime = domainRuntime
					.getServerRuntime(serverInfo.getName());
			if (serverRuntime != null) {
				ServerThreadPoolRuntimeInfo _info = getThreadPoolRuntimeInfo(serverInfo
						.getName());
				if (_info != null) {
					serverCompletedRequestCount = _info
							.getCompletedRequestCount();
					serverThroughput = _info.getThroughput();
				}
			}

			/*
			 * now we have the samples (default or fetched from the server
			 * runtime
			 */
			serverMetrics.getCompletedRequest().addSample(
					new MetricSample(sampleDate, serverCompletedRequestCount));
			serverMetrics.getThroughput().addSample(
					new MetricSample(sampleDate, serverThroughput));
		}

	}

}
