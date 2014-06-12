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
package puma.util.saml.elements;

import org.joda.time.DateTime;
import org.opensaml.saml2.core.AuthnContext;
import org.opensaml.saml2.core.AuthnStatement;

import puma.util.exceptions.SAMLException;
import puma.util.saml.ObjectFactory;
import puma.util.saml.SAMLHelper;

public class AuthnStatementFactory implements ObjectFactory<AuthnStatement> {
	private DateTime authnInstant;
	private AuthnContext context;
		
	public AuthnStatementFactory(DateTime authnInstant, AuthnContext context) throws SAMLException {
		SAMLHelper.initialize();
		this.authnInstant = authnInstant;
		this.context = context;
	}
	
	@Override
	public AuthnStatement produce() throws SAMLException {
		AuthnStatement result = SAMLHelper.createElement(AuthnStatement.class);
		result.setAuthnInstant(this.authnInstant);
		if (this.context != null) {
			result.setAuthnContext(context);
		}
		return result;
	}

}
