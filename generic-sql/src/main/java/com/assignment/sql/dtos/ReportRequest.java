package com.assignment.sql.dtos;

import java.io.Serializable;

public class ReportRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String startdate;
	private String enddate;
	private String dimension;
	private String metrics;

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public String getMetrics() {
		return metrics;
	}

	public void setMetrics(String metrics) {
		this.metrics = metrics;
	}

}
