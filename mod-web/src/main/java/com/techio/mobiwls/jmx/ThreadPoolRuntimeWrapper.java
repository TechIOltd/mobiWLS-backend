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
public class ThreadPoolRuntimeWrapper extends BaseMBeanWrapper {

	protected ThreadPoolRuntimeWrapper(MBeanServer mbeanServer, ObjectName mbean) {
		super(mbeanServer, mbean);
	}



}
