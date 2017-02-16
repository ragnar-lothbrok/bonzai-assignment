package com.assignment.lucene.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SearchDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Location location;
	private Double range;

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Double getRange() {
		return range;
	}

	public void setRange(Double range) {
		this.range = range;
	}

	@Override
	public String toString() {
		return "SearchDto [location=" + location + ", range=" + range + "]";
	}

}
