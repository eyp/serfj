package com.elpaso.serfj.test.controllers;

import javax.security.auth.login.LoginException;

import com.elpaso.serfj.RestController;
import com.elpaso.serfj.annotations.DELETE;
import com.elpaso.serfj.annotations.GET;
import com.elpaso.serfj.annotations.POST;
import com.elpaso.serfj.annotations.PUT;

/**
 * @author Eduardo Yáñez Date: 01-may-2009
 */
public class BankController extends RestController {
	@GET
	public void index() {
	}

	@GET
	public void show() {

	}

	@GET
	public void newResource() {

	}

	@GET
	public void edit() {

	}

	@POST
	public void create() {

	}

	@PUT
	public void update() {

	}

	@DELETE
	public void delete() {

	}

	@POST
	public void exceptionMethod() throws LoginException {
		throw new LoginException("0100");
	}
}
