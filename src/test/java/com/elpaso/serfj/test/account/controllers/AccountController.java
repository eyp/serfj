package com.elpaso.serfj.test.account.controllers;

import java.io.IOException;
import java.util.Map;

import com.elpaso.serfj.ResponseHelper;
import com.elpaso.serfj.annotations.GET;

/**
 * @author Eduardo Yáñez Date: 01-may-2009
 */
public class AccountController {
	@GET
	public void index(ResponseHelper response, Map<String, String> params) throws IOException {
	}

	@GET
	public void newResource(ResponseHelper response, Map<String, String> params) throws IOException {
		response.renderPage();
	}

	@GET
	public void balance(ResponseHelper response, Map<String, String> params) throws IOException {
		if (response.getSerializer() != null) {
			response.serialize("OBJECT 2 JSON");
		} else {
			response.renderPage("balance");
		}
	}
}
