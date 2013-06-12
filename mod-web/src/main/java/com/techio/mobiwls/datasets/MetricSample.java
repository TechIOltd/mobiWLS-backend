package com.techio.mobiwls.datasets;

import java.util.Date;

public class MetricSample {

	private Date sampledOn;

	private Number sampledValue;
	
	public MetricSample(Date sampledOn, Number sampledValue) {
		super();
		this.sampledOn = sampledOn;
		this.sampledValue = sampledValue;
	}

	public Date getSampledOn() {
		return sampledOn;
	}

	public Number getSampledValue() {
		return sampledValue;
	}
	
	
}
