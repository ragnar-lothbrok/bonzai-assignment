package com.assignment.sql.test;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.assignment.Application;
import com.assignment.sql.api.IReportRequestParser;
import com.assignment.sql.dtos.ReportRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
	classes = Application.class,
	loader = SpringApplicationContextLoader.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class QueryTest {

	@Autowired
	private IReportRequestParser reportRequestParser;

	@Test
	public void test1() {
		ReportRequest reportRequest = new ReportRequest();
		String response = reportRequestParser.parseReportRequest(reportRequest);
		assertNull(response);
	}
	
	@Test
	public void test2() {
		ReportRequest reportRequest = new ReportRequest();
		reportRequest.setStartdate("2017-02-01");
		reportRequest.setEnddate("2017-03-01");
		reportRequest.setMetrics("impression,clicks");
		reportRequest.setDimension("ad.name,campaign.name");
		String response = reportRequestParser.parseReportRequest(reportRequest);
		assertNotNull(response);
	}
}
