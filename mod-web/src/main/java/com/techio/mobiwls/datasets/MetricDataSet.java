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

package com.techio.mobiwls.datasets;

import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author <a href="mailto:filip@techio.com">Filip Slavik</a>
 *
 */
public class MetricDataSet {

private ArrayBlockingQueue<MetricSample> samples;
	
	private Date youngestEntry = null;
	
	
	
	private MetricDataSet() {
		super();
	}
	
	public MetricDataSet(int capacity) {
		this();
		samples = new ArrayBlockingQueue<MetricSample>(capacity);
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

	

	public Integer getEntriesCount() {
		return samples != null ? samples.size() : 0;
	}


	
	public Date getOldestEntry() {
		MetricSample oldestEntry = samples.peek();
		return oldestEntry != null ? oldestEntry.getSampledOn() : null;
	}

	public ArrayBlockingQueue<MetricSample> getSamples() {
		return samples;
	}

	public Date getYoungestEntry() {
		return youngestEntry;
	}



}
