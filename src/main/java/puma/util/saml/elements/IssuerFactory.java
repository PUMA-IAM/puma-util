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
package puma.util.saml.elements;

import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.NameIDType;

import puma.util.exceptions.SAMLException;
import puma.util.saml.ObjectFactory;
import puma.util.saml.SAMLHelper;

/**
 *
 * @author jasper
 */
public class IssuerFactory implements ObjectFactory<Issuer> {
    private static final String PREFERRED_FORMAT = NameIDType.ENTITY;
    private String format;
    private String issuerName;
    
    public IssuerFactory(String issuerName, String format) throws SAMLException {
    	SAMLHelper.initialize();
        this.issuerName = issuerName;
        this.format = format;
    	
    }
    
    public IssuerFactory(String issuerName) throws SAMLException {
    	this(issuerName, IssuerFactory.PREFERRED_FORMAT);
    }
    
    @Override
    public Issuer produce() throws SAMLException {
        Issuer result = SAMLHelper.createElement(Issuer.class);
        result.setValue(this.issuerName);
        result.setFormat(this.format);
        return result;
    }
    
}
