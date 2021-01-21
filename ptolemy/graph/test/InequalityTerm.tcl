# Tests for InequalityTerm.
#
# @Author: Yuhong Xiong
#
# $Id: InequalityTerm.tcl 36428 2005-02-28 21:07:31Z cxh $
#
# @Copyright (c) 1997-2005 The Regents of the University of California.
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
}

# Uncomment this to get a full report, or set in your Tcl shell window.
# set VERBOSE 1

# 
#

proc termToInfo {term} {
    if [ java::instanceof $term ptolemy.graph.test.TestConstant] {
	set cterm [java::cast ptolemy.graph.test.TestConstant $term]
    } else { 
	set cterm [java::cast ptolemy.graph.test.TestVariable $term]
    }
    return [$cterm getInfo]
}


######################################################################
####
# 
test InequalityTerm-1.1 {construct a variable term} {
    set ta [java::new ptolemy.graph.test.TestVariable]
    $ta setName A

    list [$ta isSettable]
} {1}

######################################################################
####
# 
test InequalityTerm-1.2 {test fixValue} {
    $ta fixValue  
    list [$ta isSettable]
} {0}

######################################################################
####
# 
test InequalityTerm-1.3 {test unfixValue} {
    $ta unfixValue  
    list [$ta isSettable]
} {1}
