package net.sf.serfj.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * With this runtime annotation, a developer is able to mark a controller's
 * method in order to not render a page after its execution.
 * 
 * It's only valid to methods that return void, because methods that return an
 * object, will always serialize that object in the response.
 * 
 * Methods that don't return anything have to be annotated so the framework is
 * able to know what to do after the execution. If it's annotated with
 * NotRenderPage, the framework will return a HTTP status code 204 (No content),
 * if it isn't annotated, the framework will search for a page to render.
 * 
 * @author eduardo.yanez
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DoNotRenderPage {
}
