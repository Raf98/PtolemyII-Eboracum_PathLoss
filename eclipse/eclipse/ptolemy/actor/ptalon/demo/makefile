# Makefile for PtolemyII Ptalon demonstrations
#
# @Version: $Id: makefile 49643 2008-05-30 22:24:52Z cxh $
# @Author: Christopher Brooks (makefile only)
#
# @Copyright (c) 2007-2008 The Regents of the University of California.
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

ME =		ptolemy/actor/ptalon/demo

# Keep these alphabetical please.
DIRS = 		CruiseControl EightChannelFFT GameOfLife \
		MapReduce ModelChange MultipleNodes SmallWorld Unicycle \
		ptinyos

# Root of Ptolemy II directory
ROOT =		../../../..

# Get configuration info
CONFIG =	$(ROOT)/mk/ptII.mk
include $(CONFIG)

# Used to build jar files
PTPACKAGE = 	demo
PTCLASSJAR =

# Include the .class files from these jars in PTCLASSALLJAR
PTCLASSALLJARS = \
	CruiseControl/CruiseControl.jar \
	EightChannelFFT/EightChannelFFT.jar \
	GameOfLife/GameOfLife.jar \
	MapReduce/MapReduce.jar \
	ModelChange/ModelChange.jar \
	MultipleNodes/MultipleNodes.jar \
	SmallWorld/SmallWorld.jar \
	Unicycle/Unicycle.jar

PTCLASSALLJAR = $(PTPACKAGE).jar

# Sources that may or may not be present, but if they are present, we don't
# want make checkjunk to barf on them.
MISC_FILES = 	$(DIRS)

# make checkjunk will not report OPTIONAL_FILES as trash
# make realclean removes OPTIONAL_FILES
OPTIONAL_FILES = \
	doc

all: jclass
	@echo "To run all the demos, run 'make demo'"

install: jclass jars

demo: subdemo

# Get the rest of the rules
include $(ROOT)/mk/ptcommon.mk
