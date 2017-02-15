package com.assignment.sql.Impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.assignment.sql.annotations.DateRange;
import com.assignment.sql.annotations.Dimension;
import com.assignment.sql.annotations.Metric;
import com.assignment.sql.api.IReportRequestParser;
import com.assignment.sql.dtos.ReportRequest;
import com.assignment.sql.model.Report;

@Service
public class ReportRequestParserImpl implements IReportRequestParser {

	private final static Logger LOGGER = LoggerFactory.getLogger(ReportRequestParserImpl.class);

	private static String query = "select {0} from reports,campaign,ad where ad.id = reports.ad_id and campaign.id = reports.campaign_id {1} {2}";

	public String parseReportRequest(ReportRequest reportRequest) {
		try {
			StringBuilder rangeClause = new StringBuilder();
			StringBuilder selectedFields = new StringBuilder();
			StringBuilder groupByClause = new StringBuilder();
			Report report = new Report();
			Map<String, List<String>> annotationMappedObjects = getMetrices(report);
			for (Entry<String, List<String>> entry : annotationMappedObjects.entrySet()) {
				if (entry.getKey().equals(Dimension.class.getName())) {
					if (reportRequest.getDimension() != null) {
						List<String> requestDimensions = Arrays.asList(reportRequest.getDimension().split(","));
						for (String value : entry.getValue()) {
							if (requestDimensions.contains(value)) {
								if (selectedFields.length() != 0) {
									selectedFields.append("," + value);
								} else {
									selectedFields.append(value);
								}
								if (groupByClause.length() == 0) {
									groupByClause.append(" group by " + value);
								} else {
									groupByClause.append("," + value);
								}
							}
						}
					}
				} else if (entry.getKey().equals(Metric.class.getName())) {
					if (reportRequest.getMetrics() != null) {
						List<String> requestMetrices = Arrays.asList(reportRequest.getMetrics().split(","));
						for (String value : entry.getValue()) {
							if (requestMetrices.contains(value)) {
								if (selectedFields.length() != 0) {
									selectedFields.append("," + "SUM(" + value + ")");
								} else {
									selectedFields.append(" SUM(" + value + ")");
								}
							}
						}
					}
				} else if (entry.getKey().equals(DateRange.class.getName())) {
					String dbColValue = entry.getValue().get(0);
					if (reportRequest.getStartdate() != null && reportRequest.getEnddate() != null) {
						rangeClause.append(
								" and " + dbColValue + " between '" + reportRequest.getStartdate() + "' and '" + reportRequest.getEnddate() + "'");
					} else if (reportRequest.getStartdate() != null) {
						rangeClause.append(" and " + dbColValue + " > '" + reportRequest.getStartdate() + "'");
					} else if (reportRequest.getEnddate() != null) {
						rangeClause.append(" and " + dbColValue + " < '" + reportRequest.getEnddate() + "'");
					}
				}
			}
			if (reportRequest != null) {
				if (reportRequest.getStartdate() != null && reportRequest.getEnddate() != null) {

				}
			}
			if (selectedFields.length() > 0) {
				String format = MessageFormat.format(query, new Object[] { selectedFields, rangeClause, groupByClause });
				return format;
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured while parsing request = {} ", e);
		}
		return null;
	}

	@SuppressWarnings("unused")
	private String getFormattedDate(String reqDate) {
		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	private Map<String, List<String>> getMetrices(Report report) {
		Map<String, List<String>> annotationMappedObjects = new HashMap<String, List<String>>();
		Field[] fields = report.getClass().getDeclaredFields();
		for (Field field : fields) {
			Annotation[] annotations = field.getAnnotations();
			for (Annotation annotation : annotations) {
				if (annotation.annotationType().equals(Metric.class)) {
					if (annotationMappedObjects.get(Metric.class.getName()) != null) {
						annotationMappedObjects.get(Metric.class.getName()).add(((Metric) annotation).dbColumn());
					} else {
						List<String> dbColumnNames = new ArrayList<String>();
						dbColumnNames.add(((Metric) annotation).dbColumn());
						annotationMappedObjects.put(Metric.class.getName(), dbColumnNames);
					}
				} else if (annotation.annotationType().equals(Dimension.class)) {
					if (annotationMappedObjects.get(Dimension.class.getName()) != null) {
						annotationMappedObjects.get(Dimension.class.getName()).add(((Dimension) annotation).dbColumn());
					} else {
						List<String> dbColumnNames = new ArrayList<String>();
						dbColumnNames.add(((Dimension) annotation).dbColumn());
						annotationMappedObjects.put(Dimension.class.getName(), dbColumnNames);
					}
				} else if (annotation.annotationType().equals(DateRange.class)) {
					if (annotationMappedObjects.get(DateRange.class.getName()) != null) {
						annotationMappedObjects.get(DateRange.class.getName()).add(((DateRange) annotation).dbColumn());
					} else {
						List<String> dbColumnNames = new ArrayList<String>();
						dbColumnNames.add(((DateRange) annotation).dbColumn());
						annotationMappedObjects.put(DateRange.class.getName(), dbColumnNames);
					}
				}
			}
		}
		return annotationMappedObjects;
	}

}
