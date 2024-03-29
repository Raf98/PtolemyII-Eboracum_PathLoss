# Makefile for Wireless domain actors library
#
# @Authors: Christopher Hylands Brooks
#
# @Version: $Id: makefile 57040 2010-01-27 20:52:32Z cxh $
#
# @Copyright (c) 2003-2008 The Regents of the University of California.
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

ME =		ptolemy/domains/wireless/lib

DIRS = 		test network tinyOS

# Root of the Java directory
ROOT =		../../../..

CLASSPATH =	$(ROOT)

# Get configuration info
CONFIG =	$(ROOT)/mk/ptII.mk
include $(CONFIG)

# Used to build jar files
PTPACKAGE = 	lib
PTCLASSJAR =	$(PTPACKAGE).jar

# Include the .class files from these jars in PTCLASSALLJAR
PTCLASSALLJARS =

# We are not shipping network
#		network/network.jar
#		tinyOS/tinyOS.jar

# PTCLASSALLJAR = $(PTPACKAGE).jar

JSRCS = \
    CollisionDetector.java \
	DelayChannel.java \
	ErasureChannel.java \
	GetProperties.java \
	GraphicalLocator.java \
	LimitedRangeChannel.java \
	LinkVisualizer.java \
	Locator.java \
	NodeRandomizer.java \
	PowerLossChannel.java \
	SetProperties.java \
	TerrainProperty.java \
	TransmitPropertyTransformer.java \
	Triangulator.java \
	WiredToWireless.java \
	WirelessToWired.java

OTHER_FILES_TO_BE_JARED = \
	channels.xml \
	wirelessentities.xml \
	LinkVisualizerIcon.xml

EXTRA_SRCS =	$(JSRCS) $(OTHER_FILES_TO_BE_JARED)

# Sources that may or may not be present, but if they are present, we don't
# want make checkjunk to barf on them.
MISC_FILES = 	test

# make checkjunk will not report OPTIONAL_FILES as trash
# make realclean removes OPTIONAL_FILES
OPTIONAL_FILES = \
	doc \
	network \
	tinyOS \
	'CollisionDetector$$1.class' \
	'CollisionDetector$$Reception.class' \
	'DelayChannel$$1.class' \
	'DelayChannel$$Reception.class' \
	'LinkVisualizer$$1.class' \
	'LinkVisualizer$$2.class' \
	'LinkVisualizer$$_LinkVisualizerThread.class' \
	'NodeRandomizer$$1.class' \
	'TerrainProperty$$1.class'

JCLASS = $(JSRCS:%.java=%.class)

all: jclass
install: jclass jars


# Get the rest of the rules
include $(ROOT)/mk/ptcommon.mk







