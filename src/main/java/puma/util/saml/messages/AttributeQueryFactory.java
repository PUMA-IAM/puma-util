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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package puma.util.saml.messages;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.opensaml.common.SAMLVersion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeQuery;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.Subject;

import puma.util.exceptions.SAMLException;
import puma.util.saml.CompoundFactory;
import puma.util.saml.ObjectFactory;
import puma.util.saml.SAMLElementFactory;
import puma.util.saml.SAMLHelper;

/**
 *
 * @author jasper
 */
public class AttributeQueryFactory implements ObjectFactory<AttributeQuery>, CompoundFactory<Attribute> {
    private String assertionIdentifier;
    private Subject subject;
    private String destination;
    private Issuer issuer;
    private List<SAMLElementFactory<? extends Attribute>> attributeFactories;
    
    public AttributeQueryFactory(String assertionId, Subject subject, String destination, Issuer issuer) throws SAMLException {
        SAMLHelper.initialize();
        this.assertionIdentifier = assertionId;
        this.subject = subject;
        this.destination = destination;
        this.issuer = issuer;
        this.attributeFactories = new ArrayList<SAMLElementFactory<? extends Attribute>>();
    }
    
    @Override
    public AttributeQuery produce() throws SAMLException {
        DateTime now = new DateTime();
        AttributeQuery query = SAMLHelper.createElement(AttributeQuery.class);
        query.setID(this.assertionIdentifier);
        query.setIssueInstant(now);
        query.setVersion(SAMLVersion.VERSION_20);        
        query.setDestination(this.destination);
        // Issuer
        query.setIssuer(this.issuer);        
        // Subject
        query.setSubject(this.subject);        
        // Attributes
        for (SAMLElementFactory<? extends Attribute> attributeFactory: this.attributeFactories) {
            query.getAttributes().add(attributeFactory.produce());
        }        
        // Signature 
        // LATER query.setSignature(null);        
        // Return
        return query;
    }
    
	@Override
	public void addFactory(SAMLElementFactory<? extends Attribute> element) {
		this.attributeFactories.add(element);		
	}
    
}
