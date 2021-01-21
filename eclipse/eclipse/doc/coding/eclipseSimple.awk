#! /usr/bin/awk
# Awk script that reads eclipse.htm and creates eclipseSimple.htm
#
# Author:  Christopher Brooks
# Version: $Id: ptinyKepler.awk 42203 2006-05-02 22:53:47Z cxh $
#
# Copyright (c) 2010 The Regents of the University of California.
# 	All Rights Reserved.
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

# This awk script is used to generate vergil compatible html files by
# reading in a html file and printing out the header up to and including
# <body>, and then only printing sections of the input that start
# with <!--ptinyKepler--> and end with <!--/ptinyKepler-->
# 
# To run this script, do
# awk -f javahtml.awk foo.htm > fooVergil.htm

BEGIN { print "<!-- DO NOT EDIT this file, it is created by running make."
	print "     The file to edit is $PTII/doc/coding/eclipe.htm"
	print "     and then run 'make update'"
        print "     to update the derived files and check them in to svn. -->"
}
$0 ~ /<body.*>/ {
    sawBody = 1;
    # Remove any background info from the body tag.
    print "<body>";
}
{
    if (sawBody!=1) {
	print $0;
    } else {
	if ($0 ~/eclipseSimple/) {
	    sawEclipseSimple=1;
	    if ($0 ~/\/eclipseSimple/) {
		sawEclipseSimple=0;
	    }
	}
	if (sawEclipseSimple==1) {
	    print $0;
            if ($0 ~/<h1>/) {
                print "<p>These are simplified instructions for setting up "
                print "Eclipse using the source tree."
                print "See <a href=\"eclipse.htm\"><code>eclipse.htm</code></a>"
                print "for instructions about how to set up Eclipse using "
                print "the Subversion version control system."
                print "Subversion allows you to get updates from the "
                print "Ptolemy development tree.  The instructions here "
                print "provide access to a static source code tree, such"
                print "as what is shipped with the Ptolemy II release.</p>"
                print "<p>"
            }
	}
    }
}
END {
    print "</body>";
    print "</html>";
}
