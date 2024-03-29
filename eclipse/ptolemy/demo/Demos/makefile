# Makefile for demos use by model that runs lots of demos
#
# @Version: $Id: makefile 43472 2006-08-21 23:16:56Z cxh $
# @Author: Christopher Brooks (makefile only)
#
# @Copyright (c) 2005-2006 The Regents of the University of California.
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
##########################################################################

ME =		ptolemy/demo/Demos

DIRS =

# Root of Ptolemy II directory
ROOT =		../../..

# Get configuration info
CONFIG =	$(ROOT)/mk/ptII.mk
include $(CONFIG)

# Used to build jar files
PTPACKAGE = 	Demos
PTCLASSJAR =	$(PTPACKAGE).jar

OTHER_FILES_TO_BE_JARED = \
	BouncingBall.xml \
	Collisions.xml \
	Controllers.xml \
	EvaderAndPursuer.xml \
	Eye.xml \
	GuardedCount.xml \
	HanoiTower.xml \
	IncreasingRatePoisson.xml \
	Inspection.xml \
	Lorenz.xml \
	MaximumEntropySpectrum.xml \
	MobileFunction.xml \
	MultipleRuns.xml \
	NewtonsCradleAnimated.xml \
	NondeterministicMerge.xml \
	OrderedMerge.xml \
	PythonScale.xml \
	Router.xml \
	SigmaDelta.xml \
	SolarSystem.xml \
	SoundSpectrum.xml \
	TerrainModel.xml \
	TimingParadox.xml \
	TransmitAntennaPattern.xml \
	ViterbiDecoder.xml \
	WirelessSoundDetection.xml

EXTRA_SRCS = \
	$(OTHER_FILES_TO_BE_JARED)


# Sources that may or may not be present, but if they are present, we don't
# want make checkjunk to barf on them.
MISC_FILES = \
	HyVisualDemos

OPTIONAL_FILES = 

all: suball $(EXTRA_SRCS)
install: subinstall jars

# Include rules for directories that contain only subdirectories.
include $(ROOT)/mk/ptcommon.mk
