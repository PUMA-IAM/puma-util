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

import org.opensaml.saml2.core.Status;
import org.opensaml.saml2.core.StatusCode;

import puma.util.exceptions.SAMLException;
import puma.util.saml.ObjectFactory;
import puma.util.saml.SAMLHelper;

public class StatusFactory implements ObjectFactory<Status> {
	private final static String DEFAULT_STATUS_CODE = StatusCode.SUCCESS_URI;
	
	private String statusCodeValue;
	
	public StatusFactory() throws SAMLException {
		this(DEFAULT_STATUS_CODE);
	}
	
	// LATER Add StatusDetail, StatusMessage
	public StatusFactory(String statusCode) throws SAMLException {
		SAMLHelper.initialize();
		this.statusCodeValue = statusCode;
	}

	@Override
	public Status produce() throws SAMLException {
		Status status = SAMLHelper.createElement(Status.class);
        StatusCode code = SAMLHelper.createElement(StatusCode.class);
        code.setValue(this.statusCodeValue);
        status.setStatusCode(code);
		return status;
	}

}
