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
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

import org.opensaml.common.SAMLObject;
import org.opensaml.saml2.common.Extensions;

import puma.util.exceptions.SAMLException;
import puma.util.saml.CompoundFactory;
import puma.util.saml.ObjectFactory;
import puma.util.saml.SAMLElementFactory;
import puma.util.saml.SAMLHelper;
/**
 *
 * @author jasper
 */
public class ExtensionsFactory implements ObjectFactory<Extensions>, CompoundFactory<SAMLObject> {
    private List<SAMLElementFactory<? extends SAMLObject>> extensionElements;
    
    public ExtensionsFactory() throws SAMLException {
        this.extensionElements = new ArrayList<SAMLElementFactory<? extends SAMLObject>>();
        SAMLHelper.initialize();
    }
    
    @Override
    public Extensions produce() throws SAMLException {
        Extensions extensions = SAMLHelper.createElement(Extensions.class, new QName("urn:oasis:names:tc:SAML:2.0:protocol", Extensions.LOCAL_NAME, "saml2p"));
        for (SAMLElementFactory<? extends SAMLObject> element: this.extensionElements) {
            extensions.getUnknownXMLObjects().add(element.produce());
        }
        return extensions;
    }
    
    @Override
    public void addFactory(SAMLElementFactory<? extends SAMLObject> element) {
        this.extensionElements.add(element);
    }
    
}
