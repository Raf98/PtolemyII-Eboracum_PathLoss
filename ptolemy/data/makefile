# Makefile for Java Ptolemy Data Token classes
#
# @Authors: Christopher Hylands, Neil Smyth based on a file by Thomas M. Parks
#
# $Id: makefile 57040 2010-01-27 20:52:32Z cxh $
#
# @Copyright (c) 1997-2009 The Regents of the University of California.
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

ME =		ptolemy/data

DIRS = 		expr test type properties unit ontologies

# Root of the Ptolemy II directory
ROOT =		../..

CLASSPATH =	$(ROOT)

# Get configuration info
CONFIG =	$(ROOT)/mk/ptII.mk
include $(CONFIG)

# Used to build jar files
PTPACKAGE = 	data

# Include the .class files from these jars in PTCLASSALLJAR
PTCLASSALLJARS = \
	expr/expr.jar \
	type/type.jar \
	unit/unit.jar

PTCLASSALLJAR = $(PTPACKAGE).jar

JSRCS = \
	AbsentToken.java \
	AbstractConvertibleToken.java \
	AbstractNotConvertibleToken.java \
	ActorToken.java \
	ArrayToken.java \
	AWTImageToken.java \
	BitwiseOperationToken.java \
	BooleanMatrixToken.java \
	BooleanToken.java \
	ComplexMatrixToken.java \
	ComplexToken.java \
	DoubleMatrixToken.java \
	DoubleToken.java \
	EventToken.java \
	FixMatrixToken.java \
	FixToken.java \
	FloatToken.java \
	Function.java \
	FunctionToken.java \
	ImageToken.java \
	IntMatrixToken.java \
	IntToken.java \
	LongMatrixToken.java \
	LongToken.java \
	MatrixToken.java \
	Numerical.java \
	ObjectToken.java \
	OrderedRecordToken.java \
	PetiteToken.java \
	RecordToken.java \
	ScalarToken.java \
	ShortToken.java \
	StringToken.java \
	Token.java \
	TokenUtilities.java \
	UnionToken.java \
	UnsignedByteToken.java \
	UnsizedArrayToken.java \
	UnsizedFixToken.java \
	XMLToken.java

EXTRA_SRCS =	$(JSRCS)

# Sources that may or may not be present, but if they are present, we don't
# want make checkjunk to barf on them.
MISC_FILES =	$(DIRS)

# make checkjunk will not report OPTIONAL_FILES as trash
# make distclean removes OPTIONAL_FILES
OPTIONAL_FILES = \
	doc \
	'FunctionToken$$Function.class' \
	'ActorToken$$ActorType.class' \
	'Token$$1.class'

JCLASS = $(JSRCS:%.java=%.class)


all: jclass
install: jclass jars


# Get the rest of the rules
include $(ROOT)/mk/ptcommon.mk
