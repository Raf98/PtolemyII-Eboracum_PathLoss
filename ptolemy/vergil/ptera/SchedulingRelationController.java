/* The edge controller for scheduling relations in a  A Ptolemy Event Relation Actor (PTERA) domain model.

 Copyright (c) 2009 The Regents of the University of California.
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

package ptolemy.vergil.ptera;

import java.awt.BasicStroke;

import ptolemy.actor.gui.Configuration;
import ptolemy.domains.ptera.kernel.SchedulingRelation;
import ptolemy.vergil.modal.Arc;
import ptolemy.vergil.modal.TransitionController;
import diva.canvas.Site;
import diva.canvas.connector.ArcConnector;
import diva.canvas.connector.Connector;
import diva.graph.GraphController;

/**
 The edge controller for scheduling relations in a A Ptolemy Event
 Relation Actor (PTERA) domain model.

 @author Thomas Huining Feng
 @version $Id: SchedulingRelationController.java 57044 2010-01-27 22:41:05Z cxh $
 @since Ptolemy II 8.0
 @Pt.ProposedRating Red (tfeng)
 @Pt.AcceptedRating Red (tfeng)
 */
public class SchedulingRelationController extends TransitionController {
    /** Create a scheduling relation controller associated with the
     *  specified graph controller.
     *  @param controller The associated graph controller.
     */
    public SchedulingRelationController(GraphController controller) {
        super(controller);
        setEdgeRenderer(new SchedulingRelationRenderer());
    }

    ///////////////////////////////////////////////////////////////////
    ////                         public methods                    ////

    /** Set the configuration.  This is may be used by derived controllers
     *  to open files or URLs.
     *  @param configuration The configuration.
     */
    public void setConfiguration(Configuration configuration) {
        super.setConfiguration(configuration);

        if (_lookInsideActionFactory != null) {
            _menuFactory.removeMenuItemFactory(_lookInsideActionFactory);
        }
    }

    ///////////////////////////////////////////////////////////////////
    ////                  public inner classes                     ////

    /** Render a scheduling relation link between two events.
     */
    public static class SchedulingRelationRenderer extends LinkRenderer {

        /**
         * Render a scheduling relation link between two events.
         * @param edge The edge.
         * @param tailSite The tailSite of the edge
         * @param headSite The headSite of the edge
         * @return The connector between the headSite and the tailSite.
         */
        public Connector render(Object edge, Site tailSite, Site headSite) {
            ArcConnector connector = (ArcConnector) super.render(edge,
                    tailSite, headSite);
            Arc arc = (Arc) edge;
            SchedulingRelation relation = (SchedulingRelation) arc
                    .getRelation();
            if (relation != null && relation.isCanceling()) {
                BasicStroke stroke = new BasicStroke(2.0f,
                        BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f,
                        new float[] { 5.0f, 7.0f }, 0.0f);
                connector.setStroke(stroke);
            }
            return connector;
        }
    }
}
