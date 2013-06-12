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

import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * @author <a href="mailto:filip@techio.com">Filip Slavik</a>
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
	
	protected Object getAttribute(String attribute) {
		return JMXUtils.getAttribute(mbeanServer, mbean,
				attribute);
	}
	
	protected Boolean getBooleanAttribute(String attribute) {
		return (Boolean) JMXUtils.getAttribute(mbeanServer, mbean,
				attribute);
	}
	
	protected Integer getIntegerAttribute(String attribute) {
		return (Integer) JMXUtils.getAttribute(mbeanServer, mbean,
				attribute);
	}
	
	protected Long getLongAttribute(String attribute) {
		return (Long) JMXUtils.getAttribute(mbeanServer, mbean,
				attribute);
	}

	protected MBeanServer getMbeanServer() {
		return mbeanServer;
	}
	protected String getStringAttribute(String attribute) {
		return JMXUtils.getStringAttribute(mbeanServer, mbean,
				attribute);
	}
}
