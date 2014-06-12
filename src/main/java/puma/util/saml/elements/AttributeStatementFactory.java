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

import java.util.ArrayList;
import java.util.List;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeStatement;

import puma.util.exceptions.SAMLException;
import puma.util.saml.ObjectFactory;
import puma.util.saml.RetrievableCompoundFactory;
import puma.util.saml.SAMLElementFactory;
import puma.util.saml.SAMLHelper;

public class AttributeStatementFactory implements RetrievableCompoundFactory<Attribute>, ObjectFactory<AttributeStatement> {
	private List<SAMLElementFactory<? extends Attribute>> attributeFactories;
	
	public AttributeStatementFactory() throws SAMLException {
		SAMLHelper.initialize();
		this.attributeFactories = new ArrayList<SAMLElementFactory<? extends Attribute>>();
	}
	
	@Override
	public AttributeStatement produce() throws SAMLException {
		AttributeStatement result = SAMLHelper.createElement(AttributeStatement.class);
		for (SAMLElementFactory<? extends Attribute> factory: this.attributeFactories) {
			result.getAttributes().add(factory.produce());
		}
		return result;
	}

	@Override
	public void addFactory(SAMLElementFactory<? extends Attribute> element) {
		this.attributeFactories.add(element);		
	}

	@Override
	public List<SAMLElementFactory<? extends Attribute>> retrieveFactories() {
		return this.attributeFactories;
	}

}
