# Makefile for the MoML Filters
#
# @Authors: Christopher Hylands, based on a file by Thomas M. Parks
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

ME =		ptolemy/moml/filter

DIRS =		test

# Root of the Java directory
ROOT =		../../..

CLASSPATH =	$(ROOT)

# Get configuration info
CONFIG =	$(ROOT)/mk/ptII.mk
include $(CONFIG)

# Used to build jar files
PTPACKAGE = 	filter
PTCLASSJAR = 	$(PTPACKAGE).jar

JSRCS = \
	ActorIndex.java \
	AddEditorFactory.java \
	AddIcon.java \
	BackwardCompatibility.java \
	ChangeFixedWidth1ToAuto.java \
	ClassChanges.java \
	GRColorChanges.java \
	HideAnnotationNames.java \
	MultiportToSinglePort.java \
	NamedObjClassesSeen.java \
	LazyTypedCompositeActorChanges.java \
	ParameterNameChanges.java \
	PortClassChanges.java \
	PortNameChanges.java \
	PropertyClassChanges.java \
	RelationWidthChanges.java \
	RemoveClasses.java \
	RemoveGraphicalClasses.java \
	RemoveNonPtinyClasses.java \
	RemoveProperties.java

EXTRA_SRCS =	$(JSRCS)

# Sources that may or may not be present, but if they are present, we don't
# want make checkjunk to barf on them.
# Don't include demo or DIRS here, or else 'make sources' will run 'make demo'
MISC_FILES =	test

# make checkjunk will not report OPTIONAL_FILES as trash
# make realclean removes OPTIONAL_FILES
OPTIONAL_FILES = \
	test \
	$(PTCLASSJAR) \

JCLASS = $(JSRCS:%.java=%.class)


all: jclass

install: all $(PTCLASSJAR)

demo: all

demoActorIndex: allActors.txt models.txt
	java -classpath "$(ROOT)$(CLASSPATHSEPARATOR)$(ROOT)/lib/diva.jar" \
		ptolemy.moml.filter.ActorIndex allActors.txt models.txt 

# Get the rest of the rules
include $(ROOT)/mk/ptcommon.mk
