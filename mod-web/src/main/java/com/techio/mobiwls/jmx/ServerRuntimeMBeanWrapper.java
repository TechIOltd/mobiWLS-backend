package com.techio.mobiwls.jmx;

import javax.management.MBeanServer;
import javax.management.ObjectName;

public class ServerRuntimeMBeanWrapper {

	protected MBeanServer mbeanServer;

	protected ObjectName serverRuntimeMBean;

	protected ServerRuntimeMBeanWrapper(MBeanServer mbeanServer,
			ObjectName serverRuntimeMBean) {
		super();
		this.mbeanServer = mbeanServer;
		this.serverRuntimeMBean = serverRuntimeMBean;
	}

	public Long getActivationTime() {
		return getLongAttribute("ActivationTime");
	}

	protected Boolean getBooleanAttribute(String attribute) {
		return (Boolean) JMXUtils.getAttribute(mbeanServer, serverRuntimeMBean,
				attribute);
	}

	public String getCurrentDirectory() {
		return getStringAttribute("CurrentDirectory");
	}

	public String getCurrentMachine() {
		return getStringAttribute("CurrentMachine");
	}

	protected Long getLongAttribute(String attribute) {
		return (Long) JMXUtils.getAttribute(mbeanServer, serverRuntimeMBean,
				attribute);
	}
	
	protected Integer getIntegerAttribute(String attribute) {
		return (Integer) JMXUtils.getAttribute(mbeanServer, serverRuntimeMBean,
				attribute);
	}

	public Integer getOpenSocketsCurrentCount() {
		return getIntegerAttribute("OpenSocketsCurrentCount");
	}

	public String getServerClasspath() {
		return getStringAttribute("ServerClasspath");
	}

	public String getState() {
		return getStringAttribute("State");
	}
	
	public String getName() {
		return getStringAttribute("Name");
	}

	protected String getStringAttribute(String attribute) {
		return JMXUtils.getStringAttribute(mbeanServer, serverRuntimeMBean,
				attribute);
	}

	public String getWeblogicVersion() {
		return getStringAttribute("WeblogicVersion");
	}

	public Boolean isRestartRequired() {
		return getBooleanAttribute("RestartRequired");
	}

}
