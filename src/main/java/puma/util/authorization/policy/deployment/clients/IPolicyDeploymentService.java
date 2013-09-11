package puma.util.authorization.policy.deployment.clients;

public interface IPolicyDeploymentService {
	// Note: Did not use Jersey annotations here, as (in a quick search) Jersey only supports limited annotation inheritance. Did not choose to include Response objects neither, as this would require the artifact to be included for just that.
	
	/**
	 * Deploy a policy with the specified id at a given position in the policy set
	 * @param index The position in the policy set at which point the policy needs to be evaluated.
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
	 * @param combinationId The identifier for the combination method (e.g. permit-overrides, deny-overrides, ...)
	 * @return A response to indicate the success or failure of the operation.
	 */
	public Object setPolicyCombinationMethod(Integer combinationId);
	
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
