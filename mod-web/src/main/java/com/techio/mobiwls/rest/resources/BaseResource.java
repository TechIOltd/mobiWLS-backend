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

import java.lang.reflect.Method;
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

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import com.techio.mobiwls.jmx.JMSServerMBeanWrapper;
import com.techio.mobiwls.jmx.ServerMBeanWrapper;
import com.techio.mobiwls.rest.infoObjects.HealthState;
import com.techio.mobiwls.rest.infoObjects.HealthStateConstant;
import com.techio.mobiwls.rest.infoObjects.JMSServerInfo;
import com.techio.mobiwls.rest.infoObjects.ServerInfo;

/**
 * @author Filip Slavik (filip@techio.com)
 * 
 */
public class BaseResource {

	public static final String EMPTY_STRING = "";

	public static final String JSON_CONTENT_TYPE = "application/json";

	protected static final String MEMORY_CACHE = "default-cache";

	protected CacheManager cacheManager = null;

	protected ObjectName domainRuntimeServiceMBeanObjectName;

	protected ObjectName runtimeServiceMBeanObjectName;

	protected byte[] computeHash(Object object) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(String.valueOf(object).getBytes());
		return md.digest();
	}

	/**
	 * Constructs and returns an instance of {@link ServerInfo} with the bare
	 * minimum info filled.
	 * 
	 * @see BaseResource#lookupDomainRuntimeServiceMBean()
	 * 
	 * @param serverMBean
	 *            The WLS server mbean
	 * @return A minimalistics instance of {@link ServerInfo}
	 */
	protected ServerInfo constructMinimalServerInfo(
			ServerMBeanWrapper serverMBean) {
		ServerInfo sinfo = new ServerInfo();
		sinfo.setName(serverMBean.getName());
		return sinfo;
	}
	
	protected JMSServerInfo constructMinimalJMSServerInfo(JMSServerMBeanWrapper jmsServerMBean) {
		JMSServerInfo sinfo = new JMSServerInfo();
		sinfo.setName(jmsServerMBean.getName());
		return sinfo;
	}

	protected String convertByteToHexString(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		return sb.toString();
	}

	@PreDestroy
	protected void destroy() {
		if (cacheManager != null) {
			cacheManager.shutdown();
		}
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

		try {
			domainRuntimeServiceMBeanObjectName = new ObjectName(
					"com.bea:Name=DomainRuntimeService,Type=weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean");
		} catch (MalformedObjectNameException e) {
			throw new RuntimeException(
					"failed to resolve DomainRuntimeService", e);
		}

		try {
			runtimeServiceMBeanObjectName = new ObjectName(
					"com.bea:Name=RuntimeService,Type=weblogic.management.mbeanservers.runtime.RuntimeServiceMBean");
		} catch (MalformedObjectNameException e) {
			throw new RuntimeException("failed to resolve RuntimeService", e);
		}

		cacheManager = CacheManager.getInstance();
		/* add the memory cache if it does not yet exist */
		if (!cacheManager.cacheExists(MEMORY_CACHE)) {
			Cache memoryCache = new Cache(MEMORY_CACHE, 5000, false, false, 5,
					5);
			cacheManager.addCache(memoryCache);
		}
		System.err.println("post construct!!!!!");
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

	/**
	 * Convert a 'weblogic.health.HealthState' int value to the corresponding {@link HealthState} enum
	 * @param healthStateValue a int value obtained from {@link #convertWeblogicHealthState(Object)}
	 * @return 
	 * 
	 * @see #convertWeblogicHealthState(Object)
	 */
	public static HealthState getHealthState(int healthStateValue) {
		switch (healthStateValue) {
		case HealthStateConstant.HEALTH_CRITICAL:
			return HealthState.HEALTH_CRITICAL;
		case HealthStateConstant.HEALTH_FAILED:
			return HealthState.HEALTH_FAILED;
		case HealthStateConstant.HEALTH_OK:
			return HealthState.HEALTH_OK;
		case HealthStateConstant.HEALTH_OVERLOADED:
			return HealthState.HEALTH_OVERLOADED;
		case HealthStateConstant.HEALTH_WARN:
			return HealthState.HEALTH_WARN;
		default:
			return null;
		}
	}
	
	/**
	 * Fetch the int value of a 'weblogic.health.HealthState' instance.
	 * @param healthStatus An object instance of 'weblogic.health.HealthState' class
	 * @return
	 */
	public static int convertWeblogicHealthState(Object healthStatus) {
		/*
		 * to avoid linking with weblogic.jar (at least for now) use
		 * reflection to get the value of the weblogic.health.HealthState
		 * instance
		 */
		try {
			@SuppressWarnings("rawtypes")
			Class clazz = Class.forName("weblogic.health.HealthState");
			@SuppressWarnings("unchecked")
			Method getStateMethod = clazz.getMethod("getState");
	
			Integer state = (Integer) getStateMethod.invoke(healthStatus);
			return state;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}
