/*
 @Copyright (c) 2007-2009 The Regents of the University of California.
 All rights reserved.

 Permission is hereby granted, without written agreement and without
 license or royalty fees, to use, copy, modify, and distribute this
 software and its documentation for any purpose, provided that the
 above copyright notice and the following two paragraphs appear in all
 copies of this software.

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
package ptolemy.codegen.java.actor.lib;

import java.util.ArrayList;

import ptolemy.codegen.java.kernel.JavaCodeGeneratorHelper;
import ptolemy.kernel.util.IllegalActionException;

/**
 * Generate Java code for an actor that publishes tokens on a named channel.
 *
 * @see ptolemy.actor.lib.Publisher
 * @author Christopher Brooks
 * @version $Id: Publisher.java 57044 2010-01-27 22:41:05Z cxh $
 * @since Ptolemy II 8.0
 * @Pt.ProposedRating Red (mankit)
 * @Pt.AcceptedRating Red (cxh)
 *
 */
public class Publisher extends JavaCodeGeneratorHelper {
    /**
     * Constructor method for the Publisher helper.
     * @param actor the associated actor
     */
    public Publisher(ptolemy.actor.lib.Publisher actor) {
        super(actor);
    }

    /**
     * Generate fire code.
     * The method reads in <code>fireBlock</code> from Publisher.c and
     * replaces macros with their values and returns the processed code
     * block.
     * @return The generated code.
     * @exception IllegalActionException If the code stream encounters an
     *  error in processing the specified code block(s).
     */
    protected String _generateFireCode() throws IllegalActionException {
        super._generateFireCode();

        ptolemy.actor.lib.Publisher actor = (ptolemy.actor.lib.Publisher) getComponent();

        ArrayList args = new ArrayList();
        args.add(Integer.valueOf(0));

        // FIXME: we are getting the minimum of the input and output
        // width for now. But we still have to prove that this is
        // sufficient.
        int width = Math.min(actor.output.getWidth(), actor.input.getWidth());

        if (actor.output.numberOfSinks() > 0) {
            for (int i = 0; i < width; i++) {
                args.set(0, Integer.valueOf(i));
                _codeStream.appendCodeBlock("fireBlock", args);
            }
        }

        return processCode(_codeStream.toString());
    }

}
