/* An unknown error occurred during AST analysis.

 Copyright (c) 2005 The Regents of the University of California.
 All rights reserved.
 Permission is hereby granted, without written agreement and without
 license or royalty fees, to use, copy, modify, and distribute this
 software and its documentation for any purpose, provided that the above
 copyright notice and the following two paragraphs appear in all copies
 of this software.

 IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY
 FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
 ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
 SUCH DAMAGE.

 THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
 INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
 PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
 CALIFORNIA HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
 ENHANCEMENTS, OR MODIFICATIONS.

 PT_COPYRIGHT_VERSION_2
 COPYRIGHTENDKEY

 */
package ptolemy.backtrack.eclipse.ast;

//////////////////////////////////////////////////////////////////////////
//// UnknownASTException

/**
 Thrown during AST analysis notifying that an unknown error occurs.

 @author Thomas Feng
 @version $Id: UnknownASTException.java 44788 2006-12-19 18:31:56Z cxh $
 @since Ptolemy II 5.1
 @Pt.ProposedRating Red (tfeng)
 @Pt.AcceptedRating Red (tfeng)
 */
public class UnknownASTException extends ASTRuntimeException {
    /** Constrcut an unknown exception caused by the AST analyzer.
     */
    public UnknownASTException() {
        super("Unknown AST exception.");
    }

    /** Constrcut an unknown exception caused by the AST analyzer.
     *  @param cause The cause.
     */
    public UnknownASTException(Throwable cause) {
        super("Unknown AST exception.", cause);
    }
}
