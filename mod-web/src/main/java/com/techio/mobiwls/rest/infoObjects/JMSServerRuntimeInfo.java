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

package com.techio.mobiwls.rest.infoObjects;

/**
 * @author <a href="mailto:filip@techio.com">Filip Slavik</a>
 * 
 */
public class JMSServerRuntimeInfo {
	
	/**
	 * Returns the current insertion paused state of the JMSServer as boolean value.
	 */
	public Boolean insertionPaused;

	/**
	 * Returns the current consumption paused state of the JMSServer as boolean value.
	 */
	public Boolean consumptionPaused;
	
	/**
	 * Returns the current production paused state of the JMSServer as boolean value.
	 */
	public Boolean productionPaused;
	
	/**
	 * The current number of bytes stored on this JMS server.
	 * This number does not include the pending bytes.
	 */
	public Long bytesCurrentCount;
	
	/**
	 * The current number of bytes pending (unacknowledged or uncommitted) stored on this JMS server.
	 * Pending bytes are over and above the current number of bytes.
	 */
	public Long bytesPendingCount;
	
	/**
	 * The number of bytes received on this JMS server since the last reset.
	 */
	public Long bytesReceivedCount;
	
	/**
	 * The current number of messages stored on this JMS server.
	 * This number does not include the pending messages.
	 */
	public Long messagesCurrentCount;
	
	/**
	 * The current number of messages pending (unacknowledged or uncommitted) stored on this JMS server.
	 * Pending messages are over and above the current number of messages.
	 */
	public Long messagesPendingCount;
	
	/**
	 * The number of messages received on this JMS server since the last reset.
	 */
	public Long messagesReceivedCount;

	public Boolean getInsertionPaused() {
		return insertionPaused;
	}

	public void setInsertionPaused(Boolean insertionPaused) {
		this.insertionPaused = insertionPaused;
	}

	public Boolean getConsumptionPaused() {
		return consumptionPaused;
	}

	public void setConsumptionPaused(Boolean consumptionPaused) {
		this.consumptionPaused = consumptionPaused;
	}

	public Boolean getProductionPaused() {
		return productionPaused;
	}

	public void setProductionPaused(Boolean productionPaused) {
		this.productionPaused = productionPaused;
	}

	public Long getBytesCurrentCount() {
		return bytesCurrentCount;
	}

	public void setBytesCurrentCount(Long bytesCurrentCount) {
		this.bytesCurrentCount = bytesCurrentCount;
	}

	public Long getBytesPendingCount() {
		return bytesPendingCount;
	}

	public void setBytesPendingCount(Long bytesPendingCount) {
		this.bytesPendingCount = bytesPendingCount;
	}

	public Long getBytesReceivedCount() {
		return bytesReceivedCount;
	}

	public void setBytesReceivedCount(Long bytesReceivedCount) {
		this.bytesReceivedCount = bytesReceivedCount;
	}

	public Long getMessagesCurrentCount() {
		return messagesCurrentCount;
	}

	public void setMessagesCurrentCount(Long messagesCurrentCount) {
		this.messagesCurrentCount = messagesCurrentCount;
	}

	public Long getMessagesPendingCount() {
		return messagesPendingCount;
	}

	public void setMessagesPendingCount(Long messagesPendingCount) {
		this.messagesPendingCount = messagesPendingCount;
	}

	public Long getMessagesReceivedCount() {
		return messagesReceivedCount;
	}

	public void setMessagesReceivedCount(Long messagesReceivedCount) {
		this.messagesReceivedCount = messagesReceivedCount;
	}
	
}
