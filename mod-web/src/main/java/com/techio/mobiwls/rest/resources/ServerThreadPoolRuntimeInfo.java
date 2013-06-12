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

public class ServerThreadPoolRuntimeInfo {
	/**
	 * The number of completed requests in the priority queue.
	 */
	private Long completedRequestCount;

	/**
	 * The number of idle threads in the pool. This count does not include
	 * standby threads and stuck threads. The count indicates threads that are
	 * ready to pick up new work when it arrives
	 */
	private Integer executeThreadIdleCount;

	/**
	 * The total number of threads in the pool.
	 */
	private Integer executeThreadTotalCount;

	/**
	 * The threads that are being held by a request right now. These threads
	 * will either be declared as stuck after the configured timeout or will
	 * return to the pool before that. The self-tuning mechanism will backfill
	 * if necessary.
	 */
	private Integer hoggingThreadCount;

	/**
	 * The number of pending user requests in the priority queue. The priority
	 * queue contains requests from internal subsystems and users. This is just
	 * the count of all user requests.
	 */
	private Integer pendingUserRequestCount;
	
	
	/**
	 * The number of pending requests in the priority queue. This is the total of internal system requests and user requests.
	 */
	private Integer queueLength;

	/**
	 * The number of threads in the standby pool. Threads that are not needed to handle the present work load are designated as standby and added to the standby pool. These threads are activated when more threads are needed.
	 */
	private Integer standbyThreadCount;
	
	
	/**
	 * The mean number of requests completed per second.
	 */
	private Double throughput;


	public Long getCompletedRequestCount() {
		return completedRequestCount;
	}


	public void setCompletedRequestCount(Long completedRequestCount) {
		this.completedRequestCount = completedRequestCount;
	}


	public Integer getExecuteThreadIdleCount() {
		return executeThreadIdleCount;
	}


	public void setExecuteThreadIdleCount(Integer executeThreadIdleCount) {
		this.executeThreadIdleCount = executeThreadIdleCount;
	}


	public Integer getExecuteThreadTotalCount() {
		return executeThreadTotalCount;
	}


	public void setExecuteThreadTotalCount(Integer executeThreadTotalCount) {
		this.executeThreadTotalCount = executeThreadTotalCount;
	}


	public Integer getHoggingThreadCount() {
		return hoggingThreadCount;
	}


	public void setHoggingThreadCount(Integer hoggingThreadCount) {
		this.hoggingThreadCount = hoggingThreadCount;
	}


	public Integer getPendingUserRequestCount() {
		return pendingUserRequestCount;
	}


	public void setPendingUserRequestCount(Integer pendingUserRequestCount) {
		this.pendingUserRequestCount = pendingUserRequestCount;
	}


	public Integer getQueueLength() {
		return queueLength;
	}


	public void setQueueLength(Integer queueLength) {
		this.queueLength = queueLength;
	}


	public Integer getStandbyThreadCount() {
		return standbyThreadCount;
	}


	public void setStandbyThreadCount(Integer standbyThreadCount) {
		this.standbyThreadCount = standbyThreadCount;
	}


	public Double getThroughput() {
		return throughput;
	}


	public void setThroughput(Double throughput) {
		this.throughput = throughput;
	}


	@Override
	public String toString() {
		return "ServerThreadPoolRuntimeInfo [completedRequestCount="
				+ completedRequestCount + ", executeThreadIdleCount="
				+ executeThreadIdleCount + ", executeThreadTotalCount="
				+ executeThreadTotalCount + ", hoggingThreadCount="
				+ hoggingThreadCount + ", pendingUserRequestCount="
				+ pendingUserRequestCount + ", queueLength=" + queueLength
				+ ", standbyThreadCount=" + standbyThreadCount
				+ ", throughput=" + throughput + "]";
	}


	

	
	
}
