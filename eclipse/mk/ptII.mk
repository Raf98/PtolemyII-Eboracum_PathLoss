# Makefile for Ptolemy II standalone distribution
#
# Version identification:
# $Id: ptII.mk.in 59470 2010-10-11 16:20:06Z cxh $
#
# Copyright (c) 1996-2010 The Regents of the University of California.
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
# Date of creation: 7/31/96
# Author: Christopher Hylands

# NOTE: Don't edit this file if it is called ptII.mk, instead
# edit ptII.mk.in, which is read by configure

# Every Ptolemy II makefile should include ptII.mk

# Variables with @ around them are substituted in by the configure script

# Default top-level directory.  Usually this is the same as $PTII
prefix =	/home/rafael/Documentos/EngenhariaDeComputacao/Semestre8/TCC1/ptII8.0.1

# Usually the same as prefix.  exec_prefix is part of the autoconf standard.
exec_prefix =	${prefix}

# Source directory we are building from.
srcdir =	.



BIN_INSTALL_DIR =	$(exec_prefix)/bin

# The home of the Java Developer's Kit (JDK)
# Generating Java documentation uses this makefile variable
# The line below gets substituted by the configure script
# Under Cygwin, PTJAVA_DIR _will_ contain /cygdrive/c
# NOTE: If your javac compiler is in /usr/bin, then PTJAVA_DIR is
# likely to be set to /usr.  Consider using PTJAVA_HOME if
# you are looking for Java jar files.  Note that PTJAVA_HOME is set to
# the jre/ directory, wheras PTJAVA_DIR is set to the directory 
# above the jre/ directory.
PTJAVA_DIR = 	/usr

# Value of the java.home Java property, which usually refers to the jre.
# Under Cygwin, PTJAVA_HOME will not contain /cygdrive/c
PTJAVA_HOME =	/usr/lib/jvm/java-14-openjdk-amd64

# Location of rt.jar, usually $(PTJAVA_HOME)/lib/rt.jar
# However, we have to be careful of backslashes and /cygwin
# Soot uses this variable to find java.lang.Object
JAVA_SYSTEM_JAR = 

# Location of jce.jar, usually $(PTJAVA_HOME)/lib/jce.jar
# Java 1.5 has javax.crypto.Cipher in jce.jar
# However, we have to be careful of backslashes and /cygwin
# Soot uses this variable to find java.lang.Object
JAVAX_CRYPTO_JAR = ${PTJAVA_HOME}/lib/jce.jar

# JDK Version from the java.version property
JVERSION =	14.0.2

# Java CLASSPATH separator
# For Unix, this would be :
# For Cygwin, this would be ;
CLASSPATHSEPARATOR = :

# The home of the Java Foundation Classes (JFC) aka Swing
JFCHOME = 	/opt/swing

# CLASSPATH necessary to find the swing.jar file for JFC
JFCCLASSPATH = $(JFCHOME)/swing.jar

# The variables below are for the SunTest JavaScope code coverage tool
# See http://www.suntest.com/JavaScope
# The 'jsinstr' command, which instruments Java code.
JSINSTR = 	jsinstr
JSINSTRFLAGS = 	-IFLUSHCLASS=true
# The 'jsrestore' command which uninstruments Java code.
JSRESTORE =	jsrestore
# The pathname to the JavaScope.zip file
JSCLASSPATH = 	/usr/local/JavaScope/JavaScope.zip


# KVM is the JDK for PalmOS, see
# http://java.sun.com/products/kvm
KVM_CLASSES = $(KVM_DIR)/bin/api/classes

# Directory that contains the kvm used by PalmOS
KVM_DIR = ${PTII}/vendors/sun/j2me_cldc

# Directory the Palm Pilot Hot Sync program looks for downloads
PALM_ADD_ON_DIR = /Program\ Files/Palm/Add-on

# Set to the location of the QTJava.zip file
QTJAVA_ZIP = 

# Jar files used Soot - a Java Optimization Framework
SOOT_CLASSES = ${SOOT_DIR}/sootclasses.jar${CLASSPATHSEPARATOR}${SOOT_DIR}/jasminclasses.jar${CLASSPATHSEPARATOR}${SOOT_DIR}/polyglotclasses-1.3.2.jar${CLASSPATHSEPARATOR}${JAVA_SYSTEM_JAR}${CLASSPATHSEPARATOR}${JAVAX_CRYPTO_JAR}

# Directory that contains the Soot installation
SOOT_DIR = /home/rafael/Documentos/EngenhariaDeComputacao/Semestre8/TCC1/ptII8.0.1/lib

# Tini is a single board Java processor from Dallas Semi (www.dalsemi.com)
TINI_CLASSES = ${TINI_DIR}/bin/tini.jar

# Directory that contains the TINI installation
TINI_DIR = ${PTII}/vendors/dalsemi/tini

# Location of tools.jar, usually $(PTJAVA_HOME)/../lib/tools.jar
TOOLS_JAR =	/usr/lib/jvm/java-14-openjdk-amd64/../Classes/classes.jar

# Waba is another JDK for PalmOS, see
# http://www.wabasoft.clom
WABA_CLASSES = $(WABA_DIR)/classes

# Directory that contains the waba used by PalmOS
WABA_DIR = ${PTII}/vendors/misc/waba/wabasdk

# Lejos is a JDK for Lego Mindstorms, see
# http://lejos.sourceforge.net
LEJOS_DIR = ${PTII}/vendors/lejos/lejosBeta3

# Set to the directory that contains xpat.h and used in $PTII/lbnl/lib/util
LIBEXPAT_INCLUDE_DIR = /usr/include

# Set to the directory that contains libxpat and used in $PTII/lbnl/lib/util
LIBEXPAT_LIB_DIR = 

########## You should not have to change anything below this line ######

# Set to backtrack if Java 1.5 or later is present used in ptolemy/makefile
PTBACKTRACK_DIR =		backtrack

# Set to eclipse and used in ptolemy/backtrack/makefile if Eclipse was found
PTBACKTRACK_ECLIPSE_DIR =	

# Plugin Eclipse jar files, used by $PTII/doc
PTBACKTRACK_ECLIPSE_DOC_JARS =	

# Eclipse jar files used by ptolemy/backtrack 
PTBACKTRACK_ECLIPSE_JARS =	

# Ptolemy II packages that use backtrack/eclipse packages
# PTBACKTRACK_ECLIPSE_PACKAGES is used in $PTII/doc/makefile
PTBACKTRACK_ECLIPSE_PACKAGES =  

# Used by Mescal. Set to -I$PTII/mescal/include or -I/usr/local/include
# if we found boost/static_assert.hpp
PTBOOST_INCLUDE =	""

# Directory that contains the Cal jar file ptCal.jar
# used by bin/ptinvoke.in
CALTROP_DIR = /home/rafael/Documentos/EngenhariaDeComputacao/Semestre8/TCC1/ptII8.0.1/lib

# Directory that contains chic.jar.
# Chic is a modular verifier for behavioral compatibility checking of
# software and hardware components.
# Used in bin/ptinvoke.in
CHIC_DIR = ${PTII}/lib

# Directory that contains colt.jar
# Colt is a "Open Source Libraries for High Performance Scientific
# and Technical Computing in Java" used in bin/ptinvoke.in
COLT_DIR = /home/rafael/Documentos/EngenhariaDeComputacao/Semestre8/TCC1/ptII8.0.1/vendors/misc/colt

# Directory that contains the CaffeineMark Java Benchmark kit
# used by C code generation in copernicus/c
CMKIT_DIR = ${PTII}/vendors/cm

# Location of Eclipse, which is used by ptolemy/backtracking
ECLIPSE_DIR = /snap/bin

# Location of GNU Awk, used by maven
GAWK = /usr/bin/awk

# Directory that contains gc.h, used in copernicus/c
GC_INCLUDE_DIR = 

# GC_LD_DIRECTIVE is set to the values to pass to cc or ld if
# GC_malloc() can be found either in the default compiler location
# or in $PTII/lib.
# PTGC_LD_DIRECTIVE is used in ptolemy/copernicus/c/
GC_LD_DIRECTIVE = 

# The 'javac' compiler.
JAVAC = 	/usr/bin/javac

# Flags to pass to javac.  Usually something like '-g -depend'
JDEBUG =	-g
JOPTIMIZE =	-O
JFLAGS = 	$(JDEBUG) $(JOPTIMIZE)

# The javadoc binary, used to create html documentation of java classes.
JAVADOC = 	/usr/bin/javadoc

# The javah binary, used by jni for matlab.
JAVAH =		

# The javaws command is used to test Webstart JNLP files.
JAVAWS =	

# -breakiterator is only present in jdk1.4 and later
JDOCBREAKITERATOR = -breakiterator

# Increase the amount of memory that javadoc uses.
JDOCMEMORY =	-J-Xmx1000m

# Allow @Pt.AcceptedRating and @Pt.ProposedRating tags.
JDOCTAG = 	

# Javadoc taglet
# Usually -tagletpath $(PTII) -taglet doc.doclets.RatingTaglet
JDOCTAGLET = 	

# Doccheck is a doclet that checks for bugs
# Location of the doccheck jar file, available from
# http://java.sun.com/developer/earlyAccess/doccheck/
DOCCHECKJAR =	$(PTII)/vendors/sun/doccheck1.2b2/doccheck.jar
JDOCCHECKFLAG = -doclet com.sun.tools.doclets.doccheck.DocCheck  \
	-docletpath $(DOCCHECKJAR)

# caltrop uses the assert keyword, which is new in Java 1.4
# ptalon uses generics, which are new in Java 1.5, so if
# Java 1.5 is present, use -source 1.5
JDOCSOURCEFLAGS = 

# Doccheck is a doclet that checks for bugs, see
# http://java.sun.com/developer/earlyAccess/doccheck/
JDOCCHECKFLAGS = $(JDOCBREAKITERATOR) $(JDOCMEMORY) $(JDOCSOURCEFLAGS)

# Flags to pass to javadoc.
JDOCFLAGS = 	-author -version -private $(JDOCCHECKFLAGS) $(JDOCTAG) $(JDOCTAGLET)

# The jar command, used to produce jar files, which are similar to tar files
JAR =		/usr/bin/jar

# If Jar fails with a message about stack size being too small, 
# set JAR_FLAGS to -JXss20m
JAR_FLAGS = 

#The jar signer command is used to create signed jar files for Webstart JNLP files
JARSIGNER =	/usr/bin/jarsigner

# Command to run that indexes a jar file named tmp.jar
# Usually it looks like '"$(JAR)" -i $@'
JAR_INDEX =	"$(JAR)" $(JAR_FLAGS) -i $@

# The 'java' interpreter.
JAVA =		/usr/bin/java

# Flags to use with java.  Try 'java -help' or 'java -X'
# A common value is -Xmx100m to set the maximum stack size
JAVAFLAGS = 

# Jar files for Java Advanced Imaging (JAI) used by
# ptolemy/actor/lib/jai/makefile
JAI_JARS =	

# If Jini is present, the set to yes, otherwise, set to no
JINI_PRESENT =  yes

# ImageJ Jar file, used to compile ptolemy/domains/gr/lib/vr
IMAGEJ_JAR =	

# Jini home directory, see http://www.sun.com/jini/
JINI_DIR =	/home/rafael/Documentos/EngenhariaDeComputacao/Semestre8/TCC1/ptII8.0.1/ptolemy/distributed/jini

# The directory of the Jini distribution that contains the jar files 
JINI_LIB =	$(JINI_DIR)/jar

# Do not include a trailng $(CLASSPATHSEPARATOR) here, or
# we may run into problems compiling under Solaris8 with JavaScope
# because we end up with a classpath with an empty element ::
# Include jini-ext.jar for javadoc.
JINI_JARS =     $(JINI_LIB)/jini-core.jar$(CLASSPATHSEPARATOR)$(JINI_LIB)/jini-ext.jar

# Jar files for Java Media Framework (JMF) used by ptolemy/actor/lib/makefile
JMF_JARS =	

# Java Native Access jar file (jna.jar).  See
# https://jna.dev.java.net/.  Used in ptolemy/actor/lib/opencv
JNA_JAR =	

# Jar file that contains Joystick interface,
# see http://sourceforge.net/projects/javajoystick/
JOYSTICK_JAR =	${PTII}/vendors/misc/joystick/Joystick.jar

# Jython home directory that contains jython.jar, see http://www.jython.org
JYTHON_DIR =    /home/rafael/Documentos/EngenhariaDeComputacao/Semestre8/TCC1/ptII8.0.1/lib

# JXTA home directory that contains jxta.jar, see http://www.jxta.org
JXTA_DIR =	${PTII}/vendors/sun/jxta

# Jar files used by JXTA
JXTA_JARS = $(JXTA_DIR)/jxta.jar$(CLASSPATHSEPARATOR)$(JXTA_DIR)/log4j.jar$(CLASSPATHSEPARATOR)$(JXTA_DIR)/beepcore.jar$(CLASSPATHSEPARATOR)$(JXTA_DIR)/jxtasecurity.jar$(CLASSPATHSEPARATOR)$(JXTA_DIR)/cryptix-asn1.jar$(CLASSPATHSEPARATOR)$(JXTA_DIR)/cryptix32.jar$(CLASSPATHSEPARATOR)$(JXTA_DIR)/jxtaptls.jar$(CLASSPATHSEPARATOR)$(JXTA_DIR)/minimalBC.jar

# keytool command used to generate a certificate for testing actor.lib.security actors
KEYTOOL =	/usr/bin/keytool

# The Kieler jar file, used to layout models
KIELER_JAR =	/home/rafael/Documentos/EngenhariaDeComputacao/Semestre8/TCC1/ptII8.0.1/lib/kieler.jar

# Jar files for Open Computer Vision (OpenCV), used in ptolemy.actor.lib.opencv/makefile
OPENCV_JAR =	

# The major type of OS we are running under.
# Under all forms Windows, this should be Windows; ynder Linux: Linux, etc.
# Used in ptolemy/matlab/makefile
MAJOR_OS_NAME =	Linux

# Location of the Matlab directory.  The matlab binary will be
# found at $(MATLAB_DIR)/bin/matlab
MATLAB_DIR = 	

# Location of Matlab's engine libraries (libeng.so, libmx.so)
MATLAB_LIBDIR =	

# Location of Matlab's engine libraries (libeng.so, libmx.so) (64 bit)
MATLAB_64LIBDIR =	

# Set to the BSH jar file and used to compile org/mlc
PTBSH_JAR = /home/rafael/Documentos/EngenhariaDeComputacao/Semestre8/TCC1/ptII8.0.1/lib/bsh-2.0b4.jar

# Set to mlc and used in $PTII/org/makefile if Java Bean Shell is found.
PTBSH_MLC_DIR = mlc

# Set to the packages that require bsh and used in $PTII/doc/makefile
PTBSH_PACKAGES = com.jgoodies.forms.builder com.jgoodies.forms.debug com.jgoodies.forms.factories com.jgoodies.forms.layout com.jgoodies.forms.util org.mlc.swing.example org.mlc.swing.layout ptolemy.actor.gui.run

# PTBSH_RUN_DIR is set to "run" and used in
# $PTII/ptolemy/actor/gui/makefile if bsh.jar was found.
PTBSH_RUN_DIR= run

# Set to caltrop and used in $PTII/ptolemy/makefile if Cal was found.
PTCALTROP_DIR = caltrop

# Set to gcc if gcc was found and used in $PTII/ptolemy/matlab/makefile.
PTCC =	/usr/bin/gcc

# Set to mingw32-gcc or gcc and used in $PTII/ptolemy/matlab/makefile and jni
PTCCJNI = /usr/bin/gcc

# Set to chic and used in $PTII/ptolemy/makefile if chic was found.
PTCHIC_DIR = 

# PTCM_DIR is set to cm and used in $PTII/ptolemy/copernicus/c/test/makefile
# if the CaffeineMark Java Benchmark kit is found.
PTCM_DIR =	

# Optional codegen package that are in the devel tree, but not shipped.
# Used in doc/makefile
PTCODEGEN_OPTIONAL_PACKAGES =	

# Set to colt and used in $PTII/ptolemy/actor/lib/makefile if colt was found.
PTCOLT_DIR =    colt

# Colt jar files.  We ship $PTII/lib/ptcolt.jar, which is a subset
# of $PTII/vendors/misc/colt.jar
PTCOLT_JARS =	    /home/rafael/Documentos/EngenhariaDeComputacao/Semestre8/TCC1/ptII8.0.1/lib/ptcolt.jar

# Ptolemy II packages that use COLT
# PTCOLT_PACKAGES is used in ptII/doc/makefile
PTCOLT_PACKAGES =   ptolemy.actor.lib.colt

# Set to comm and used in
# $PTII/ptolemy/actor/lib/makefile if the Java Communications API was found
#
PTCOMM_DIR = 

# Ptolemy II packages that use the Java Serial Communication facility
# PTCOMM_PACKAGES is used in ptII/doc/makefile
PTCOMM_PACKAGES =	  	

# Set to copernicus and used in $PTII/ptolemy/makefile if Soot was found
PTCOPERNICUS_DIR = copernicus

# PTCUNIT_DIR is set to test and used in 
# ptII/ptolemy/codegen/c/kernel/type/test/makefile
# if CUnit is found
PTCUNIT_DIR =		

# PTDATABASE_DIR is set to database and used in
# $PTII/ptolemy/actor/lib/makefile if ojdbc6.jar was found.
PTDATABASE_DIR =	database

# PTDATABASE_JNLP_JARS is set to the database jars that need to be signed
# and used in mk/jnlp.mk
PTDATABASE_JNLP_JARS =	ptolemy/actor/lib/database/database.jar ptolemy/domains/space/space.jar ptolemy/actor/lib/database/ojdbc6.jar ptolemy/actor/lib/database/mysql-connector-java-5.1.6-bin.jar

# PTDATABASE_PACKAGES is set if ojdbc6.jar was found and 
# used in ptII/doc/makefile
PTDATABASE_PACKAGES =	ptolemy.actor.lib.database

# Set to distributed and used in
# $PTII/ptolemy/makefile if jini was found
PTDISTRIBUTED_DIR = distributed

# Set to jini jar file and used
# $PTII/ptolemy/distributed/*/makefile if jini was found
PTDISTRIBUTED_JARS = :/home/rafael/Documentos/EngenhariaDeComputacao/Semestre8/TCC1/ptII8.0.1/ptolemy/distributed/jini/jar/jini-core.jar:/home/rafael/Documentos/EngenhariaDeComputacao/Semestre8/TCC1/ptII8.0.1/ptolemy/distributed/jini/jar/jini-ext.jar:/home/rafael/Documentos/EngenhariaDeComputacao/Semestre8/TCC1/ptII8.0.1/ptolemy/distributed/jini/jar/sun-util.jar

# Ptolemy II packages that use the distributed
# PTDISTRIBUTED_PACKAGES is used in ptII/doc/makefile
PTDISTRIBUTED_PACKAGES = ptolemy.distributed.actor.lib ptolemy.distributed.actor ptolemy.distributed.client ptolemy.distributed.common ptolemy.distributed.domains.sdf.kernel ptolemy.distributed.rmi ptolemy.distributed.util

# PTDOCLETS_DIR is set to doclets and used in 
# $PTII/doc if tools.jar can be found.
PTDOCLETS_DIR =		 	

# PTEXCEL_DIR is set to excel and used in
# $PTII/ptolemy/actor/lib/makefile if jxl was found
PTEXCEL_DIR=	

# PTEXCEL_PACKAGES is used in doc/makefile
PTEXCEL_PACKAGES=	

# Set to fuzzy and used in
# $PTII/ptolemy/actor/lib/logic/makefile if fuzzy/FuzzyEngine.class was found
PTFUZZY_DIR = 

# The FuzzyEngine directory, usually
# $PTII/vendors/misc/FuzzyEngine.  Available from
# http://people.clarkson.edu/~esazonov/FuzzyEngine.htm
PTFUZZYENGINE_DIR = ${PTII}/vendors/misc/FuzzyEngine

# PTFUZZYLOGIC_PACKAGES is used in ptII/doc/makefile if fuzzy/FuzzyEngine.class is found
PTFUZZY_PACKAGES = 

# Location of the local $PTII directory as a file:/// URL
# This variable is used with the Java Network Launching Protocol files
PTII_LOCALURL = file:////home/rafael/Documentos/EngenhariaDeComputacao/Semestre8/TCC1/ptII8.0.1

# JAVA3D_JARS is the jar files to use with Java 3D.  Usually, this is
# empty, because most installations have Java 3D in the jdk
# jre/lib/ext directory, but some do not. If --with-java3D was used
# with configure, then JAVA3D_JARS will be set to include the jars.
JAVA3D_JARS =	

# Set to gr and used in
# $PTII/ptolemy/domains/makefile if Java 3D was found
PTJAVA3D_DIR = 

# Set to kvm and used in $PTII/ptolemy/makefile if the PalmOS KVM was found
PTKVM_DIR =	

# JavaCC is the Java Compiler Compiler which is used by ptolemy.data.expr

# The default location is $(PTII)/vendors/sun/JavaCC
JAVACC_DIR =	/usr
# Under Unix:
# JJTREE =	$(JAVACC_DIR)/bin/jjtree
# JAVACC =	$(JAVACC_DIR)/bin/javacc
# Under Cygwin32 NT the following should be used and JavaCC.zip must be in
# the CLASSPATH
# JJTREE = 	$(JAVA) COM.sun.labs.jjtree.Main
# JAVACC = 	$(JAVA) COM.sun.labs.javacc.Main

JJTREE =	"$(JAVACC_DIR)/bin/jjtree"
JAVACC =	"$(JAVACC_DIR)/bin/javacc"

# The JavaCV jar file, used in actor/lib/opencv/javacv for computer vision
JAVACV_JAR =	

# Mysql Java Database jar (mysql-connector-java-5.1.6-bin.jar),
# used by domains/space and actor/lib/database
MYSQL_JAR =		/home/rafael/Documentos/EngenhariaDeComputacao/Semestre8/TCC1/ptII8.0.1/ptolemy/actor/lib/database/mysql-connector-java-5.1.6-bin.jar

# Oracle Java Database jar (ojdbc6.jar)
ORACLE_OJDBC_JAR = 	/home/rafael/Documentos/EngenhariaDeComputacao/Semestre8/TCC1/ptII8.0.1/ptolemy/actor/lib/database/ojdbc6.jar

# Set to $PTII/lib if $PTII/lib/mapss.jar was found and used
# in $PTII/bin/ptinvoke.in
PSDF_DIR =	/home/rafael/Documentos/EngenhariaDeComputacao/Semestre8/TCC1/ptII8.0.1/lib


# Set to -I$PTII/mescal/include or -I/usr/local/include if gmp.h is found
# and used in $PTII/mescal/relsat
# FIXME: There could be problems if PTII have spaces
PTGMP_INCLUDE = 	

# Set to -Wl,-R$PTII/mescal/lib or -Wl,-R/usr/local/lib if the gmp
# libraries are found and used in $PTII/mescal/relsat
PTGMP_LD_FLAGS =        

# Set to -I$PTII/mescal/lib or -I/usr/local/lib if the gmp libraries are found
# and used in $PTII/mescal/relsat
PTGMP_LIB =	

# Name of the jar file that includes the GR domain if Java 3D was found.
# Used in domains/makefile.
PTGRDOMAIN_JAR =	

# Ptolemy II packages that use the Java 3D facility
# PTGR_PACKAGES is used in ptII/doc/makefile
PTGR_PACKAGES =		

# Set to gro and used in
# $PTII/ptolemy/domains/makefile if the Java OpenGL (Jogl) interface was found.
PTGRO_DIR =		

# PTGRO_PACKAGES is used in ptII/doc/makefile if Jogl was found.
PTGRO_PACKAGES =	

# Set to itextpdf and used in
# $PTII/ptolemy/vergil/basic/makefile if iText.jar was found
PTITEXTPDF_DIR = 	

# iText PDF jar file.  iText is from
# http://itextpdf.com
PTITEXTPDF_JAR =	${PTII}/vendors/misc/itext/iText.jar

# PTITEXTPDF_PACKAGES is used in ptII/doc/makefile if iTextjar is found
PTITEXTPDF_PACKAGES =	

# Set to ptjacl and used in
# $PTII/ptolemy/actor/gui/makefile if ptjacl.jar was found 
PTJACL_DIR =	ptjacl

# Jar file that contains Jacl
PTJACL_JAR =	/home/rafael/Documentos/EngenhariaDeComputacao/Semestre8/TCC1/ptII8.0.1/lib/ptjacl.jar

# Jar files used in configs/test/makefile and vergil/test/makefile
PTJACL_JARS =   $(DIVA_JAR)$(CLASSPATHSEPARATOR)$(JYTHON_DIR)/jython.jar$(CLASSPATHSEPARATOR)$(JXTA_JARS)$(CLASSPATHSEPARATOR)$(SOOT_CLASSES)$(CLASSPATHSEPARATOR)$(PTCOLT_JARS)$(CLASSPATHSEPARATOR)

# jtclsh script to run Jacl for the test suite.
# We could use bin/ptjacl here, but instead we start it from within
# make and avoid problems
# configure sets "$(JAVA)" -Xmx1000M $(JAVAFLAGS) "-Dptolemy.ptII.dir=$(PTII)" $(JTCLSHFLAGS) tcl.lang.Shell to include JTCLSHFLAGS
# JTCLSHFLAGS gets set to -Dptolemy.ptII.isRunningNightlyBuild=true
# when we are running the nightly build.
JTCLSH =	CLASSPATH="$(CLASSPATH)$(AUXCLASSPATH)$(CLASSPATHSEPARATOR)$(PTJACL_JAR)" "$(JAVA)" -Xmx1000M $(JAVAFLAGS) "-Dptolemy.ptII.dir=$(PTII)" $(JTCLSHFLAGS) tcl.lang.Shell

# Saxon is the XSLT and XQuery Processor (http://saxon.sourceforge.net/)
# At runtime, ptolemy.util.XSLTUtilities, caltrop and hsif uses Saxon.
SAXON_JAR =	$(ROOT)/lib/saxon8.jar$(CLASSPATHSEPARATOR)$(ROOT)/lib/saxon8-dom.jar

# Set to jai and used in 
# $PTII/ptolemy/actor/lib/makefile if Java Advanced Imaging was found
PTJAI_DIR =	

# Ptolemy II packages that use Java Advanced Imaging
# PTJAI_PACKAGES is used in ptII/doc/makefile
PTJAI_PACKAGES =	

# Set to javacv and used in
# $PTII/ptolemy/actor/lib/opencv/makefile if JavaCV was found
PTJAVACV_DIR =	

# Ptolemy II packages that use JavaCV.
# PTJAVACV_PACKAGES is used in ptII/doc/makefile
PTJAVACV_PACKAGES =	 

# PTJMF_DIR is set to jmf if the JMF directory is found
PTJMF_DIR =     

# Ptolemy II packages that use Java Media Framwork
# PTJMF_PACKAGES is used in ptII/doc/makefile
PTJMF_PACKAGES =	

# JNI architecture, used to compile C files
PTJNI_ARCHITECTURE =	linux

# set to jni include directory
PTJNI_INCLUDE =    /usr/lib/jvm/java-14-openjdk-amd64/../include

# Set to -ldl for use by jni/launcher/makefile under Linux
PTJNI_DL_LIBRARY = -ldl

# JNI lib architecture, used to run jni/launcher
PTJNI_LIB_ARCHITECTURE = amd64

# Flag to use with TinyOS under Cygwin (-mno-cygwin)
#PTJNI_NO_CYGWIN =	
# Under Windows with Cygwin-1.3.22 and gcc-3.2, we do not use -mno-cygwin
PTJNI_NO_CYGWIN =

# GCC shared flag.  Usually -shared, except on the mac, where it is -dynamiclib
PTJNI_GCC_SHARED_FLAG = 	-shared

# JNI shared library C compiler flag, under Solaris this would be -fPIC.
PTJNI_SHAREDLIBRARY_CFLAG =	-fPIC

# JNI shared library linker flag, under Solaris this would be -fPIC.
PTJNI_SHAREDLIBRARY_LDFLAG =	-fPIC

# JNI shared library prefix, under Solaris this would be lib.
PTJNI_SHAREDLIBRARY_PREFIX =	lib

# JNI shared library suffix, under Windows this would be dll.
PTJNI_SHAREDLIBRARY_SUFFIX =	so

# JNI libraries needed to link, such as -lcygwin (Used by Viptos) 
PTJNI_LIBRARIES = 

# Java OpenGL (Jogl) directory.  Jogl is from
# https://jogl.dev.java.net/
PTJOGL_DIR =			${PTII}/vendors/sun/jogl

# Java OpenGL (Jogl) jar files used by the gro domain.
PTJOGL_JARS=			

# Set to joystick if Joystick.jar was found.
PTJOYSTICK_DIR =     

# Ptolemy II packages that use the Java Joystick facilty
# PTJOYSTICK_PACKAGES is used in ptII/doc/makefile
PTJOYSTICK_PACKAGES =	

# Used in ptolemy/actor/lib/excel/makefile
PTJXL_JAR=	

# PTJYTHON_DIR is set to python (FIXME: should be jython) and used in
# $PTII/ptolemy/actor/lib/makefile if Jython was found.
PTJYTHON_DIR=	python

# Ptolemy II packages that use Python
# PTJYTHON_PACKAGES is used in ptII/doc/makefile
PTJYTHON_PACKAGES = ptolemy.actor.lib.python ptolemy.actor.gui.python

# Set to jxta and used in
# $PTII/actor/lib/makefile if jxta.jar was found
PTJXTA_DIR =	

# Set to opencv and used in 
# $PTII/ptolemy/actor/lib/makefile if OpenCV Open Computer Vision was found
PTOPENCV_DIR = 

# Ptolemy II packages that use OpenCV
# PTOPENCV_PACKAGES is used in ptII/doc/makefile
PTOPENCV_PACKAGES = 

# PTCGOPENCV_DIR is set to OpenCVTracking and used in 
# $PTII/ptolemy/codegen/demo/makefile if opencv was found
PTOPENCVCG_DIR = OpenCVTracking

# Directory that contains cv.h.
# Used in $PTII/ptolemy/codegen/demo/makefile/OpenCVTracking
PTOPENCV_INCLUDE_DIR = 

# Set to kieler and used in
# $PTII/ptolemy/vergil/basic/layout/makefile if kieler was found
PTKIELER_DIR =	kieler

# Set to lbnl and used in $PTII/makefile if libxpat was found.
PTLBNL_DIR = 	

# Set to the lbnl jars that need to be signed if libexpat was found
# and used in jnlp.mk.
PTLBNL_JARS =	

# PTLBNL_PACKAGES is set if libexpat was found and
# used in ptII/doc/makefile
PTLBNL_PACKAGES =	

# Set to lego and used in $PTII/ptolemy/apps/makefile if
# the Java Communications API was found
PTLEGO_DIR =	

# Set to lejos and used in $PTII/ptolemy/apps/makefile if
# the Java Communications API and Lejos was found
PTLEJOS_DIR =	

# Set to util and used in $PTII/lbnl/lib/util/makefile if libxpat was found
PTLIBEXPAT_DIR =	

# Set to gcc or cl and used in
# $PTII/ptolemy/matlab/makefile if the Matlab was found.
PTMATLAB_CC =	

# Set to -m64 under Solaris if libs are in bin/sol64
# and used in $PTII/ptolemy/matlab/makefile if the Matlab was found.
PTMATLAB_CC_FLAGS = 

# Set to matlab and used in $PTII/ptolemy/makefile if
# matlab was found.
PTMATLAB_DIR =	

# Set to the matlab jars that need to be signed if matlab was found.
# Used in jnlp.mk
PTMATLAB_JARS = 

# Linker args for Matlab
# $PTII/ptolemy/matlab/makefile
PTMATLAB_LD_ARGS = 

# Linker args for Matlab
# $PTII/ptolemy/matlab/makefile (64 bit)
PTMATLAB_64LD_ARGS = 

# Used in $PTII/doc/makefile
PTMATLAB_PACKAGES = 

# Set to mescal and used in $PTII/makefile 
# if $PTII/mescal was found
PTMESCAL_DIR=	

# Set to pdf and used in ptolemy/vergil/makefile if
# PDFRenderer.jar is found.
PTPDFRENDERER_DIR = pdfrenderer

# Jar file used in ptolemy/vergil/pdf
PTPDFRENDERER_JAR = /home/rafael/Documentos/EngenhariaDeComputacao/Semestre8/TCC1/ptII8.0.1/lib/PDFRenderer.jar

# Set to psdf and used in $PTII/ptolemy/domains/makefile if
# $PTII/lib/mapss.jar was found
PTPSDF_DIR =	psdf

# PTSVG_DIR is set to svg and used in
# $PTII/diva/util/java2d/makefile if Batik SVG jar files were found
PTSVG_DIR =	

# Jar file used by diva/util/java2d/svg/makefile
PTSVG_JAR =	

# Jar files used by the Image Processing library from http://processing.org
PTPROCESSING_JARS =	

# PTPTALON_DIR is set to ptalon and used in
# $PTII/ptolemy/actor/makefile if antlr.jar was found.
PTPTALON_DIR =  ptalon

# PTALON_PACKAGES is used in $PTII/doc/makefile
PTPTALON_PACKAGES =  ptolemy.actor.ptalon ptolemy.actor.ptalon.lib ptolemy.actor.ptalon.gt

# Directory that contains the antlr.jar file, used by ptalon
ANTLR_DIR =	/home/rafael/Documentos/EngenhariaDeComputacao/Semestre8/TCC1/ptII8.0.1/ptolemy/actor/ptalon/antlr


# Set to quicktime and used in $PTII/ptolemy/domains/gr/lib/makefile if
# QuickTime for Java was found.
PTQUICKTIME_DIR =	

# Ptolemy II packages that use the Quicktime facility
# PTQUICKTIME_PACKAGES is used in ptII/doc/makefile
PTQUICKTIME_PACKAGES = 	

# PTSPACE_DIR is set to space and used in
# $PTII/ptolemy/domains/makefile if ojdbc6.jar was found.
PTSPACE_DIR =		space

# PTSPACEDOMAIN_JAR is the name of the jar file that includes the space
# domain if ojdbc6.jar was found.  PTSPACEDOMAIN_JAR used in domains/makefile
PTSPACEDOMAIN_JAR =	space/space.jar

# PTSPACEDOMAIN_PACKAGES is set if ojdbc6.jar was found and 
# used in ptII/doc/makefile
PTSPACEDOMAIN_PACKAGES = ptolemy.domains.space

# Set to tini and used in $PTII/ptolemy/apps/makefile if the Dallas Semi
# TINI was found
PTTINI_DIR =	@PTINI_DIR@

# PTTINYOS_DIR is set to ptinyos and used in
# $PTII/ptolemy/domains/makefile if TinyOS is found
PTTINYOS_DIR =	

# Set to waba and used in $PTII/ptolemy/makefile if the PalmOS WABA was found
PTWABA_DIR =	

# Set to x10 and used in
# $PTII/ptolemy/apps/superb/makefile if the X10 jar files were found
PTX10_DIR =	
# The location of X10 interface library, defaults to $PTII/vendors/misc/x10
X10_DIR =	
X10_JAR =       

# Ptolemy II packages that use the X10 facilty.
# PTX10_PACKAGES is used in ptII/doc/makefile
PTX10_PACKAGES = 	

# Jar file that contains RXTX Serial Port Interface
RXTX_JAR =   		${PTII}/vendors/misc/rxtx/RXTXcomm.jar

# PTRXTX_PACKAGES is used in ptII/doc/makefile
PTRXTX_PACKAGES =	

# Location of the diva.jar file.  Diva is (among other things) a graph
# visualization tool used by some of the demos.  For more information, see
# http://www-cad.eecs.berkeley.edu/diva/
DIVA_JAR = $(PTII)/lib/diva.jar

# JSAT jar location
MESCAL_DEPEND_JAR = $(PTII)/mescal/lib/jsat.jar$(CLASSPATHSEPARATOR)$(PTII)/mescal/lib/mescal_java_cup.jar$(CLASSPATHSEPARATOR)$(SOOT_CLASSES)$(CLASSPATHSEPARATOR)$(PTII)/mescal/lib/chloe.jar

# Commands used to install scripts and data
# Use $(ROOT) instead of $(PTII) for install so that we don't
# need to have PTII set under Ptolemy classic when installing Ptplot
INSTALL =		$(ROOT)/config/install-sh -c
INSTALL_PROGRAM =	${INSTALL}
INSTALL_DATA =		${INSTALL} -m 644


# Note that CALTROP_JARS should be before SOOT_JARS because
# CALTROP_JARS contains java_cup.jar and jasminclasses.jar contains
# a version of java cup
CALTROP_JARS = $(CLASSPATHSEPARATOR)$(CALTROP_DIR)/ptCal.jar$(CLASSPATHSEPARATOR)$(SAXON_JAR)$(CLASSPATHSEPARATOR)$(CALTROP_DIR)/java_cup.jar

# The complete classpath, used in ptolemy/configs/test/makefile, doc/makefile and ptolemy/vergil/test/makefile
COMPLETE_CLASSPATH = $(CLASSPATHSEPARATOR)$(ANTLR_DIR)/antlr.jar$(CLASSPATHSEPARATOR)$(PTBACKTRACK_ECLIPSE_JARS)$(CLASSPATHSEPARATOR)$(PTBACKTRACK_ECLIPSE_DOC_JARS)$(CLASSPATHSEPARATOR)$(PTBSH_JAR)$(CLASSPATHSEPARATOR)$(PTCOLT_JARS)$(CLASSPATHSEPARATOR)$(RXTX_JAR)$(CLASSPATHSEPARATOR)$(DIVA_JAR)$(CALTROP_JARS)$(CLASSPATHSEPARATOR)$(SOOT_CLASSES)$(CLASSPATHSEPARATOR)$(PTFUZZYENGINE_DIR)$(CLASSPATHSEPARATOR)$(JAI_JARS)$(CLASSPATHSEPARATOR)$(JAVA3D_JARS)$(CLASSPATHSEPARATOR)$(JSCLASSPATH)$(CLASSPATHSEPARATOR)$(JINI_JARS)$(CLASSPATHSEPARATOR)$(JMF_JARS)$(CLASSPATHSEPARATOR)$(CLASSPATHSEPARATOR)$(PTJOGL_JARS)$(CLASSPATHSEPARATOR)$(JOYSTICK_JAR)$(CLASSPATHSEPARATOR)$(JYTHON_DIR)/jython.jar$(CLASSPATHSEPARATOR)$(KIELER_JAR)$(CLASSPATHSEPARATOR)$(TOOLS_JAR)$(CLASSPATHSEPARATOR)$(QTJAVA_ZIP)$(CLASSPATHSEPARATOR)$(PSDF_DIR)/mapss.jar$(CLASSPATHSEPARATOR)$(PTINYOS_CLASSPATH)$(CLASSPATHSEPARATOR)$(PTITEXTPDF_JAR)$(CLASSPATHSEPARATOR)$(PTJACL_JAR)$(CLASSPATHSEPARATOR)$(PTPDFRENDERER_JAR)$(CLASSPATHSEPARATOR)$(X10_JAR)
