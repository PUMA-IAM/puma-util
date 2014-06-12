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

import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.AttributeValue;
import org.opensaml.xml.schema.XSAny;

import puma.util.exceptions.SAMLException;
import puma.util.saml.ObjectFactory;
import puma.util.saml.SAMLHelper;

/**
 * @author jasper
 */
public class CustomProxyExtensionFactory implements ObjectFactory<AttributeStatement> {
    private static final String ELEMENT_NAME = "Proxy";
    private String content;
    
    public CustomProxyExtensionFactory(String proxyPath) {
        this.content = proxyPath;
    }
    
    @Override
    public AttributeStatement produce() throws SAMLException {
        AttributeStatement stmt = SAMLHelper.createElement(AttributeStatement.class);
        Attribute attr = SAMLHelper.createElement(Attribute.class);
        XSAny val = SAMLHelper.createElement(XSAny.class, AttributeValue.DEFAULT_ELEMENT_NAME, XSAny.TYPE_NAME);
        val.setTextContent(this.content);
        attr.setFriendlyName(CustomProxyExtensionFactory.ELEMENT_NAME);
        attr.getAttributeValues().add(val);
        stmt.getAttributes().add(attr);
        return stmt;
    }
    
}
