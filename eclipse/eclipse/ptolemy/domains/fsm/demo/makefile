# Makefile for PtolemyII Finite State Machine Domain Demos
#
# @Version: $Id: makefile 57040 2010-01-27 20:52:32Z cxh $
# @Author: Xiaojun Liu
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

ME = 		ptolemy/domains/fsm/demo

DIRS =		ABP ChainedTransitions Blending ModalBSC \
		MultipleRuns StateTracker SystemLevelType

# Root of ptII directory
ROOT =		../../../..

CLASSPATH =     $(ROOT)

# Get configuration info
CONFIG =	$(ROOT)/mk/ptII.mk
include $(CONFIG)

# Used to build jar files
PTPACKAGE = 	demo
PTCLASSJAR =

# Include the .class files from these jars in PTCLASSALLJAR
PTCLASSALLJARS = \
	ABP/ABP.jar \
	ChainedTransitions/ChainedTransitions.jar \
	Blending/Blending.jar \
	ModalBSC/ModalBSC.jar \
	StateTracker/StateTracker.jar \
	SystemLevelType/SystemLevelType.jar

# We are not shipping MultipleRuns because it leaves threads running.
# MultipleRuns/MultipleRuns.jar

PTCLASSALLJAR = $(PTPACKAGE).jar

JSRCS =

EXTRA_SRCS =    $(JSRCS) \
	index.htm

# Sources that may or may not be present, but if they are present, we don't
# want make checkjunk to barf on them.
MISC_FILES =    $(DIRS)

# make checkjunk will not report OPTIONAL_FILES as trash
# make distclean removes OPTIONAL_FILES
OPTIONAL_FILES = \
	doc

JCLASS = $(JSRCS:%.java=%.class)

all: jclass
	@echo "To run all the demos, run 'make demo'"

install: jclass jars $(PTCLASSALLJAR)

demo: subdemo

# Get the rest of the rules
include $(ROOT)/mk/ptcommon.mk
