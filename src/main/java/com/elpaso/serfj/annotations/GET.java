package com.elpaso.serfj.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation used to mark a resource method that responds to HTTP GET requests. 
 * 
 * @author eduardo.yanez
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface GET {
}
