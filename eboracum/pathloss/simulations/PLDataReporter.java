package eboracum.pathloss.simulations;

import java.text.DecimalFormat;
import java.util.Iterator;

import eboracum.pathloss.FreeSpaceChannel;
import eboracum.pathloss.LogDistanceChannel;
import eboracum.simulation.BenchmarksGenerator;
import eboracum.simulation.DataReporter;
import ptolemy.actor.CompositeActor;
import ptolemy.kernel.CompositeEntity;
import ptolemy.kernel.Entity;
import ptolemy.kernel.util.IllegalActionException;
import ptolemy.kernel.util.NameDuplicationException;

public class PLDataReporter extends DataReporter {

    private static final long serialVersionUID = 1L;

    public PLDataReporter(CompositeEntity container, String name)
            throws IllegalActionException, NameDuplicationException {
        super(container, name);
    }

    @Override
    public void fire() throws IllegalActionException {
        super.fire();

        DecimalFormat df = new DecimalFormat("#.#######");

        if (trigger.hasToken(0)) {
            CompositeActor container = (CompositeActor) getContainer();
            @SuppressWarnings("rawtypes")
            Iterator actors = container.deepEntityList().iterator();
            String fileReport = simulationReportFile.getValueAsString().substring(1,
                    simulationReportFile.getValueAsString().length() - 1);
            
            if(simulationReportFile.getValueAsString().equals("null")) {
                fileReport = simulationReportFile.getExpression();
            }
            
            while (actors.hasNext()) {
                Entity component = (Entity) actors.next();
                if (component.getClassName().startsWith("eboracum.pathloss.")
                        && component.getClassName().contains("Channel")) {
                    FreeSpaceChannel freeSpaceChannel = (FreeSpaceChannel) component;

                    BenchmarksGenerator.appendDataReportFile(fileReport,
                            "Calculated Communication Cover;" + freeSpaceChannel.calculatedRangeValue);
                    BenchmarksGenerator.appendDataReportFile(fileReport,
                            "Calculated Path Loss;" + freeSpaceChannel.maximumPathLoss);
                    if (freeSpaceChannel.getName().equals("LogDistanceChannel")) {

                        LogDistanceChannel logDistanceChannel = (LogDistanceChannel) freeSpaceChannel;

                        BenchmarksGenerator.appendDataReportFile(fileReport,
                                "Path Loss Exponent n;" + logDistanceChannel.pathLossFactorNValue);
                        BenchmarksGenerator.appendDataReportFile(fileReport,
                                "PL(d0);" + logDistanceChannel.pathLossD0Value);
                    }
                }
            }
        }
    }

}
