# Makefile for Ptolemy Classic style Codegen Life demo
#
# @Version: $Id: makefile 59247 2010-09-24 18:49:42Z cxh $
# @Author: Christopher Brooks
#
# @Copyright (c) 2006-2009 The Regents of the University of California
# All rights reserved.
#
# Permission is hereby granted, without written agreement and without
# license or royalty fees, to use, copy, modify, and distribute this
# software and its documentation for any purpose, provided that the
# above copyright notice and the following two paragraphs appear in all
# copies of this software.
#
# IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA  OR RESEARCH IN MOTION
# LIMITED BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL,
# OR CONSEQUENTIAL DAMAGES ARISING OUT OF THE USE OF THIS SOFTWARE AND
# ITS DOCUMENTATION, EVEN IF THE UNIVERSITY OF CALIFORNIA OR
# RESEARCH IN MOTION LIMITED HAVE BEEN ADVISED OF THE POSSIBILITY OF
# SUCH DAMAGE.
#
# THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
# INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
# MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
# PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
# CALIFORNIA  AND RESEARCH IN MOTION LIMITED
# HAVE NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
# ENHANCEMENTS, OR MODIFICATIONS.
#
# 						PT_COPYRIGHT_VERSION_2
# 						COPYRIGHTENDKEY
##########################################################################

ME =		ptolemy/codegen/demo/Life

DIRS =

# Root of the Java directory
ROOT =		../../../..

CLASSPATH =	$(ROOT)

# Get configuration info
CONFIG =	$(ROOT)/mk/ptII.mk
include $(CONFIG)

# Used to build jar files
PTPACKAGE = 	Life
PTCLASSJAR =	$(PTPACKAGE).jar

# Keep this list alphabetized.
JSRCS =

OTHER_FILES_TO_BE_JARED = \
	Life.xml \
	LifeCGAVR.xml \
	LifeNoCG.xml \
	LifePtalon.ptln \
	CycleLEDs.mk.in \
	CycleLEDs.xml \
	LifeCGAVR.mk.in \
	LifePtalonCGAVR.mk.in \
	LifePtalonCGAVR.xml \
	ModalLEDs.mk.in \
	ModalLEDs.xml

EXTRA_SRCS =	$(JSRCS) \
	$(OTHER_FILES_TO_BE_JARED) \
	Life.htm

# Sources that may or may not be present, but if they are present, we don't
# want make checkjunk to barf on them.
# Don't include demo or DIRS here, or else 'make sources' will run 'make demo'
MISC_FILES = ledTankTop


# make checkjunk will not report OPTIONAL_FILES as trash
# make distclean removes OPTIONAL_FILES
OPTIONAL_FILES =

JCLASS = $(JSRCS:%.java=%.class)

all: jclass
	@echo "To run the demo, run 'make demo'"

demo:
	"$(PTII)/bin/vergil" Life.xml

install: all jars $(PTPACKAGE).jar $(PTPACKAGE_NATIVE_JAR)

# Get the rest of the rules
include $(ROOT)/mk/ptcommon.mk
