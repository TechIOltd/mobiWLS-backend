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
	
	public ThreadPoolRuntimeWrapper getThreadPoolRuntime() {
		return new ThreadPoolRuntimeWrapper(getMbeanServer(), (ObjectName)getAttribute("ThreadPoolRuntime"));
	}
	

}
