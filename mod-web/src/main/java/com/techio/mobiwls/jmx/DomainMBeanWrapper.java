package com.techio.mobiwls.jmx;

import java.util.ArrayList;
import java.util.List;

import javax.management.MBeanServer;
import javax.management.ObjectName;

public class DomainMBeanWrapper extends BaseMBeanWrapper {

	protected DomainMBeanWrapper(MBeanServer mbeanServer, ObjectName mbean) {
		super(mbeanServer, mbean);
	}

	public String getDomainVersion() {
		return getStringAttribute("DomainVersion");
	}
	
	public String getConfigurationVersion() {
		return getStringAttribute("ConfigurationVersion");
	}
	
	public Boolean isConsoleEnabled() {
		return getBooleanAttribute("ConsoleEnabled");
	}
	
	public String getConsoleContextPath() {
		return getStringAttribute("ConsoleContextPath");
	}
	
	public Long getLastModificationTime() {
		return getLongAttribute("LastModificationTime");
	}
	
	public String getName() {
		return getStringAttribute("Name");
	}
	
	public List<ServerMBeanWrapper> getServers() {
		ObjectName serverMBeans[] = (ObjectName[]) JMXUtils.getAttribute(mbeanServer, mbean, "Servers");
		List<ServerMBeanWrapper> _result = new ArrayList<ServerMBeanWrapper>();
		for (ObjectName mbean : serverMBeans) {
			_result.add(new ServerMBeanWrapper(mbeanServer, mbean));
		}
		return _result;
	}
	
	
	public List<ClusterMBeanWrapper> getClusters() {
		ObjectName serverMBeans[] = (ObjectName[]) JMXUtils.getAttribute(mbeanServer, mbean, "Clusters");
		List<ClusterMBeanWrapper> _result = new ArrayList<ClusterMBeanWrapper>();
		for (ObjectName mbean : serverMBeans) {
			_result.add(new ClusterMBeanWrapper(mbeanServer, mbean));
		}
		return _result;
	}
	
	public List<JDBCSystemResourceMBeanWrapper> getJDBCSystemResources() {
		ObjectName serverMBeans[] = (ObjectName[]) JMXUtils.getAttribute(mbeanServer, mbean, "JDBCSystemResources");
		List<JDBCSystemResourceMBeanWrapper> _result = new ArrayList<JDBCSystemResourceMBeanWrapper>();
		for (ObjectName mbean : serverMBeans) {
			_result.add(new JDBCSystemResourceMBeanWrapper(mbeanServer, mbean));
		}
		return _result;
	}
	
	public List<JMSServerMBeanWrapper> getJMSServers() {
		ObjectName serverMBeans[] = (ObjectName[]) JMXUtils.getAttribute(mbeanServer, mbean, "JMSServers");
		List<JMSServerMBeanWrapper> _result = new ArrayList<JMSServerMBeanWrapper>();
		for (ObjectName mbean : serverMBeans) {
			_result.add(new JMSServerMBeanWrapper(mbeanServer, mbean));
		}
		return _result;
	}
	
	public List<AppDeploymentMBeanWrapper> getAppDeployments() {
		ObjectName serverMBeans[] = (ObjectName[]) JMXUtils.getAttribute(mbeanServer, mbean, "AppDeployments");
		List<AppDeploymentMBeanWrapper> _result = new ArrayList<AppDeploymentMBeanWrapper>();
		for (ObjectName mbean : serverMBeans) {
			_result.add(new AppDeploymentMBeanWrapper(mbeanServer, mbean));
		}
		return _result;
	}
	
}
