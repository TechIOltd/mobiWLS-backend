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

import java.util.ArrayList;
import java.util.List;

import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * @author <a href="mailto:filip@techio.com">Filip Slavik</a>
 * 
 */
public class JMSRuntimeMBeanWrapper extends BaseMBeanWrapper {

	protected JMSRuntimeMBeanWrapper(MBeanServer mbeanServer, ObjectName mbean) {
		super(mbeanServer, mbean);
	}
	
	protected ObjectName[] getJMSServerRuntimeMBeans() {
		try {
			return (ObjectName[]) mbeanServer.getAttribute(mbean, "JMSServers");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public List<JMSServerRuntimeMBeanWrapper> getJMSServerRuntimes() {
		try {
			ObjectName[] mbeans = getJMSServerRuntimeMBeans();
			List<JMSServerRuntimeMBeanWrapper> returnValue = new ArrayList<JMSServerRuntimeMBeanWrapper>(
					mbeans.length);
			for (ObjectName mbean : mbeans) {
				JMSServerRuntimeMBeanWrapper _w = new JMSServerRuntimeMBeanWrapper(
						mbeanServer, mbean);
				returnValue.add(_w);
			}
			return returnValue;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public JMSServerRuntimeMBeanWrapper getJMSServerRuntime(String serverName) {
		try {
			ObjectName[] mbeans = getJMSServerRuntimeMBeans();
			for (ObjectName mbean : mbeans) {
				String _serverName = JMXUtils.getStringAttribute(
						mbeanServer, mbean, "Name");
				if (_serverName.equals(serverName)) {
					JMSServerRuntimeMBeanWrapper _w = new JMSServerRuntimeMBeanWrapper(
							mbeanServer, mbean);
					return _w;
				} else
					continue;
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return null;
	}
	

}
