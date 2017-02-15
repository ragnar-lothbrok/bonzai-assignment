package com.assignment.sql.controllers;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.assignment.sql.api.IReportRequestParser;
import com.assignment.sql.dtos.ReportRequest;

/**
 * Will send filters in return get sql query
 * 
 * @author raghunandangupta
 *
 */
@Controller
public class ReportController {

	private final static Logger LOGGER = LoggerFactory.getLogger(ReportController.class);

	@Autowired
	private IReportRequestParser reportRequestParser;

	@RequestMapping(
		method = { RequestMethod.GET })
	@ResponseBody
	public Map<String, String> get(ReportRequest reportRequest) {
		LOGGER.info("Request received for report {} ", reportRequest);
		Map<String, String> responses = new HashMap<String, String>();
		String sql = reportRequestParser.parseReportRequest(reportRequest);
		if (sql == null) {
			responses.put("SQL", "Couldn't construct query");
		} else {
			responses.put("SQL", sql);
		}
		return responses;
	}

}
