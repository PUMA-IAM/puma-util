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
package puma.util.saml.cloners;

import org.joda.time.DateTime;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.RequestedAuthnContext;
import org.opensaml.saml2.core.impl.RequestedAuthnContextBuilder;

import puma.util.exceptions.SAMLException;
import puma.util.saml.SAMLHelper;
import puma.util.saml.elements.IssuerFactory;
import puma.util.saml.elements.NameIDPolicyFactory;
import puma.util.saml.elements.SubjectFactory;

public class AuthnRequestCloner implements SAMLObjectCloner<AuthnRequest> {
	private final static Boolean DEFAULT_GENERATE_TIME = true;
	
	private String assertionIdentifier;
	private String messageDestination;
	private String providerName;
	private String assertionConsumerServiceUrl;
	private Boolean generateTime;
	
	public AuthnRequestCloner(String assertionId, String destination, String providerName, String assertionConsumerServiceUrl, Boolean generateTime) throws SAMLException {
        SAMLHelper.initialize();
		this.assertionIdentifier = assertionId;
		this.messageDestination = destination;
		this.providerName = providerName;
		this.assertionConsumerServiceUrl = assertionConsumerServiceUrl;
		this.generateTime = generateTime;
	}
	
	public AuthnRequestCloner(String assertionId, String destination, String providerName, String assertionConsumerServiceUrl) throws SAMLException {
		this(assertionId, destination, providerName, assertionConsumerServiceUrl, AuthnRequestCloner.DEFAULT_GENERATE_TIME);
	}
	
	@Override
	public AuthnRequest cloneElement(AuthnRequest item) throws SAMLException {
		AuthnRequest result = SAMLHelper.createElement(AuthnRequest.class);
		DateTime timestamp;
		if (this.generateTime)
			timestamp = new DateTime();
		else
			timestamp = item.getIssueInstant();
		
		result.setForceAuthn(item.isForceAuthn()); // Sets whether the IdP should force the user to reauthenticate.
        result.setIsPassive(item.isPassive()); // Sets whether the IdP should refrain from interacting with the user during the authentication process.
        result.setProtocolBinding(item.getProtocolBinding()); // Sets the protocol binding URI for the request.
        result.setProviderName(this.providerName);   // Sets the human-readable name of the requester for use by the presenter's user agent or the identity provider.
        result.setNameIDPolicy((new NameIDPolicyFactory()).produce()); // Sets the NameIDPolicy of the request.
        result.setIssueInstant(timestamp);
        result.setDestination(this.messageDestination);
        result.setIssuer((new IssuerFactory(this.providerName)).produce());
        result.setID(this.assertionIdentifier); // Set the id of the request
        // result.setScoping((new ScopingFactory()).produce());
        result.setAssertionConsumerServiceURL(this.assertionConsumerServiceUrl);
        // result.setSignature((new SignatureFactory(this.signatureData)).produce());
        // result.setConditions((new ConditionsFactory()).produce());
        RequestedAuthnContextBuilder contextBuilder = new RequestedAuthnContextBuilder();
        RequestedAuthnContext theContext = contextBuilder.buildObject();
        theContext.setComparison(item.getRequestedAuthnContext().getComparison());
        result.setRequestedAuthnContext(theContext);
        if (item.getSubject() != null && item.getSubject().getNameID() != null && item.getSubject().getNameID().getValue() != null && item.getSubject().getNameID().getFormat() != null)
        	result.setSubject((new SubjectFactory(item.getSubject().getNameID().getValue(), item.getSubject().getNameID().getFormat())).produce());
        if (item.getNameIDPolicy() != null) {
        	if (item.getNameIDPolicy().getFormat() != null)
        		result.setNameIDPolicy((new NameIDPolicyFactory(item.getNameIDPolicy().getFormat())).produce());
        	if (item.getNameIDPolicy().getAllowCreate() != null)	
        		result.getNameIDPolicy().setAllowCreate(item.getNameIDPolicy().getAllowCreate());
        }
        result.setVersion(item.getVersion());
		return result;
	}

}
