package com.elpaso.serfj.test.account.controllers;

import com.elpaso.serfj.ResponseHelper;
import java.io.IOException;
import java.util.Map;

/**
 * @author Eduardo Yáñez
 * Date: 01-may-2009
 */
public class AccountController {
    public void index(ResponseHelper response, Map<String, String> params) throws IOException {
    }
    public void newResource(ResponseHelper response, Map<String, String> params) throws IOException {
        response.renderPage();
    }
    public void balance(ResponseHelper response, Map<String, String> params) throws IOException {
        if (response.getSerializer() != null) {
            response.serialize("OBJECT 2 JSON");
        } else {
            response.renderPage("balance");
        }
    }
}
