/*
 * Copyright 2010 Eduardo Yáñez Parareda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.serfj.test.controllers;

import javax.security.auth.login.LoginException;

import net.sf.serfj.RestController;
import net.sf.serfj.annotations.DELETE;
import net.sf.serfj.annotations.GET;
import net.sf.serfj.annotations.POST;
import net.sf.serfj.annotations.PUT;


/**
 * @author Eduardo Yáñez
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
