# Makefile for the Continuous Time domain kernel
#
# @Authors: Christopher Brooks (makefile only)
#
# @Version: $Id: makefile 56527 2009-12-08 23:49:06Z eal $
#
# @Copyright (c) 2006-2009 The Regents of the University of California.
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

ME =		ptolemy/domains/continuous/kernel

DIRS = 		solver

# Root of the Java directory
ROOT =		../../../..

CLASSPATH =	$(ROOT)

# Get configuration info
CONFIG =	$(ROOT)/mk/ptII.mk
include $(CONFIG)

# Used to build jar files
PTPACKAGE = 	kernel
PTCLASSJAR =

# Include the .class files from these jars in PTCLASSALLJAR
PTCLASSALLJARS = \
		solver/solver.jar

PTCLASSALLJAR = $(PTPACKAGE).jar

JSRCS = \
	ContinuousDirector.java \
	ContinuousIntegrator.java \
	ContinuousODESolver.java \
	ContinuousScheduler.java \
	ContinuousStatefulComponent.java \
	ContinuousStepSizeController.java \
	HybridModalDirector.java \
	Noise.java

EXTRA_SRCS =	$(JSRCS)

# Sources that may or may not be present, but if they are present, we don't
# want make checkjunk to barf on them.
MISC_FILES =	$(DIRS)

# make checkjunk will not report OPTIONAL_FILES as trash
# make distclean removes OPTIONAL_FILES
OPTIONAL_FILES = \
	doc \
	'ContinuousIntegrator$$IntegratorCausalityInterface.class' \
	'HybridModalDirector$$ActorsFiredIterator.class'

JCLASS = $(JSRCS:%.java=%.class)


all: jclass
install: jclass jars


# Get the rest of the rules
include $(ROOT)/mk/ptcommon.mk
