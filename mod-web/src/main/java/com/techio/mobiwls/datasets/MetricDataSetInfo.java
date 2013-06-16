
package com.techio.mobiwls.datasets;


public class MetricDataSetInfo {
	/**
	 * axis X title
	 */
	private String axisXTitle;
	/**
	 * axis Y title
	 */
	private String axisYTitle;
	/**
	 * dataset type, for example counter or gauge
	 */
	private MetricDataSetType dataSetType;
	/**
	 * Metric dataset description.
	 */
	private String description;
	/**
	 * Metric dataset Id
	 */
	private String id;
	/**
	 * Metric dataset title
	 */
	private String title;

	public MetricDataSetInfo(MetricDataSetType dataSetType) {
		this.dataSetType = dataSetType;
	}

	public String getAxisXTitle() {
		return axisXTitle;
	}

	public void setAxisXTitle(String axisXTitle) {
		this.axisXTitle = axisXTitle;
	}

	public String getAxisYTitle() {
		return axisYTitle;
	}

	public void setAxisYTitle(String axisYTitle) {
		this.axisYTitle = axisYTitle;
	}

	public MetricDataSetType getDataSetType() {
		return dataSetType;
	}

	public void setDataSetType(MetricDataSetType dataSetType) {
		this.dataSetType = dataSetType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "MetricDataSetInfo [axisXTitle=" + axisXTitle + ", axisYTitle="
				+ axisYTitle + ", dataSetType=" + dataSetType
				+ ", description=" + description + ", id=" + id + ", title="
				+ title + "]";
	}
}