# Makefile for the Ptolemy II Finite State Machine (FSM) Interface Automatons
#
# @Authors: Christopher Brooks (makefile only)
#
# @Version: $Id: makefile 57040 2010-01-27 20:52:32Z cxh $
#
# @Copyright (c) 1998-2009 The Regents of the University of California.
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

ME =            ptolemy/domains/fsm/kernel/ia

# Root of the ptII directory
ROOT =		../../../../..


# Get configuration info
CONFIG =	$(ROOT)/mk/ptII.mk
include $(CONFIG)

DIRS = 		test

CLASSPATH =	$(ROOT)

# Used to build zip files
PTPACKAGE = 	ia
PTCLASSJAR =	$(PTPACKAGE).jar

JSRCS = \
	InterfaceAutomaton.java \
	InterfaceAutomatonTransition.java \
	StatePair.java

OTHER_FILES_TO_BE_JARED =

EXTRA_SRCS =	$(JSRCS) $(OTHER_FILES_TO_BE_JARED)

# Sources that may or may not be present, but if they are present, we don't
# want make checkjunk to barf on them.
MISC_FILES =	$(DIRS)

# make checkjunk will not report OPTIONAL_FILES as trash
# make distclean removes OPTIONAL_FILES
OPTIONAL_FILES = \
	doc \
	'AbstractActionsAttribute$$TypeFunction.class' \
	'FSMActor$$1.class' \
	'FSMActor$$PortScope.class' \
	'FSMDirector$$1.class' \
	'FSMDirector$$2.class' \
	'InterfaceAutomaton$$1.class' \
	'InterfaceAutomaton$$Triple.class' \
	'RelationList$$RelationNode.class'

JCLASS = $(JSRCS:%.java=%.class)

all: jclass
install: all jars


# Get the rest of the rules
include $(ROOT)/mk/ptcommon.mk
