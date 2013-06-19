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

import com.techio.mobiwls.rest.infoObjects.HealthState;
import com.techio.mobiwls.rest.resources.BaseResource;

/**
 * @author <a href="mailto:filip@techio.com">Filip Slavik</a>
 * 
 */
public class JMSServerRuntimeMBeanWrapper extends BaseMBeanWrapper {

	protected JMSServerRuntimeMBeanWrapper(MBeanServer mbeanServer, ObjectName mbean) {
		super(mbeanServer, mbean);
	}
	
	/**
	 * The current number of bytes stored on this JMS server.
	 * This number does not include the pending bytes.
	 */
	public Long getBytesCurrentCount() {
		return getLongAttribute("BytesCurrentCount");
	}

	/**
	 * The current number of bytes pending (unacknowledged or uncommitted) stored on this JMS server.
	 * Pending bytes are over and above the current number of bytes.
	 */
	public Long getBytesPendingCount() {
		return getLongAttribute("BytesPendingCount");
	}
	
	/**
	 * The number of bytes received on this JMS server since the last reset.
	 */
	public Long getBytesReceivedCount() {
		return getLongAttribute("BytesReceivedCount");
	}
	
	/**
	 * The current number of messages stored on this JMS server.
	 * This number does not include the pending messages.
	 */
	public Long getMessagesCurrentCount() {
		return getLongAttribute("MessagesCurrentCount");
	}
	
	/**
	 * The current number of messages pending (unacknowledged or uncommitted) stored on this JMS server.
	 * Pending messages are over and above the current number of messages.
	 */
	public Long getMessagesPendingCount() {
		return getLongAttribute("MessagesPendingCount");
	}
	
	/**
	 * The number of messages received on this JMS server since the last reset.
	 */
	public Long getMessagesReceivedCount() {
		return getLongAttribute("MessagesReceivedCount");
	}
	
	/**
	 * Returns the current consumption paused state of the JMSServer as boolean value.
	 */
	public Boolean isConsumptionPaused() {
		return getBooleanAttribute("ConsumptionPaused");
	}
	
	/**
	 * Returns the current insertion paused state of the JMSServer as boolean value.
	 */
	public Boolean isInsertionPaused() {
		return getBooleanAttribute("InsertionPaused");
	}
	
	/**
	 * Returns the current production paused state of the JMSServer as boolean value.
	 */
	public Boolean isProductionPaused() {
		return getBooleanAttribute("ProductionPaused");
	}
	
	public HealthState getHealthState() {
		Object healthState = getAttribute("HealthState");
		if(healthState != null) {
			return BaseResource.getHealthState(BaseResource.convertWeblogicHealthState(healthState));
		} else return null;
	}
	

}
