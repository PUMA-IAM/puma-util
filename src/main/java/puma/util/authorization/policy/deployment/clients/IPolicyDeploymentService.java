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

public interface IPolicyDeploymentService {
	// Note: Did not use Jersey annotations here, as (in a quick search) Jersey only supports limited annotation inheritance. Did not choose to include Response objects neither, as this would require the artifact to be included for just that.
	
	/**
	 * Deploy a policy with the specified id at a given position in the policy set
	 * @param index The position in the policy set at which point the policy needs to be evaluated. If a policy already exists at the given index, it will be given the next index, as will be done for all consecutive policies.
	 * @param id Unique identifier to identify the policy with during the deployment lifetime.
	 * @param policy XACML representation of the policy. This string can also consist of a description of policy sets, which contain the respective policies.
	 * @return A response to indicate the success or failure of the operation.
	 */
	public Object deployPolicy(Integer index, Integer id, String policy);
	
	/**
	 * Undeploy the policy specified by the given identifier
	 * @param id The unique identifier for the policy
	 * @return A response to indicate the success or failure of the operation.
	 */
	public Object undeployPolicy(Integer id);
	
	/**
	 * Undeploy the policy specified by the given index in the policy
	 * @param index The position in the policy set to delete the policy from
	 * @return A response to indicate the success or failure of the operation.
	 */
	public Object undeployPolicyAtIndex(Integer index);
	
	/**
	 * Set the combination method for the evaluated policies
	 * @param combination The identifier for the combination method (i.e. permit-overrides, deny-overrides, first-applicable, only-one-applicable)
	 * @return A response to indicate the success or failure of the operation.
	 */
	public Object setPolicyCombinationMethod(String combination);
	
	/**
	 * Retrieve the position in the policy set where the policy resides.
	 * @param id The unique identifier specifying a policy
	 * @return The identifier of the policy
	 */
	public Object retrieveIndexOfPolicy(Integer id);
	
	/**
	 * Obtain the log records for a specified policy
	 * @param identifier The unique identifier specifying the policy 
	 * @param start The starting record for the log
	 * @param end The last record for the log
	 * @return The log for the given policy. If start is less than 0 or larger than the end parameter or than  the last record available, every record is retrieved. If end is less than start then no record is retrieved. If end is larger than the size of the log then all records until the end are retrieved. 
	 */
	public Object obtainLog(Integer identifier, Integer start, Integer end);
	
	/**
	 * Checks if a policy can be deployed
	 * @param policyContents The XACML representation of the policy
	 * @return True if the policy can be deployed
	 */
	public Object canDeployPolicy(String policyContents);
	
	/**
	 * Checks if a policy has already been deployed
	 * @param policyId The unique identifier of the policy
	 * @return True if the policy is deployed at the given site
	 */
	public Object hasPolicy(Integer policyId);
}
