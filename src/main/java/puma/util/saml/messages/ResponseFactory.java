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
package puma.util.saml.messages;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.opensaml.common.SAMLVersion;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.Status;

import puma.util.exceptions.SAMLException;
import puma.util.saml.CompoundFactory;
import puma.util.saml.ObjectFactory;
import puma.util.saml.SAMLElementFactory;
import puma.util.saml.SAMLHelper;
import puma.util.saml.elements.StatusFactory;

public class ResponseFactory implements ObjectFactory<Response>, CompoundFactory<Assertion> {
	private static final SAMLVersion DEFAULT_VERSION = SAMLVersion.VERSION_20;
	
	private String assertionIdentifier;
	private String inResponseTo;
	private String destination;
	private Issuer issuer;
	private Status status;
	private List<SAMLElementFactory<? extends Assertion>> assertions;
	
	public ResponseFactory(String assertionId, String inResponseTo, String destination, Issuer issuer) throws SAMLException {
		this(assertionId, inResponseTo, destination, issuer, (new StatusFactory()).produce());
	}
	
	public ResponseFactory(String assertionId, String inResponseTo, Issuer issuer, Status status) throws SAMLException {
		this(assertionId, inResponseTo, null, issuer, status);
	}
	
	public ResponseFactory(String assertionId, String inResponseTo, String destination, Issuer issuer, Status status) throws SAMLException {
		SAMLHelper.initialize();
		this.assertionIdentifier = assertionId;
		this.inResponseTo = inResponseTo;
		this.destination = destination;
		this.issuer = issuer;
		this.status = status;
		this.assertions = new ArrayList<SAMLElementFactory<? extends Assertion>>();		
	}
	
	
	@Override
	public Response produce() throws SAMLException {
		Response result = SAMLHelper.createElement(Response.class);
		result.setID(this.assertionIdentifier);
		result.setInResponseTo(this.inResponseTo);
		if (this.destination != null)
			result.setDestination(this.destination);
		result.setIssuer(this.issuer);
		result.setIssueInstant(new DateTime());
		result.setStatus(this.status);
		for (SAMLElementFactory<? extends Assertion> factory: this.assertions)
			result.getAssertions().add(factory.produce());
		result.setVersion(ResponseFactory.DEFAULT_VERSION);
		return result;
	}

	@Override
	public void addFactory(SAMLElementFactory<? extends Assertion> element) {
		this.assertions.add(element);
	}
}
