# Makefile for the ptolemy/vergil/basic package
#
# @Authors: Christopher Hylands, based on a file by Thomas M. Parks
#
# @Version: $Id: makefile 57040 2010-01-27 20:52:32Z cxh $
#
# @Copyright (c) 2001-2010 The Regents of the University of California.
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


ME =		ptolemy/vergil/basic

# PTITEXTPDF_DIR is set to itextpdf if iText.jar was found by configure.
DIRS =		img $(PTITEXTPDF_DIR) layout test

# Root of the Java directory
ROOT =		../../..

CLASSPATH =	$(ROOT)$(CLASSPATHSEPARATOR)$(DIVA_JAR)

# Get configuration info
CONFIG =	$(ROOT)/mk/ptII.mk
include $(CONFIG)

# Used to build jar files
PTPACKAGE = 	basic
PTCLASSJAR =
PTCLASSALLJAR = $(PTPACKAGE).jar

# Include the .class files from these jars in PTCLASSALLJAR
PTCLASSALLJARS = \
		img/img.jar \
		layout/layout.jar

JSRCS = \
	AbstractBasicGraphModel.java \
	BasicGraphController.java \
	BasicGraphFrame.java \
	BasicGraphPane.java \
	ContextMenuFactoryCreator.java \
	CustomizeDocumentationAction.java \
	DependencyHighlighter.java \
	DocAttribute.java \
	DocViewerFactory.java \
	EditorDropTarget.java \
	ExtendedGraphFrame.java \
	GetDocumentationAction.java \
	IconController.java \
	KeplerDocumentationAttribute.java \
	LocatableNodeController.java \
	LocatableNodeDragInteractor.java \
	NamedObjController.java \
	NamedObjNodeModel.java \
	NodeControllerFactory.java \
	OffsetMoMLChangeRequest.java \
	ParameterizedNodeController.java \
	PopupMouseFilter.java \
	RemoveCustomDocumentationAction.java \
	RunnableGraphController.java \
	WithIconGraphController.java

EXTRA_SRCS =	$(JSRCS) \
	DocML_1.dtd

# Sources that may or may not be present, but if they are present, we don't
# want make checkjunk to barf on them.
MISC_FILES =	$(DIRS)

# make checkjunk will not report OPTIONAL_FILES as trash
# make realclean removes OPTIONAL_FILES
OPTIONAL_FILES = \
	$(PTCLASSJAR) \
	'BasicGraphController$$1.class' \
	'BasicGraphController$$OpenBaseClassAction.class' \
	'BasicGraphController$$SchematicContextMenuFactory.class' \
	'BasicGraphController$$UnitSolverDialogAction.class' \
	'BasicGraphFrame$$1.class' \
	'BasicGraphFrame$$2.class' \
	'BasicGraphFrame$$CopyAction.class' \
	'BasicGraphFrame$$CutAction.class' \
	'BasicGraphFrame$$DocumentationMenuItemFactory$$1.class' \
	'BasicGraphFrame$$DocumentationMenuItemFactory.class' \
	'BasicGraphFrame$$EditPreferencesAction.class' \
	'BasicGraphFrame$$ElementInLinkType.class' \
	'BasicGraphFrame$$LinkElementProperties.class' \
	'BasicGraphFrame$$MoveToBackAction$$1.class' \
	'BasicGraphFrame$$MoveToBackAction.class' \
	'BasicGraphFrame$$MoveToFrontAction$$1.class' \
	'BasicGraphFrame$$MoveToFrontAction.class' \
	'BasicGraphFrame$$OpenContainerAction.class' \
	'BasicGraphFrame$$OpenLibraryMenuItemFactory$$1.class' \
	'BasicGraphFrame$$OpenLibraryMenuItemFactory.class' \
	'BasicGraphFrame$$PasteAction.class' \
	'BasicGraphFrame$$PrintAction.class' \
	'BasicGraphFrame$$PtolemyLayout.class' \
	'BasicGraphFrame$$PtolemyLayoutTarget.class' \
	'BasicGraphFrame$$RedoAction.class' \
	'BasicGraphFrame$$SaveAction.class' \
	'BasicGraphFrame$$UndoAction.class' \
	'BasicGraphFrame$$ZoomFitAction.class' \
	'BasicGraphFrame$$ZoomInAction.class' \
	'BasicGraphFrame$$ZoomOutAction.class' \
	'BasicGraphFrame$$ZoomResetAction.class' \
	'CustomizeDocumentationAction$$1.class' \
	'CustomizeDocumentationAction$$2.class' \
	'CustomizeDocumentationAction$$3.class' \
	'DependencyHighlighter$$DependencyController.class' \
	'DependencyHighlighter$$HighlightDependents.class' \
	'EditorDropTarget$$1.class' \
	'EditorDropTarget$$DTListener.class' \
	'ExtendedGraphFrame$$FullScreenAction.class' \
	'IconController$$IconRenderer$$1.class' \
	'IconController$$IconRenderer.class' \
	'LocatableNodeDragInteractor$$1.class' \
	'RunnableGraphController$$1.class' \
	'RunnableGraphController$$2.class' \
	'RunnableGraphController$$ButtonFigureAction.class' \
	'RunnableGraphController$$PauseModelAction.class' \
	'RunnableGraphController$$RunModelAction.class' \
	'RunnableGraphController$$StopModelAction.class' \
	'WithIconGraphController$$NewPortAction$$1.class' \
	'WithIconGraphController$$NewPortAction.class'

JCLASS = $(JSRCS:%.java=%.class)

all: jclass
install: jclass jars

# Get the rest of the rules
include $(ROOT)/mk/ptcommon.mk
