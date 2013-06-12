/**
 * 
 */
package com.techio.mobiwls.datasets;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author slavikos
 *
 */
public class MetricDataSet {

	private String description;
	private String id;
	private ArrayBlockingQueue<MetricSample> samples;
	private String title;
	/**
	 * 
	 */
	public MetricDataSet(String id, String title, String description, Integer setCapacity) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.samples = new ArrayBlockingQueue<MetricSample>(setCapacity);
	}

	public void addSample(MetricSample sample) {
		samples.add(sample);
	}

	public String getDescription() {
		return description;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}
	
	public ArrayBlockingQueue<MetricSample> getSamples() {
		return samples;
	}
}
