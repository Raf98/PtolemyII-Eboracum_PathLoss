# Makefile for Ptolemy II Process Networks QR Demo
#
# @Authors: Christopher Hylands, based on a file by Thomas M. Parks
#
# @Version: $Id: makefile 36422 2005-02-28 19:19:13Z cxh $
#
# @Copyright (c) 1998-2005 The Regents of the University of California.
# All rights reserved.
#
# Permission is hereby granted, without written agreement and without
# license or royalty fees, to use, copy, modify, and distribute this
# software and its documentation for any purpose, provided that the
# above copyright notice and the following two paragraphs appear in all
# copies of this software.
#
# IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY
# FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
# ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
# THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
# SUCH DAMAGE.
#
# THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
# INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
# MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
# PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
# CALIFORNIA HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
# ENHANCEMENTS, OR MODIFICATIONS.
#
# 						PT_COPYRIGHT_VERSION_2
# 						COPYRIGHTENDKEY

ME =		ptolemy/domains/pn/demo/QR

# Root of the Java directory
ROOT =		../../../../..

CLASSPATH =	$(ROOT)

# Get configuration info
CONFIG =	$(ROOT)/mk/ptII.mk
include $(CONFIG)

# Used to build jar files
PTPACKAGE = 	QR
PTCLASSJAR = 	$(PTPACKAGE).jar

JSRCS = \
	ND_1.java \
	ND_2.java \
	ND_3.java \
	ND_4.java \
	ND_5.java \
	StreamToMatrix.java \
	SourceMatrix.java \
	ArrayIndex.java

OTHER_FILES_TO_BE_JARED = \
	$(JSRCS) \
	QR.xml

EXTRA_SRCS = \
	$(OTHER_FILES_TO_BE_JARED) \
	QR.htm \
	QRVergil.htm

# Sources that may or may not be present, but if they are present, we don't
# want make checkjunk to barf on them.
MISC_FILES =

# make checkjunk will not report OPTIONAL_FILES as trash
# make distclean removes OPTIONAL_FILES
OPTIONAL_FILES = doc

JCLASS = $(JSRCS:%.java=%.class)

# Don't include all or install rules here, we want the user
# to run 'make demos' to run the tests.
all: jclass $(PTCLASSJAR)
	@echo "To run all the demos, run 'make demo'"

install: all

demo: demo_applet demo_ptolemy demo_vergil
demo_applet:
	CLASSPATH="$(CLASSPATH)" appletviewer QR.htm

demo_ptolemy:
	"$(PTII)/bin/ptolemy" QR.xml

demo_vergil:
	"$(PTII)/bin/vergil" QR.xml

docs: javadocs

# 'make clean' removes everything in KRUFT
KRUFT = $(PTCLASSJAR)

# Jar file for the Applet
QRCompile.jar: $(JSRCS) $(JCLASS) makefile
	(cd $(ROOT); \
		$(JAR) cf \
		$(ME)/$@ \
		$(ME)/*.class)

# Get the rest of the rules
include $(ROOT)/mk/ptcommon.mk
