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


/**
 * @author <a href="mailto:filip@techio.com">Filip Slavik</a>
 * 
 */
public class MetricDataSetHolder {

	private MetricDataSetInfo info = null;

	private MetricDataSet dataset = null;

	/**
	 * 
	 */
	public MetricDataSetHolder(String id, String title, String description,
			Integer setCapacity) {
		super();
		MetricDataSetInfo _info = new MetricDataSetInfo();
		_info.setId(id);
		_info.setTitle(title);
		_info.setDescription(description);
		this.info = _info;
		this.dataset = new MetricDataSet(setCapacity);
	}

	public MetricDataSetInfo getInfo() {
		return info;
	}

	public MetricDataSet getDataset() {
		return dataset;
	}

	public void setDataset(MetricDataSet dataset) {
		this.dataset = dataset;
	}

	public void addSample(MetricSample sample) {
		dataset.addSample(sample);
	}
}
