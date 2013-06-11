package com.techio.mobiwls.jmx;

import java.util.ArrayList;
import java.util.List;

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
		ObjectName domainMbean = (ObjectName)JMXUtils.getAttribute(domainRuntimeServiceMBeanServer, domainRuntimeServiceMBeanObjectName, "DomainConfiguration");
		if(domainMbean!=null) {
			return new DomainMBeanWrapper(domainRuntimeServiceMBeanServer, domainMbean);
		} else return null;
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
