package com.techio.mobiwls.jmx;

import javax.management.AttributeNotFoundException;
import javax.management.MBeanServer;
import javax.management.ObjectName;

public class JMXUtils {
	public static Object getAttribute(MBeanServer mbeanServer,
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
	
	public static String getStringAttribute(MBeanServer mbeanServer,
			ObjectName objectName, String attributeName) {
		Object attribute = getAttribute(mbeanServer, objectName, attributeName);
		if (attribute != null)
			return String.valueOf(attribute);
		else
			return null;

	}

}
