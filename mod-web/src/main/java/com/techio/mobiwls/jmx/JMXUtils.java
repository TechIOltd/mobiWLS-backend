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
