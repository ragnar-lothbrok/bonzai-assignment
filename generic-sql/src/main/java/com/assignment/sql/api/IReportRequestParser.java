package com.assignment.sql.api;

import com.assignment.sql.dtos.ReportRequest;

public interface IReportRequestParser {

	String parseReportRequest(ReportRequest reportRequest);
}
