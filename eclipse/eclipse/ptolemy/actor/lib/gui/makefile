# Makefile for Java Ptolemy polymorphic actor gui classes
#
# @Authors: Christopher Hylands, based on a file by Thomas M. Parks
#
# @Version: $Id: makefile 56352 2009-11-28 16:15:18Z cxh $
#
# @Copyright (c) 2000-2009 The Regents of the University of California.
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

ME =		ptolemy/actor/lib/gui

DIRS = 		test

# Root of the Java directory
ROOT =		../../../..

# ArrowKeySensor requires this
CLASSPATH =	$(ROOT)$(CLASSPATHSEPARATOR)$(DIVA_JAR)

# Get configuration info
CONFIG =	$(ROOT)/mk/ptII.mk
include $(CONFIG)

# Used to build jar files
PTPACKAGE = 	gui
PTCLASSJAR = 	$(PTPACKAGE).jar

# Keep this list alphabetized.
JSRCS = \
	ArrayPlotter.java \
	ArrayPlotterXY.java \
	ArrowKeyProbe.java \
	ArrowKeySensor.java \
	BarGraph.java \
	Display.java \
	HistogramPlotter.java \
	InteractiveShell.java \
	MatrixViewer.java \
	Plotter.java \
	PlotterBase.java \
	RealTimePlotter.java \
	SequencePlotter.java \
	SequenceScope.java \
	SketchedSource.java \
	SliderSource.java \
	TimedPlotter.java \
	TimedScope.java \
	XYPlotter.java \
	XYScope.java

OTHER_FILES_TO_BE_JARED = \
	XYPlotter.gif

EXTRA_SRCS =	$(JSRCS) $(OTHER_FILES_TO_BE_JARED)

# Sources that may or may not be present, but if they are present, we don't
# want make checkjunk to barf on them.
MISC_FILES =	$(DIRS)

# make checkjunk will not report OPTIONAL_FILES as trash
# make distclean removes OPTIONAL_FILES
OPTIONAL_FILES = \
	'ArrowKeySensor$$ActionListenerExceptionCatcher.class' \
	'ArrowKeySensor$$MyFrame.class' \
	'ArrowKeySensor$$MyFrame$$1.class' \
	'ArrowKeySensor$$MyFrame$$2.class' \
	'ArrowKeySensor$$MyFrame$$3.class' \
	'ArrowKeySensor$$MyFrame$$4.class' \
	'ArrowKeySensor$$MyFrame$$5.class' \
	'ArrowKeySensor$$MyFrame$$6.class' \
	'ArrowKeySensor$$MyFrame$$7.class' \
	'ArrowKeySensor$$MyFrame$$8.class' \
	'ArrowKeySensor$$MyFrame$$FocusMouseListener.class' \
	'ArrowKeySensor$$1.class' \
	'ArrowKeySensor$$2.class' \
	'ArrowKeySensor$$3.class' \
	'ArrowKeySensor$$4.class' \
	'ArrowKeySensor$$5.class' \
	'ArrowKeySensor$$6.class' \
	'ArrowKeySensor$$7.class' \
	'ArrowKeySensor$$8.class' \
	'Display$$1.class' \
	'Display$$DisplayWindow.class' \
	'Display$$DisplayWindowTableau.class' \
	'HistogramPlotter$$1.class' \
	'InteractiveShell$$1.class' \
	'InteractiveShell$$2.class' \
	'InteractiveShell$$ShellFrame.class' \
	'InteractiveShell$$ShellTableau.class' \
	'MatrixViewer$$1.class' \
	'MatrixViewer$$DisplayWindow.class' \
	'MatrixViewer$$MatrixAsTable.class' \
	'Plotter$$1.class' \
	'PlotterBase$$1.class' \
	'PlotterBase$$PlotWindowTableau.class' \
	'PlotterBase$$PlotterPlotFrame.class' \
	'SliderSource$$1.class' \
	'SliderSource$$SliderFrame.class' \
	'SketchedSource$$1.class'

JCLASS= $(JSRCS:%.java=%.class)


all: jclass
install: jclass $(PTCLASSJAR)


# Get the rest of the rules
include $(ROOT)/mk/ptcommon.mk
