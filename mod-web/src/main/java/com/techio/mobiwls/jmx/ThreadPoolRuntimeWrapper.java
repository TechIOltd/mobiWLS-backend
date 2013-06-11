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

	/**
	 * The number of completed requests in the priority queue.
	 * 
	 * @return
	 */
	public Long getCompletedRequestCount() {
		return getLongAttribute("CompletedRequestCount");
	}

	/**
	 * The number of idle threads in the pool. This count does not include
	 * standby threads and stuck threads. The count indicates threads that are
	 * ready to pick up new work when it arrives
	 * 
	 * @return
	 */
	public Integer getExecuteThreadIdleCount() {
		return getIntegerAttribute("ExecuteThreadIdleCount");
	}

	/**
	 * The total number of threads in the pool.
	 * 
	 * @return
	 */
	public Integer getExecuteThreadTotalCount() {
		return getIntegerAttribute("ExecuteThreadTotalCount");
	}

	/**
	 * The threads that are being held by a request right now. These threads
	 * will either be declared as stuck after the configured timeout or will
	 * return to the pool before that. The self-tuning mechanism will backfill
	 * if necessary.
	 * 
	 * @return
	 */
	public Integer getHoggingThreadCount() {
		return getIntegerAttribute("HoggingThreadCount");
	}

	/**
	 * The number of pending user requests in the priority queue. The priority
	 * queue contains requests from internal subsystems and users. This is just
	 * the count of all user requests.
	 * 
	 * @return
	 */
	public Integer getPendingUserRequestCount() {
		return getIntegerAttribute("PendingUserRequestCount");
	}

	/**
	 * The number of pending requests in the priority queue. This is the total
	 * of internal system requests and user requests.
	 * 
	 * @return
	 */
	public Integer getQueueLength() {
		return getIntegerAttribute("QueueLength");
	}

	/**
	 * The number of threads in the standby pool. Threads that are not needed to
	 * handle the present work load are designated as standby and added to the
	 * standby pool. These threads are activated when more threads are needed.
	 * 
	 * @return
	 */
	public Integer getStandbyThreadCount() {
		return getIntegerAttribute("StandbyThreadCount");
	}
	
	/**
	 * The mean number of requests completed per second.
	 */
	public Double getThroughput() {
		return (Double)getAttribute("Throughput");
	}

}
