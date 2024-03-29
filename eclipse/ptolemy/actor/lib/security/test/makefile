# Makefile for the Java classes used to test the security actors
#
# @Authors: Christopher Hylands
#
# @Version: $Id: makefile 59403 2010-10-06 15:27:22Z cxh $
#
# @Copyright (c) 2003-2009 The Regents of the University of California.
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

# Location of this directory, relative to the Ptolemy II directory
ME =		ptolemy/actor/lib/security/test

# Root of the Ptolemy II directory
ROOT =		../../../../..

CLASSPATH =	$(ROOT)

# Get configuration info
CONFIG =	$(ROOT)/mk/ptII.mk
include $(CONFIG)

# Tcl sources that are part of the testing infrastructure
TCL_SRCS = \
	testDefs.tcl

JSRCS =

# Non-graphical Java tests written in Tcl
# If there are no tests, we use a dummy file so that the script that builds
# alljtests.tcl works.  If you add a test, be sure to add
# $(JSIMPLE_TESTS) to EXTRA_SRCS
JSIMPLE_TESTS = \
	KeyReader.tcl \
	KeyToken.tcl

# Graphical Java tests.
# If there are no tests, we use a dummy file so that the script that builds
# alljtests.tcl works.  If you add a test, be sure to add
# $(JGRAPHICAL_TESTS) to EXTRA_SRCS
JGRAPHICAL_TESTS = \
	dummy.tcl

EXTRA_SRCS =	$(TCL_SRCS) $(JSRCS) \
	info.tcl \
	KeyReaderBad.xml \
	$(JSIMPLE_TESTS) #$(JGRAPHICAL_TESTS)

# Sources that may or may not be present, but if they are present, we don't
# want make checkjunk to barf on them.
MISC_FILES =	alljtests.tcl \
	auto

# make checkjunk will not report OPTIONAL_FILES as trash
# make distclean removes OPTIONAL_FILES
OPTIONAL_FILES =

JCLASS =	$(JSRCS:%.java=%.class)

# Don't include all or install rules here, we want the user
# to run 'make tests' to run the tests.

tests:: $(EXTRA_SRCS) jclass test_java test_jsimple

test_java: jclass

# PtolemyII-wide test definitions.
KERNEL_TESTDEFS = $(ROOT)/util/testsuite/testDefs.tcl

# These tests do not require a graphics terminal, but do use Jacl and Java
test_jsimple: $(EXTRA_SRCS) jclass $(KERNEL_TESTDEFS) \
		$(ROOT)/ptKeystore alljtests.tcl \
		initialize_key test_auto
	$(JTCLSH) alljtests.tcl

initialize_key:
	rm -f auto/foo.keystore
	@echo "creating auto/foo.keystore"
	CLASSPATH=$(AUXCLASSPATH) \
		$(ROOT)/bin/ptinvoke \
		ptolemy.actor.gui.MoMLSimpleApplication auto/KeyWriter.xml 
	@echo "Note that the key is of type JCEKS, which is not the default"
	@echo "If you get 'Provider fully qualified class name:', then check"
	@echo "which version of keytool you are running.  You should be "
	@echo "running a Sun version, the GNU gcj version will _not_ work" 
	"$(KEYTOOL)" -list -keystore auto/foo.keystore \
                -storepass  this.is.the.storePassword,change.it \
		-storetype JCEKS
	ls -l auto/foo.keystore

# The default keystore is $(PTII)/ptKeystore
$(ROOT)/ptKeystore:
	(cd $(ROOT); $(MAKE) ptKeystore)


# Get the rest of the rules
include $(ROOT)/mk/ptcommon.mk
