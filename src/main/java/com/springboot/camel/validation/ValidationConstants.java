package com.springboot.camel.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ValidationConstants {

	/*
	 * These constants are used for validation purpose, values are not fixed and can
	 * be modified, if required.
	 */

	final String SOURCECOUNTRYCODE = "AT";
	final String SERVICECODE = "ATZ";
	final String MESSAGETEXT = "ship dual fert chem";

	List<String> getList() {

		List<String> list = new ArrayList<>();

		list.add("Mark Imaginary");
		list.add("Govind Real");
		list.add("Shakil Maybe");
		list.add("Chand Imaginary");

		return list;
	}

}
