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

package com.techio.mobiwls.jmx;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.ObjectName;

public class DomainRuntimeServiceMBeanWrapper {

	protected ObjectName domainRuntimeServiceMBeanObjectName = null;

	protected MBeanServer domainRuntimeServiceMBeanServer = null;

	public DomainRuntimeServiceMBeanWrapper(
			MBeanServer domainRuntimeServiceMBeanServer,
			ObjectName domainRuntimeServiceMBeanObjectName) {
		super();
		this.domainRuntimeServiceMBeanServer = domainRuntimeServiceMBeanServer;
		this.domainRuntimeServiceMBeanObjectName = domainRuntimeServiceMBeanObjectName;
	}

	/**
	 * returns the active DomainMBean for the current WebLogic Server domain.
	 * 
	 * @return
	 */
	public DomainMBeanWrapper getDomainConfiguration() {
		ObjectName domainMbean = (ObjectName) JMXUtils.getAttribute(
				domainRuntimeServiceMBeanServer,
				domainRuntimeServiceMBeanObjectName, "DomainConfiguration");
		if (domainMbean != null) {
			return new DomainMBeanWrapper(domainRuntimeServiceMBeanServer,
					domainMbean);
		} else
			return null;
	}

	/**
	 * @todo we need to use an operation rather than querying the mbeans
	 * @param serverName
	 * @return
	 */
	public ServerRuntimeMBeanWrapper getServerRuntime(String serverName) {
		try {
			ObjectName[] mbeans = getServerRuntimeMBeans();
			for (ObjectName mbean : mbeans) {
				String _serverName = JMXUtils.getStringAttribute(
						domainRuntimeServiceMBeanServer, mbean, "Name");
				if (_serverName.equals(serverName)) {
					ServerRuntimeMBeanWrapper _w = new ServerRuntimeMBeanWrapper(
							domainRuntimeServiceMBeanServer, mbean);
					return _w;
				} else
					continue;
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return null;
	}

	protected ObjectName[] getServerRuntimeMBeans() {
		try {
			return (ObjectName[]) domainRuntimeServiceMBeanServer.getAttribute(
					domainRuntimeServiceMBeanObjectName, "ServerRuntimes");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public JMSServerRuntimeMBeanWrapper getJMSServerRuntime(String jmsServerName) {
		try {
			ObjectName obj = new ObjectName(String.format(
					"com.bea:*,Name=%s,Type=JMSServerRuntime", jmsServerName));
			Set<ObjectName> result = domainRuntimeServiceMBeanServer.queryNames(obj, null);
			if(!result.isEmpty()) {
				return new JMSServerRuntimeMBeanWrapper(domainRuntimeServiceMBeanServer, (ObjectName)(result.toArray())[0]);
			} else return null;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public List<ServerRuntimeMBeanWrapper> getServerRuntimes() {
		try {
			ObjectName[] mbeans = getServerRuntimeMBeans();
			List<ServerRuntimeMBeanWrapper> returnValue = new ArrayList<ServerRuntimeMBeanWrapper>(
					mbeans.length);
			for (ObjectName mbean : mbeans) {
				ServerRuntimeMBeanWrapper _w = new ServerRuntimeMBeanWrapper(
						domainRuntimeServiceMBeanServer, mbean);
				returnValue.add(_w);
			}
			return returnValue;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}
