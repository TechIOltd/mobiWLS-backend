/**
 * 
 */
package com.techio.mobiwls.datasets;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author slavikos
 * 
 */
public class MetricDataSet {
	
	private ArrayBlockingQueue<MetricSample> samples;
	
	private Date youngestEntry = null;
	
	private MetricDataSetInfo info = null;
	

	public Date getOldestEntry() {
		MetricSample oldestEntry = samples.peek();
		return oldestEntry != null ? oldestEntry.getSampledOn() : null;
	}

	public Date getYoungestEntry() {
		return youngestEntry;
	}

	public Integer getEntriesCount() {
		return samples != null ? samples.size() : 0;
	}


	/**
	 * 
	 */
	public MetricDataSet(String id, String title, String description,
			Integer setCapacity) {
		super();
		MetricDataSetInfo _info = new MetricDataSetInfo();
		_info.setId(id);
		_info.setTitle(title);
		_info.setDescription(description);
		this.info = _info;
		this.samples = new ArrayBlockingQueue<MetricSample>(setCapacity);
	}

	public void addSample(MetricSample sample) {
		try {
			if (samples.remainingCapacity() == 0) {
				/* no more space in queue, remove the item currently on queue's head */
				MetricSample element = samples.take();
			}
			// add the new sample and update the youngest entry date
			samples.add(sample);
			this.youngestEntry = sample.getSampledOn();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	

	public ArrayBlockingQueue<MetricSample> getSamples() {
		return samples;
	}

	public MetricDataSetInfo getInfo() {
		return info;
	}
}
