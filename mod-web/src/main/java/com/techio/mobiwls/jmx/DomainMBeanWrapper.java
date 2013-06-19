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

import javax.management.MBeanServer;
import javax.management.ObjectName;

public class DomainMBeanWrapper extends BaseMBeanWrapper {

	protected DomainMBeanWrapper(MBeanServer mbeanServer, ObjectName mbean) {
		super(mbeanServer, mbean);
	}

	public List<AppDeploymentMBeanWrapper> getAppDeployments() {
		ObjectName serverMBeans[] = (ObjectName[]) JMXUtils.getAttribute(
				mbeanServer, mbean, "AppDeployments");
		List<AppDeploymentMBeanWrapper> _result = new ArrayList<AppDeploymentMBeanWrapper>();
		for (ObjectName mbean : serverMBeans) {
			_result.add(new AppDeploymentMBeanWrapper(mbeanServer, mbean));
		}
		return _result;
	}

	public List<ClusterMBeanWrapper> getClusters() {
		ObjectName serverMBeans[] = (ObjectName[]) JMXUtils.getAttribute(
				mbeanServer, mbean, "Clusters");
		List<ClusterMBeanWrapper> _result = new ArrayList<ClusterMBeanWrapper>();
		for (ObjectName mbean : serverMBeans) {
			_result.add(new ClusterMBeanWrapper(mbeanServer, mbean));
		}
		return _result;
	}

	public String getConfigurationVersion() {
		return getStringAttribute("ConfigurationVersion");
	}

	public String getConsoleContextPath() {
		return getStringAttribute("ConsoleContextPath");
	}

	public String getDomainVersion() {
		return getStringAttribute("DomainVersion");
	}

	public List<JDBCSystemResourceMBeanWrapper> getJDBCSystemResources() {
		ObjectName serverMBeans[] = (ObjectName[]) JMXUtils.getAttribute(
				mbeanServer, mbean, "JDBCSystemResources");
		List<JDBCSystemResourceMBeanWrapper> _result = new ArrayList<JDBCSystemResourceMBeanWrapper>();
		for (ObjectName mbean : serverMBeans) {
			_result.add(new JDBCSystemResourceMBeanWrapper(mbeanServer, mbean));
		}
		return _result;
	}

	public List<JMSServerMBeanWrapper> getJMSServers() {
		ObjectName serverMBeans[] = (ObjectName[]) JMXUtils.getAttribute(
				mbeanServer, mbean, "JMSServers");
		List<JMSServerMBeanWrapper> _result = new ArrayList<JMSServerMBeanWrapper>();
		for (ObjectName mbean : serverMBeans) {
			_result.add(new JMSServerMBeanWrapper(mbeanServer, mbean));
		}
		return _result;
	}
	
	public JMSServerMBeanWrapper getJMSServer(String jmsServerName) {
		ObjectName serverMBeans[] = (ObjectName[]) JMXUtils.getAttribute(
				mbeanServer, mbean, "JMSServers");
		for (ObjectName mbean : serverMBeans) {
			if (JMXUtils.getStringAttribute(mbeanServer, mbean, "Name").equals(
					jmsServerName)) {
				return new JMSServerMBeanWrapper(mbeanServer, mbean);
			} else
				continue;
		}
		return null;
	}

	public Long getLastModificationTime() {
		return getLongAttribute("LastModificationTime");
	}

	public String getName() {
		return getStringAttribute("Name");
	}

	public List<ServerMBeanWrapper> getServers() {
		ObjectName serverMBeans[] = (ObjectName[]) JMXUtils.getAttribute(
				mbeanServer, mbean, "Servers");
		List<ServerMBeanWrapper> _result = new ArrayList<ServerMBeanWrapper>();
		for (ObjectName mbean : serverMBeans) {
			_result.add(new ServerMBeanWrapper(mbeanServer, mbean));
		}
		return _result;
	}

	public ServerMBeanWrapper getServers(String serverName) {
		ObjectName serverMBeans[] = (ObjectName[]) JMXUtils.getAttribute(
				mbeanServer, mbean, "Servers");
		for (ObjectName mbean : serverMBeans) {
			if (JMXUtils.getStringAttribute(mbeanServer, mbean, "Name").equals(
					serverName)) {
				return new ServerMBeanWrapper(mbeanServer, mbean);
			} else
				continue;
		}
		return null;
	}

	public Boolean isConsoleEnabled() {
		return getBooleanAttribute("ConsoleEnabled");
	}

}
