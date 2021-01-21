package eboracum.wsn.network.node.sensor.cpu;

import java.util.List;

import ptolemy.actor.util.Time;

	public interface SensorCPU {
		
		/*
		 * The ApplicationLoad.xml is a XML file like above containing the task structure (definition and interrelationship graph)of the 
		 * events with the costs associates of each task of each event form each CPU in the model.
		 * 
		 * <load>
		 *	<event type="nameOfEvent">
		 *		<task id="0">
		 *			<cpu name="nameOfTheCPUClass" cost="1" />
		 *		</task>
		 *		<task id="1">
		 *			<cpu name="nameOfTheCPUClass" cost="3" />
		 *		</task>
		 *		<graph>
		 *			<edge from='1' to='2' />
		 *		</graph>
		 *	</event>
		 *	<event type="nameOfOtherEvent">
		 *		<task id="0">
		 *			<cpu name="nameOfTheCPUClass" cost="5" />
		 *		</task>
		 *	</event>
		 * </load>
		 */
		final static String PLATFORMCONFIG = "eboracum/wsn/PlatformConfig.xml";
		
		/*
		 * Receives a task to be processed, or null if there is no new task, and the current time of the simulation
		 * 
		 * Returns a tuple [numTasksToBeProcessed, processedTask]
		 * numTasksToBeProcessed is the number of tasks waiting to be processed by the CPU at the current simulation time.
		 * processedTask is the task processed in the current simulation time if any, null if there is no finished task at this simulation time. 
		 */
		public List<Object> run(String task, Time currentTime);
		
	}

