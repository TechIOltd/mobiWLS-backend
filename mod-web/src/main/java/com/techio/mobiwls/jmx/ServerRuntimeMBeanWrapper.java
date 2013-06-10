package com.techio.mobiwls.jmx;

import javax.management.MBeanServer;
import javax.management.ObjectName;

public class ServerRuntimeMBeanWrapper extends BaseMBeanWrapper {

	

	


	

	protected ServerRuntimeMBeanWrapper(MBeanServer mbeanServer,
			ObjectName mbean) {
		super(mbeanServer, mbean);
		// TODO Auto-generated constructor stub
	}



	public Long getActivationTime() {
		return getLongAttribute("ActivationTime");
	}

	

	public String getCurrentDirectory() {
		return getStringAttribute("CurrentDirectory");
	}

	public String getCurrentMachine() {
		return getStringAttribute("CurrentMachine");
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

	

	public String getWeblogicVersion() {
		return getStringAttribute("WeblogicVersion");
	}

	public Boolean isRestartRequired() {
		return getBooleanAttribute("RestartRequired");
	}

}
