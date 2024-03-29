# Makefile for classes that support unit systems
#
# @Authors: Christopher Hylands, Neil Smyth based on a file by Thomas M. Parks
#
# $Id: makefile 57040 2010-01-27 20:52:32Z cxh $
#
# @Copyright (c) 2001-2008 The Regents of the University of California.
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

ME =		ptolemy/moml/unit

DIRS =		test

# Root of the Ptolemy II directory
ROOT =		../../..

CLASSPATH =	$(ROOT)

# Get configuration info
CONFIG =	$(ROOT)/mk/ptII.mk
include $(CONFIG)

# Used to build jar files
PTPACKAGE = 	unit
PTCLASSJAR =	$(PTPACKAGE).jar

JSRCS = \
	Bindings.java  \
	EquationVisitor.java  \
	ExpandPortNames.java  \
	Solution.java  \
	Unit.java  \
	UnitAttribute.java  \
	UnitConstraint.java  \
	UnitConstraints.java  \
	UnitEquation.java  \
	UnitExpr.java  \
	UnitInEquality.java  \
	UnitLibrary.java  \
	UnitPresentation.java  \
	UnitTerm.java

OTHER_FILES_TO_BE_JARED =

EXTRA_SRCS =	UParser.jjt $(JSRCS) $(OTHER_FILES_TO_BE_JARED)

# Sources that may or may not be present, but if they are present, we don't
# want make checkjunk to barf on them.
# if $(DIRS) includes demo, then do not set MISC_FILES= $(DIRS) 
MISC_FILES = test

# OPTIONAL_JSRCS files are created by javacc.
# javadoc generates html for files in $(OPTIONAL_JSRCS)
#	ASCII_CharStream.java 
OPTIONAL_JSRCS = \
	ParseException.java  \
	SimpleCharStream.java  \
	Token.java  \
	TokenMgrError.java  \
	UParser.java  \
	UParserConstants.java  \
	UParserTokenManager.java

# OPTIONAL_JSRCS depends on UParser.java, but we want javadoc
# to generate docs for UParser, so we modify the javadoc
# rule to also operate on DERIVED_JSRCS
DERIVED_JSRCS = UParser.java


# make checkjunk will not report OPTIONAL_FILES as trash
# make distclean removes OPTIONAL_FILES
OPTIONAL_FILES = \
	demo \
	doc \
	$(OPTIONAL_JSRCS) \
	UParser.java \
	'Solution$$1.class' \
	'Solution$$Index.class' \
	'Solver$$1.class' \
	'Solver$$Index.class' \
	'UParser$$1.class' \
	'UParser$$JJCalls.class' \
	'UParser$$LookaheadSuccess.class' \
	'UnitLibrary$$UnitNameExprPair.class'


JCLASS =  \
	$(JSRCS:%.java=%.class) \
	UParser.class \
	$(OPTIONAL_JSRCS:%.java=%.class)


all: $(OPTIONAL_JSRCS) $(JCLASS)
install: jclass $(PTCLASSJAR)

UParser.java: UParser.jjt
	@echo "This command runs JavaCC, which can be downloaded from"
	@echo "http://javacc.dev.java.net/"
	@echo "See \$$PTII/doc/install.htm for details."
	@echo "You only need install JavaCC if you modify UParser.jj"
	@echo "UParser.java is derived from UParser.jj"
	@echo "If you don't have JavaCC, you can workaround this with"
	@echo "   touch UParser.jj UParser.java"
	CLASSPATH="$(CLASSPATH)" 
	$(JAVACC) UParser.jjt

# This rule is useful for cvs committing the derived files
# For example:
# cvs commit -m "Updated MatrixParser." `make echo_OPTIONAL_JSRCS`
echo_OPTIONAL_JSRCS:
	@echo $(OPTIONAL_JSRCS) UParser.jj UParser.java

# Get the rest of the rules
include $(ROOT)/mk/ptcommon.mk
