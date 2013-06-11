/**
 * 
 */
package com.techio.mobiwls.jmx;

import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * @author slavikos
 *
 */
public abstract class BaseMBeanWrapper {
	
	protected ObjectName mbean = null;

	protected MBeanServer mbeanServer = null;

	protected BaseMBeanWrapper(MBeanServer mbeanServer, ObjectName mbean) {
		super();
		this.mbean = mbean;
		this.mbeanServer = mbeanServer;
	}
	
	protected Boolean getBooleanAttribute(String attribute) {
		return (Boolean) JMXUtils.getAttribute(mbeanServer, mbean,
				attribute);
	}
	
	protected Long getLongAttribute(String attribute) {
		return (Long) JMXUtils.getAttribute(mbeanServer, mbean,
				attribute);
	}
	
	protected Integer getIntegerAttribute(String attribute) {
		return (Integer) JMXUtils.getAttribute(mbeanServer, mbean,
				attribute);
	}
	
	protected String getStringAttribute(String attribute) {
		return JMXUtils.getStringAttribute(mbeanServer, mbean,
				attribute);
	}

	protected Object getAttribute(String attribute) {
		return JMXUtils.getAttribute(mbeanServer, mbean,
				attribute);
	}
	protected MBeanServer getMbeanServer() {
		return mbeanServer;
	}
}
