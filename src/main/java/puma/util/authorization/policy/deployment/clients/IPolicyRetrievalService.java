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
package puma.util.authorization.policy.deployment.clients;

public interface IPolicyRetrievalService {
	/**
	 * Retrieve the xacml representation of the policy at the specified index. 
	 * @param index The index of the policy
	 * @param tenantIdentifier An id specifying the tenant for which the evaluation takes place
	 * @param applicationIdentifier An id specifying the application for which the evaluation takes place
	 * @return The XACML representation of the policy at the specified index, or an empty string if no policy could be found at the given index.
	 */
	public String retrievePolicy(Integer index, Integer tenantIdentifier, Integer applicationIdentifier);
	/**
	 * Retrieve the total number of managed policies at this administration point
	 * @return The total number of policies which are currently deployed at the administration point
	 */
	public Integer getNumberOfPolicies();
	/**
	 * Retrieve the combination method which is used to combine the decision of all policies which are deployed at the given administration point.
	 * @return A string representation of the combination method
	 */
	public String getPolicyCombinationMethod();
	/**
	 * Retrieve a representation of the unified policy. The unified policy is the combination of all relevant policies deployed at the administration point
	 * @param tenantIdentifier The identifier specifying the tenant for which to fetch the unified policy. Note that the unified policy is not the same for every tenant as a tenant can dispose of custom policies
	 * @param applicationIdentifier An id specifying the application for which the evaluation takes place. Note that a unified policy is not the same for every application as an application provider can specify its custom policies for the application
	 * @return A XACML representation of the unified policy for the given tenant. If the tenant identifier is invalid, the returned unified policy is composed of all policies that are common to all tenants for the given application. If the application identifier is invalid, then the unified policy is composed of all policies common to every application and every tenant. If both are invalid, an empty string is returned. It is considered the caller's responsibility to make sure the identifiers are correct.
	 */
	public String getUnifiedPolicy(Integer tenantIdentifier);
}
