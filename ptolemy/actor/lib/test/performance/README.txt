$Id: README.txt 51960 2008-12-24 20:05:20Z cxh $
Notes about measuring performance

See $PTII/doc/coding/performance.htm

Below are the different performance tests in this directory

pubSubAgg
---------
Construct models with different depths and numbers of actors

pubSubAgg.tcl - a script that creates models with different numbers
          of Subscribers per level and different numbers of levels

runModels - a script that runs MoMLSimpleStatisticalApplication
	  and saves the output in separate files

plotPerformance - a script that takes MoMLSimpleStatisticalApplication
          runs and generates PlotML suitable for $PTII/bin/plot

To use pubSubAgg.tcl:
  $PTII/bin/ptjacl pubSubAgg.tcl
  ./runModels pubSubAgg*.xml
  ./plotPerformance pubSubAgg*.out > pubSubAgg.plt
  $PTII/bin/ptplot pubSubAgg.plt 

Publisher2
----------
Construct models with different numbers of actors

Publisher2.tcl - a script that generates different models that have
a depth of two, but have a different number of actors

To use Publisher2.tcl:
  $PTII/bin/ptjacl Publisher2.tcl
  ./runModels pubsub*.xml
  ./stats pubsub*.out > pubsub.plt
  $PTII/bin/ptplot pubsub.plt 

