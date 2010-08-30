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
package net.sf.serfj.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * With this runtime annotation, a developer is able to mark a controller's
 * method in order to not render a page after its execution.<br><br>
 * 
 * It's only valid to methods that return void, because methods that return an
 * object, will always serialize that object in the response.<br><br>
 * 
 * Methods that don't return anything have to be annotated so the framework is
 * able to know what to do after the execution. If it's annotated with
 * DoNotRenderPage, the framework will return a HTTP status code 204 (No content),
 * if it isn't annotated, the framework will search for a page to render.
 * 
 * @author Eduardo Yáñez
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DoNotRenderPage {
}
