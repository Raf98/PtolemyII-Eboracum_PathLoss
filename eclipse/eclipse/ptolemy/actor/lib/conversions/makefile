# Makefile for Java Ptolemy polymorphic actor classes
#
# @Authors: Christopher Hylands, based on a file by Thomas M. Parks
#
# @Version: $Id: makefile 57040 2010-01-27 20:52:32Z cxh $
#
# @Copyright (c) 1999-2009 The Regents of the University of California.
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

# This directory contains polymorphic actors.

ME =		ptolemy/actor/lib/conversions

DIRS = 		test

# Root of the Java directory
ROOT =		../../../..

CLASSPATH =	$(ROOT)

# Get configuration info
CONFIG =	$(ROOT)/mk/ptII.mk
include $(CONFIG)

# Used to build jar files
PTPACKAGE = 	conversions
PTCLASSJAR = 	$(PTPACKAGE).jar

# Keep this list alphabetized.
JSRCS = \
	AnythingToDouble.java \
	BooleanToAnything.java \
	CartesianToComplex.java \
	CartesianToPolar.java \
	ComplexToCartesian.java \
	ComplexToPolar.java \
	Converter.java \
	DoubleToFix.java \
	ExpressionToToken.java \
	FixToDouble.java \
	FixToFix.java \
	IntArrayToString.java \
	UnsignedByteArrayToString.java \
	InUnitsOf.java \
	LongToDouble.java \
	PolarToCartesian.java \
	PolarToComplex.java \
	Round.java \
	StringToIntArray.java \
	StringToUnsignedByteArray.java \
	StringToXML.java \
	TokenToExpression.java 

OTHER_FILES_TO_BE_JARED = \
	conversions.xml

EXTRA_SRCS =	$(JSRCS) $(OTHER_FILES_TO_BE_JARED)


# Sources that may or may not be present, but if they are present, we don't
# want make checkjunk to barf on them.
MISC_FILES =	$(DIRS)

# make checkjunk will not report OPTIONAL_FILES as trash
# make distclean removes OPTIONAL_FILES
OPTIONAL_FILES = \
	doc \
	'ExpressionToToken$$1.class' \
	'ExpressionToToken$$ExpressionScope.class'

JCLASS = $(JSRCS:%.java=%.class)


all: jclass
install: jclass jars


# Get the rest of the rules
include $(ROOT)/mk/ptcommon.mk
