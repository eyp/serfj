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
package net.sf.serfj.test.account.controllers;

import java.io.IOException;
import java.util.Map;

import net.sf.serfj.ResponseHelper;
import net.sf.serfj.annotations.GET;


/**
 * @author Eduardo Yáñez
 */
public class Account {
    @GET
    public void index(ResponseHelper response, Map<String, String> params) throws IOException {
    }

    @GET
    public void show(ResponseHelper response, Map<String, String> params) throws IOException {
        if (response.getSerializer() != null) {
            response.serialize("Account object to serialize");
        }
    }

	@GET
	public void newResource(ResponseHelper response, Map<String, String> params) throws IOException {
		response.renderPage();
	}

	@GET
	public void balance(ResponseHelper response, Map<String, String> params) throws IOException {
		if (response.getSerializer() != null) {
			response.serialize("Balance object to serialize");
		} else {
			response.renderPage("balance");
		}
	}
}
