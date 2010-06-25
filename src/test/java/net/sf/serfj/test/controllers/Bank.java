package net.sf.serfj.test.controllers;

import javax.security.auth.login.LoginException;

import net.sf.serfj.RestController;
import net.sf.serfj.annotations.DELETE;
import net.sf.serfj.annotations.GET;
import net.sf.serfj.annotations.POST;
import net.sf.serfj.annotations.PUT;


/**
 * @author Eduardo Yáñez Date: 01-may-2009
 */
public class Bank extends RestController {
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
