/**
 * 
 */
package com.techio.mobiwls.rest.resources;

import java.util.ArrayList;
import java.util.List;

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
 * @author slavikos
 * 
 */
@Path("/server")
@Singleton
public class ServerResource extends BaseResource implements TimerListener {
	
	protected DomainRuntimeServiceMBeanWrapper domainRuntime;
	
	
	@Override
	public void timerExpired(Timer timer) {
		List<ServerInfo> servers = getServerNames();
		for(ServerInfo serverInfo : servers) {
			System.err.println(String.format("quering '%s'", serverInfo.getName()));
			ServerRuntimeMBeanWrapper serverRuntime = domainRuntime.getServerRuntime(serverInfo.getName());
			if(serverRuntime != null) {
			ServerThreadPoolRuntimeInfo _info = getThreadPoolRuntimeInfo(serverInfo.getName());
			System.err.println(_info);
			}
		}
		
	}

	private Timer analyticsTimer = null;
	
	
	protected ServerRuntimeInfo constructServerRuntimeInfo(ServerRuntimeMBeanWrapper serverRuntime) {
		
		/* server runtime info */
		ServerRuntimeInfo runtimeInfo = new ServerRuntimeInfo();
		runtimeInfo.setActivationTime(serverRuntime.getActivationTime());
		runtimeInfo.setCurrentDirectory(serverRuntime.getCurrentDirectory());
		runtimeInfo.setCurrentMachine(serverRuntime.getCurrentMachine());
		runtimeInfo.setOpenSocketsCurrentCount(serverRuntime.getOpenSocketsCurrentCount());
		runtimeInfo.setRestartRequired(serverRuntime.isRestartRequired());
		runtimeInfo.setServerClasspath(serverRuntime.getServerClasspath());
		runtimeInfo.setState(serverRuntime.getState());
		runtimeInfo.setWeblogicVersion(serverRuntime.getWeblogicVersion());
		
		return runtimeInfo;
	}
	
	protected ServerThreadPoolRuntimeInfo constructServerThreadPoolRuntimeInfo(ThreadPoolRuntimeWrapper threadpoolRuntime) {
		ServerThreadPoolRuntimeInfo runtimeInfo = new ServerThreadPoolRuntimeInfo();
		
		runtimeInfo.setCompletedRequestCount(threadpoolRuntime.getCompletedRequestCount());
		runtimeInfo.setExecuteThreadIdleCount(threadpoolRuntime.getExecuteThreadIdleCount());
		runtimeInfo.setExecuteThreadTotalCount(threadpoolRuntime.getExecuteThreadTotalCount());
		runtimeInfo.setHoggingThreadCount(threadpoolRuntime.getHoggingThreadCount());
		runtimeInfo.setPendingUserRequestCount(threadpoolRuntime.getPendingUserRequestCount());
		runtimeInfo.setQueueLength(threadpoolRuntime.getQueueLength());
		runtimeInfo.setStandbyThreadCount(threadpoolRuntime.getStandbyThreadCount());
		runtimeInfo.setThroughput(threadpoolRuntime.getThroughput());
		
		return runtimeInfo;
	}

	@Override
	@PostConstruct
	protected void initialize() {
		super.initialize();
		try {
			
			domainRuntime = new DomainRuntimeServiceMBeanWrapper(lookupDomainRuntimeServiceMBean(), domainRuntimeServiceMBeanObjectName);
			
			InitialContext inctxt = new InitialContext(); 
			TimerManager mgr = (TimerManager)inctxt.lookup("java:comp/env/tm/default");
			analyticsTimer = mgr.scheduleAtFixedRate(this, (long)0, (long)10000);
		} catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@GET
	@Produces({ JSON_CONTENT_TYPE })
	@Path("/{serverName}")
	public ServerInfo getServerInfo(@PathParam("serverName") String serverName) {
		ServerMBeanWrapper serverMBean =  domainRuntime.getDomainConfiguration().getServers(serverName);
		if(serverMBean==null) {
			throw new NotFoundException();
		}
		try {
			ServerInfo returnValue = constructMinimalServerInfo(serverMBean);
			ServerRuntimeMBeanWrapper serverRuntime = domainRuntime.getServerRuntime(serverName);
			if(serverRuntime != null) {
				returnValue.setRuntimeInfo(constructServerRuntimeInfo(serverRuntime));
			} 
			return returnValue;
		} catch (Exception ex) {
			throw new WebApplicationException(ex);
		}
		
	}
	
	@GET
	@Produces({ JSON_CONTENT_TYPE })
	@Path("/{serverName}/threadpool")
	public ServerThreadPoolRuntimeInfo getThreadPoolRuntimeInfo(@PathParam("serverName") String serverName) {
		ServerRuntimeMBeanWrapper serverRuntime = domainRuntime.getServerRuntime(serverName);
		if(serverRuntime == null) {
			throw new NoRuntimeAvailableException(String.format("No server runtime for server '%s' available.", serverName));
		}
		try {
			return constructServerThreadPoolRuntimeInfo(serverRuntime.getThreadPoolRuntime());
		} catch(Exception ex) {
			throw new WebApplicationException(ex);
		}
	}

	@Override
	@PreDestroy
	protected void destroy() {
		if(analyticsTimer!=null) 
			analyticsTimer.cancel();
		super.destroy();
	}

	

	@GET
	@Produces({ JSON_CONTENT_TYPE })
	public List<ServerInfo> getServerNames() {
		List<ServerInfo> _serversInfo = new ArrayList<ServerInfo>();
		try {
			List<ServerMBeanWrapper> serverMBeans = domainRuntime.getDomainConfiguration().getServers();
			for (ServerMBeanWrapper mbean : serverMBeans) {
				_serversInfo.add(constructMinimalServerInfo(mbean));
			}
			return _serversInfo;
		} catch (Exception ex) {
			throw new WebApplicationException(ex);
		}

	}
}
