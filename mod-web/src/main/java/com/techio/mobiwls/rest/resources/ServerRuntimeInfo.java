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

package com.techio.mobiwls.rest.resources;

/**
 * @author <a href="mailto:filip@techio.com">Filip Slavik</a>
 *
 */
public class ServerRuntimeInfo {
	public Long activationTime;
	
	public String currentDirectory;
	
	public String currentMachine;
	
	public Integer openSocketsCurrentCount;
	
	public Boolean restartRequired;
	
	public String serverClasspath;
	
	public String state;
	
	public String weblogicVersion;

	public Long getActivationTime() {
		return activationTime;
	}

	public String getCurrentDirectory() {
		return currentDirectory;
	}

	public String getCurrentMachine() {
		return currentMachine;
	}

	public Integer getOpenSocketsCurrentCount() {
		return openSocketsCurrentCount;
	}

	public Boolean getRestartRequired() {
		return restartRequired;
	}

	public String getServerClasspath() {
		return serverClasspath;
	}

	public String getState() {
		return state;
	}

	public String getWeblogicVersion() {
		return weblogicVersion;
	}

	public void setActivationTime(Long activationTime) {
		this.activationTime = activationTime;
	}

	public void setCurrentDirectory(String currentDirectory) {
		this.currentDirectory = currentDirectory;
	}

	public void setCurrentMachine(String currentMachine) {
		this.currentMachine = currentMachine;
	}

	public void setOpenSocketsCurrentCount(Integer openSocketsCurrentCount) {
		this.openSocketsCurrentCount = openSocketsCurrentCount;
	}

	public void setRestartRequired(Boolean restartRequired) {
		this.restartRequired = restartRequired;
	}

	public void setServerClasspath(String serverClasspath) {
		this.serverClasspath = serverClasspath;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setWeblogicVersion(String weblogicVersion) {
		this.weblogicVersion = weblogicVersion;
	}
}
