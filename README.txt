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
--------------------------------------------------------------------------------
SerfJ - Simplest Ever REST Framework for Java
--------------------------------------------------------------------------------
Using SerfJ is the easiest way of developing Java REST web applications. It helps
you develop your application over an elegant MVC arquitecture, giving more importance
to convention than configuration, so for example, you will not have to have configuration 
files or annotations in order to specify which view serves a controller's method. However, 
SerfJ is very flexible library, so if you want to jump over those conventions, you can 
configure the behaviour of your applications as you like.

The framework tries to meet JSR 311 specification, but it doesn't follow every point 
of that, because the purpose is to have a very intuitive library, and some some aspects 
of the specification are out of the scope of SerfJ.

Documentation: http://serfj.sourceforge.net


Version 0.4.2 (20131112)
------------------------

* Feature: Added a new config param 'encoding' with default value to UTF-8 for encoding responses.

Version 0.4.1 (20120914)
------------------------

* Defect: Communication exceptions were not launched properly.

Version 0.4.0 (20120808)
------------------------

* Feature: Default FileSerializer implementation, now is possible to serve files for downloading.
* Feature: New default extension (.file) for serving files.
* Feature: Within functional style you are able to implement generic serializers for whatever model you have.
* Feature: Now javax.servlet.ServletContext, javax.servlet.http.HttpServletRequest and javax.servlet.http.HttpServletResponse are accessible from controllers.
* Patch: Instead of implementing net.sf.serfj.serializers.Serializer developers must implement net.sf.serfj.serializers.ObjectSerializer for serializing objects.
* Patch: RestController.addObject2request is deprecated in favour of RestController.putParam

Version 0.3.4 (20120424)
------------------------

* Defect: RestController.getRemoteAddress() doesn't check proxy headers.

Version 0.3.3 (20120420)
------------------------

* Defect: Requests with null param values cause NPE.
* Binary compiled for Java 5.

Version 0.3.2 (20120314)
------------------------

* Patch: Updated Maven plugins and added versions at POM for plugins without version.
* Patch: Updated JAR dependencies versions
* Patch: Built on Java 6
* Defect: Extension .64 doesn't work. Now it's changed to .base64. Tests added.
* Defect: For resources called 'Signatures' the singular is not well made. Added an exception for signatures. Tests added.
* Defect: Identifiers for resources didn't allow alphabet chars although the
            identifier started with number. Changed to allow this kind of identifiers. Tests added.
* Defect: GroupId for serfj must be net.sf.serfj instead of net.sf. Changed to net.sf.serfj.
* Defect: When not serializer is found a NullPointerException is thrown.
* Defect: NPE thrown when server is not reached
* Documentation: Fix Javadoc formatting

New dependencies:

* slf4j-api-1.6.4
* logback-classic-1.0.0
* logback-core-1.0.0
* xstream-1.4.2
* xpp3_min-1.1.4c
* commons-codec-1.6
* jettison-1.3
* stax-1.2.0
* stax-api-1.0.1

Version 0.3.1 (20110804)
------------------------

* Defect: Calling getId(resource) always returns null
* Feature: Add method getId() to controllers
* Patch: RestServlet refactor

For more information and documentation, please visit:
  * Website: http://serfj.sourceforge.net
  * Blog: http://serfj.wordpress.com
  * Project hosted on GitHub: https://github.com/eyp/serfj

