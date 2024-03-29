# C codegen SDFDirector test
#
# @Author: Christopher Brooks
#
# @Version: $Id: SDFDirector.tcl 57040 2010-01-27 20:52:32Z cxh $
#
# @Copyright (c) 2005-2008 The Regents of the University of California.
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
#######################################################################

# Ptolemy II test bed, see $PTII/doc/coding/testing.html for more information.

# Load up the test definitions.
if {[string compare test [info procs test]] == 1} then {
    source testDefs.tcl
} {}

if {[info procs sdfModel] == "" } then {
    source [file join $PTII util testsuite models.tcl]
}

#####
test SDFDirector-1.1 {Call generateCode(StringBuffer)} {
    set model [sdfModel]
    set codeGenerator \
	    [java::new ptolemy.codegen.java.kernel.JavaCodeGenerator \
	    $model "myCodeGenerator"]

    set results [java::new StringBuffer]
    set processExitCode [$codeGenerator generateCode $results]
    set fd [open foo.c w]
    set filename [$codeGenerator getCodeFileName]
    
    list $processExitCode \
	[string range $filename [expr {[string length $filename] - 8}] [string length $filename]] \
	[expr {[string length [$results toString]] > 0}]
} {0 top.java 1}
