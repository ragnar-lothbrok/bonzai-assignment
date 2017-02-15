package com.assignment.sql.model;

import java.util.Date;

import com.assignment.sql.annotations.Dimension;
import com.assignment.sql.annotations.DateRange;
import com.assignment.sql.annotations.Metric;

public class Report {

	@DateRange(
		name = "startDate",
		dbColumn = "date")
	private Date startDate;

	@DateRange(
		name = "endDate",
		dbColumn = "date")
	private Date endDate;

	@Dimension(
		name = "ad.name",
		dbColumn = "ad.name")
	private Integer adId;

	@Dimension(
		name = "campaign.name",
		dbColumn = "campaign.name")
	private Integer campaignId;
	
	private Integer request;

	@Metric(
		name = "impression",
		dbColumn = "impression")
	private Integer impression;

	@Metric(
		name = "clicks",
		dbColumn = "clicks")
	private Integer clicks;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getAdId() {
		return adId;
	}

	public void setAdId(Integer adId) {
		this.adId = adId;
	}

	public Integer getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(Integer campaignId) {
		this.campaignId = campaignId;
	}

	public Integer getRequest() {
		return request;
	}

	public void setRequest(Integer request) {
		this.request = request;
	}

	public Integer getImpression() {
		return impression;
	}

	public void setImpression(Integer impression) {
		this.impression = impression;
	}

	public Integer getClicks() {
		return clicks;
	}

	public void setClicks(Integer clicks) {
		this.clicks = clicks;
	}

}
