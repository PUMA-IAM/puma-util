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

import org.opensaml.saml2.core.NameIDPolicy;
import org.opensaml.saml2.core.NameIDType;

import puma.util.exceptions.SAMLException;
import puma.util.saml.ObjectFactory;
import puma.util.saml.SAMLHelper;

/**
 *
 * @author jasper
 */
public class NameIDPolicyFactory implements ObjectFactory<NameIDPolicy> {
    private static final String DEFAULT_FORMAT = NameIDType.PERSISTENT;
    private String format;
    
    public NameIDPolicyFactory() {
        this.format = NameIDPolicyFactory.DEFAULT_FORMAT;
    }
    
    public NameIDPolicyFactory(String preferredFormat) {
        this.format = preferredFormat;
    }

    @Override
    public NameIDPolicy produce() throws SAMLException {
        NameIDPolicy policy = SAMLHelper.createElement(NameIDPolicy.class);
        policy.setAllowCreate(true);
        policy.setFormat(this.format);
        return policy;
    }
    
}
