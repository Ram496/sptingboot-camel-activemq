package com.springboot.camel.validation;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class ValidationConstants {

	/*
	 * These constants are used for validation purpose, values are not fixed and can
	 * be modified, if required.
	 */

	final int REQUESTTYPE_MIN = 0;
	final int REQUESTTYPE_MAX = 1;

	final int TRN_MIN = 1;
	final int TRN_MAX = 23;

	final int NAME_MIN = 23;
	final int NAME_MAX = 43;

	final int FORMATTYPE_MIN = 43;
	final int FORMATTYPE_MAX = 44;

	final int AMOUNT_MIN = 44;
	final int AMOUNT_MAX = 63;

	final int CURRENCY_MIN = 63;
	final int CURRENCY_MAX = 66;

	final int SERVICE_MIN = 66;
	final int SERVICE_MAX = 69;

	final int SOURCECOUNTRY_MIN = 69;
	final int SOURCECOUNTRY_MAX = 71;
	final int MESSAGE_MIN = 71;
	// message max length value is equal to message.length().
	final String MESSAGE_MAX = "";

	
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
	List<String> getService() {

		List<String> list = new ArrayList<>();

		list.add("ATZ");
		list.add("AUZ");
		list.add("ATC");

		return list;
	}

	Set<Currency> currencies = Currency.getAvailableCurrencies();

	
}
