# Makefile for interface automaton models for some Ptolemy II
# components with respect to token passing
#
# @Authors: Christopher Hylands (makefile only)
#
# @Version: $Id: makefile 54302 2009-06-10 22:30:35Z cxh $
#
# @Copyright (c) 2001-2009 The Regents of the University of California.
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

ME = 		ptolemy/domains/modal/demo/SystemLevelType

DIRS =		fmv

# Root of the Java directory
ROOT =		../../../../..

CLASSPATH =	$(ROOT)

# Get configuration info
CONFIG =	$(ROOT)/mk/ptII.mk
include $(CONFIG)

# Used to build jar files
PTPACKAGE = 	SystemLevelType
PTCLASSJAR =

# Include the .class files from these jars in PTCLASSALLJAR
PTCLASSALLJARS = \
	fmv/fmv.jar
PTCLASSALLJAR = $(PTPACKAGE).jar

JSRCS =

DEMOS = \
	CSPDomain.xml \
	CSPDomain_PolyActor.xml \
	CSPDomain_SDFActor.xml \
	DEDomain.xml \
	DEDomain_PolyActor.xml \
	DEDomain_SDFActor.xml \
	DP.xml \
	DummyDirector1.xml \
	DummyDirector1_SDFActor.xml \
	DummyDirector2.xml \
	DummyDirector2_SDFActor.xml \
	InterruptableWaitingThreadA.xml \
	Interrupter.xml \
	Monitor.xml \
	PNDomain.xml \
	PNDomain_PolyActor.xml \
	PNDomain_SDFActor.xml \
	PolyActor.xml \
	SDFActor.xml \
	SDFDomain.xml \
	SDFDomain_PolyActor.xml \
	SDFDomain_SDFActor.xml \
	ThreadA.xml \
	ThreadA_Monitor_ThreadB.xml \
	ThreadB.xml \
	ThreadBNotifies.xml \
	Trigger.xml \
	UNKNOWN.xml

OTHER_FILES_TO_BE_JARED = \
	$(DEMOS) \
	README.htm \
	README.txt

EXTRA_SRCS = \
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

install: all jars

demo:
	for demo in $(DEMOS); \
	do \
		"$(PTII)/bin/vergil" -modal $$demo; \
	done

# Get the rest of the rules
include $(ROOT)/mk/ptcommon.mk
