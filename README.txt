SerfJ, copyright 2010-2012, Eduardo Yáñez Parareda.

Last version 0.3.3 (20120420)

Defect #36: Requests with null param values cause NPE.
Binary compiled for Java 5.

Last version 0.3.2 (20120314)

Patch #32: Updated Maven plugins and added versions at POM for plugins without version.
Patch #33: Updated JAR dependencies versions
Patch #34: Built on Java 6
Defect #27: Extension .64 doesn't work. Now it's changed to .base64. Tests added.
Defect #28: For resources called 'Signatures' the singular is not well made. Added an exception for signatures. Tests added.
Defect #29: Identifiers for resources didn't allow alphabet chars although the
            identifier started with number. Changed to allow this kind of identifiers. Tests added.
Defect #30: GroupId for serfj must be net.sf.serfj instead of net.sf. Changed to net.sf.serfj.
Defect #31: When not serializer is found a NullPointerException is thrown.
Defect #35: NPE thrown when server is not reached
Documentation #17: Fix Javadoc formatting

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

Last version 0.3.1 (20110804)

Defect #26: Calling getId(resource) always returns null
Feature #20: Add method getId() to controllers
Patch #25: RestServlet refactor

For more information and documentation, please visit:
  * Website: http://serfj.sourceforge.net
  * Blog: http://serfj.wordpress.com
  * Tracker and Roadmap: http://serfj.elpaso-software.com
