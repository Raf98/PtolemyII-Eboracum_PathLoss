# Makefile for Ptolemy II Video demo
#
# @Authors: Christopher Brooks (makefile only)
#
# @Version: $Id: makefile 49618 2008-05-30 21:10:32Z cxh $
#
# @Copyright (c) 2008 The Regents of the University of California.
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

ME =		ptolemy/actor/lib/video/demo/DCT8x8

# Root of the Java directory
ROOT =		../../../../../..

CLASSPATH =	$(ROOT)

# Get configuration info
CONFIG =	$(ROOT)/mk/ptII.mk
include $(CONFIG)

# Used to build jar files
PTPACKAGE = 	DCT8x8
PTCLASSJAR = 	$(PTPACKAGE).jar

JSRCS =

OTHER_FILES_TO_BE_JARED = \
	DCT8x8.xml

EXTRA_SRCS = \
	$(JSRCS) \
	$(OTHER_FILES_TO_BE_JARED)

# Sources that may or may not be present, but if they are present, we don't
# want make checkjunk to barf on them.
MISC_FILES = $(DIRS)

# make checkjunk will not report OPTIONAL_FILES as trash
# make realclean removes OPTIONAL_FILES
OPTIONAL_FILES = \
	doc

JCLASS = $(JSRCS:%.java=%.class)

# Don't include all or install rules here, we want the user
# to run 'make demos' to run the tests.
all: jclass
	@echo "To run all the demos, run 'make demo'"

install: all $(PTCLASSJAR)

demo: demo_ptolemy demo_vergil

#demo_applet:
#	CLASSPATH="$(CLASSPATH)" appletviewer DCT8x8.htm

#demo_applet_vergil:
#	CLASSPATH="$(CLASSPATH)" appletviewer DCT8x8Vergil.htm

demo_ptolemy:
	"$(PTII)/bin/ptolemy" DCT8x8.xml

demo_vergil:
	"$(PTII)/bin/vergil" DCT8x8.xml

# Get the rest of the rules
include $(ROOT)/mk/ptcommon.mk
