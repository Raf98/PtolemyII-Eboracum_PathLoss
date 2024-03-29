# Makefile for Ptolemy II Small World Ptalon demo
#
# @Authors: Christopher Hylands (makefile only)
#
# @Version: $Id: makefile 57040 2010-01-27 20:52:32Z cxh $
#
# @Copyright (c) 2008-2009 The Regents of the University of California.
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

ME =		ptolemy/actor/ptalon/demo/SmallWorld

# Root of the Java directory
ROOT =		../../../../..

CLASSPATH =	$(ROOT)

# Get configuration info
CONFIG =	$(ROOT)/mk/ptII.mk
include $(CONFIG)

# Used to build jar files
PTPACKAGE = 	SmallWorld
PTCLASSJAR = 	$(PTPACKAGE).jar

JSRCS =

OTHER_FILES_TO_BE_JARED = \
	$(JSRCS) \
	Initiator.xml \
	ParameterSweepSmallWorldFSM.xml \
	ParameterSweepSmallWorldSDF.xml \
	RelayNode.xml \
	SmallWorldMultiInstanceComposite.xml \
	SmallWorldPtalonActor.xml \
	SmallWorld.ptln \
	SmallWorld.xml

EXTRA_SRCS = \
	$(OTHER_FILES_TO_BE_JARED) \
	ParameterSweepSmallWorldFSM.htm \
	ParameterSweepSmallWorldSDF.htm \
	ParameterSweepSmallWorldFSMVergil.htm \
	ParameterSweepSmallWorldSDFVergil.htm \
	SmallWorldMultiInstanceCompositeVergil.htm \
	SmallWorldPtalonActorVergil.htm \
	SmallWorldMultiInstanceComposite.htm \
	SmallWorldPtalonActor.htm

# Sources that may or may not be present, but if they are present, we don't
# want make checkjunk to barf on them.
MISC_FILES = $(DIRS)

# make checkjunk will not report OPTIONAL_FILES as trash
# make realclean removes OPTIONAL_FILES
OPTIONAL_FILES = \
	output \
	doc

# Files to remove with 'make clean'
KRUFT = \
	*.jar \
	dataOut*.txt

JCLASS = $(JSRCS:%.java=%.class)

# Don't include all or install rules here, we want the user
# to run 'make demos' to run the tests.
all: jclass
	@echo "To run all the demos, run 'make demo'"

install: all $(PTCLASSJAR)

demo: demo_applet demo_ptolemy demo_vergil

demo_applet:
	CLASSPATH="$(CLASSPATH)" appletviewer SmallWorld.htm

demo_ptolemy:
	"$(PTII)/bin/ptolemy" SmallWorld.xml

demo_vergil:
	"$(PTII)/bin/vergil" SmallWorld.xml

diff:
	diff dataOut0_0.txt output
	diff dataOut1_0.txt output
	diff dataOut0_1.txt output
	diff dataOut1_1.txt output

# Get the rest of the rules
include $(ROOT)/mk/ptcommon.mk
