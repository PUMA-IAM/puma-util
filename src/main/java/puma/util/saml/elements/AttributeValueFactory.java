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

import org.opensaml.saml2.core.AttributeValue;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.schema.XSString;

import puma.util.exceptions.SAMLException;
import puma.util.saml.SAMLElementFactory;
import puma.util.saml.SAMLHelper;

public class AttributeValueFactory implements SAMLElementFactory<XSString> {
	private final static String TYPE_IDENTIFIER = "xsi:type";
	private final static String DEFAULT_TYPE = "xs:string";
	
	private String attributeValue;
	private String attributeType;
	
	public AttributeValueFactory(String value) throws SAMLException {
		this(value, DEFAULT_TYPE);
	}
	
	public AttributeValueFactory(String value, String type) throws SAMLException {
		SAMLHelper.initialize();
		this.attributeType = type;
		this.attributeValue = value;
	}

	@Override
	public XSString produce() {
		XSString result = (XSString) Configuration.getBuilderFactory().getBuilder(XSString.TYPE_NAME).buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSString.TYPE_NAME);
//		XSAny result = (XSAny) Configuration.getBuilderFactory().getBuilder(XSAny.TYPE_NAME).buildObject(AttributeValue.DEFAULT_ELEMENT_NAME);
//		XSAny result = SAMLHelper.createElement(XSAny.class, AttributeValue.DEFAULT_ELEMENT_NAME, XSAny.TYPE_NAME);
//		result.getUnknownAttributes().put(new QName(TYPE_IDENTIFIER), this.attributeType);
//		result.setTextContent(this.attributeValue);		
		if (this.attributeType == null || TYPE_IDENTIFIER == null); // DEBUG Introduce XSAny as type here - will also require a change in AttributeQueryCloner
		result.setValue(this.attributeValue);
		return result;
	}

}
