/*******************************************************************************
 * Copyright 2014 KU Leuven Research and Developement - iMinds - Distrinet 
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

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface used for representing objects that provide metrics (i.e.,
 * the measurements).
 * 
 * @author maartend
 *
 */
public interface MetricsProvider extends Remote {
	
	/**
	 * Returns the metrics as JSON string.
	 * 
	 * @return
	 */
	public String getMetrics() throws RemoteException;
	
	/**
	 * Resets all the metrics for performing a new test
	 * 
	 */
	public void resetMetrics() throws RemoteException;

}
