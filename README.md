NanoHTTPd2_WIP
======================
A fork of the famous project : NanoHTTPd (https://github.com/gseguin/NanoHTTPd)

Things that have been changed:

* Used some Java8 features to improve the performance.
* Made the serve() method abstract thus easy to use.
* Fixed some minor typo mistakes.
* Refactored the code structure to make it easier to be maintained.

When you need it:

* If you want to use the HTTP as a user interface of your program.
* If you want to build a private NAS for your family.
* If you find all the other HTTP frameworks too heavy-duty.
* If you want it simple & elegant.

Features & limitations
======================

* Released as open source, Modified BSD licence
* No fixed config files, logging, authorization etc. (Implement by yourself if you need them.)
* Supports parameter parsing of GET and POST methods
* Parameter names must be unique. (Adding support to multiple instance of a parameter is not difficult, but would make
  the interface a bit more cumbersome to use.)
* Supports both dynamic content and file serving
* Supports file upload
* Never caches anything
* Doesn't limit bandwidth, request time or simultaneous connections
* Default code serves files and shows all HTTP parameters and headers
* File server supports directory listing, index.html and index.htm
* File server does the 301 redirection trick for directories without /
* File server supports simple skipping for files (continue download)
* File server uses current directory as a web root
* File server serves also very long files without memory overhead
* Contains a built-in list of most common mime types
* All header names are converted lowercase, so they don't vary between browsers/clients

Ways to use
===========

* Run as a standalone app (serves files from current directory and shows requests)
* Implement serve() and embed to your own program (see ExampleServer.java for a simple example)
* Call serveFile() from serve() with your own base directory
* To test file uploading, try browsing file-upload-test.htm through NanoHTTPd2, upload something and watch the console
  output.
* A fast way to develop dbo-alike workers.

Maven usage (Experimental)
===

```xml
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
	<dependency>
	    <groupId>com.github.huzpsb</groupId>
	    <artifactId>NanoHTTPd2</artifactId>
	    <version>1.2</version>
	</dependency>
```