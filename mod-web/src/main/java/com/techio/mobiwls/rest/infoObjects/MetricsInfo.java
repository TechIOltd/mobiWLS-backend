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

import java.util.ArrayList;
import java.util.List;

import com.techio.mobiwls.datasets.MetricDataSetInfo;

/**
 * @author <a href="mailto:filip@techio.com">Filip Slavik</a>
 * 
 */
public class MetricsInfo {
	
	public static final String CACHE_KEY = "MetricsInfo";

	private String version;

	private List<MetricDataSetInfo> metrics = new ArrayList<MetricDataSetInfo>();

	/**
	 * 
	 */
	public MetricsInfo() {

	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<MetricDataSetInfo> getMetrics() {
		return metrics;
	}

	public void setMetrics(List<MetricDataSetInfo> metrics) {
		this.metrics = metrics;
	}

	@Override
	public String toString() {
		return "MetricsInfo [metrics=" + metrics + "]";
	}

}
