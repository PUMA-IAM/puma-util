/*******************************************************************************
 * Copyright 2013 KU Leuven Research and Developement - IBBT - Distrinet 
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *    
 *    Administrative Contact: dnet-project-office@cs.kuleuven.be
 *    Technical Contact: maarten.decat@cs.kuleuven.be
 *    Author: maarten.decat@cs.kuleuven.be
 ******************************************************************************/
package puma.util.timing;

import static com.codahale.metrics.MetricRegistry.name;

import java.util.logging.Logger;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

/**
 * 
 * 
 * @author maartend
 * 
 */
public class TimerFactory {

	/***************************
	 * LOGGER
	 ***************************/

	private static final Logger logger = Logger.getLogger(TimerFactory.class
			.getName());

	/***************************
	 * SINGLETON STUFF
	 ***************************/

	private static TimerFactory instance;
	
	public static TimerFactory getInstance() {
		if(instance == null) {
			instance = new TimerFactory();
		}
		return instance;
	}

	/***************************
	 * CONSTRUCTOR
	 ***************************/

	/**
	 * Default constructor.
	 */
	public TimerFactory() {
		// nothing to do
	}

	/***************************
	 * FUNCTIONALITY
	 ***************************/
	
	private MetricRegistry metricRegistry = new MetricRegistry();
	
	public MetricRegistry getMetricRegistry() {
		return metricRegistry;
	}

	/**
	 * Returns a timer context for the given class with given name.
	 */
	public Timer getTimer(Class klass, String name) {
		return this.metricRegistry.timer(name(klass, name));
	}

	/**
	 * Returns a counter given class with given name.
	 */
	public Counter getCounter(Class klass, String name) {
		return this.metricRegistry.counter(name(klass, name));
	}
	
	/**
	 * Resets all metrics (timers, counters,...) from the application (in essence, this method just
	 * destroys all timers and makes sure a new one will be returned when asked
	 * for a timer above).
	 */
	public void resetAllTimers() {
		/*for (String next: this.metricRegistry.getTimers().keySet()) {
			this.metricRegistry.remove(next);
			this.metricRegistry.timer(next);
		}*/
		this.metricRegistry = new MetricRegistry();
	}
	
	/**
	 * Resets only the counters.
	 */
	public void resetCounters() {
		for(String name: this.metricRegistry.getCounters().keySet()) {
			this.metricRegistry.remove(name);
		}
	}

}
