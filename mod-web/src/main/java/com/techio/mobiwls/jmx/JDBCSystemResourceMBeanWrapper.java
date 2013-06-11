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
public class JDBCSystemResourceMBeanWrapper extends BaseMBeanWrapper {

	protected JDBCSystemResourceMBeanWrapper(MBeanServer mbeanServer, ObjectName mbean) {
		super(mbeanServer, mbean);
	}
	
	public String getName() {
		return getStringAttribute("Name");
	}

}
