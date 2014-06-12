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
package puma.util.saml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.opensaml.DefaultBootstrap;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.StatusCode;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.XMLObjectBuilder;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.validation.ValidationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import puma.util.exceptions.SAMLException;
import puma.util.exceptions.saml.ElementProcessingException;
import puma.util.exceptions.saml.ServiceParameterException;

/**
 *
 * @author jasper
 */
public class SAMLHelper {
    public SAMLHelper() throws SAMLException {
        SAMLHelper.initialize();     
    }
    
    public static void initialize() throws SAMLException {
        try {
			DefaultBootstrap.bootstrap();
		} catch (ConfigurationException e) {
			throw new SAMLException(e);
		}
    }
    
    /**
     * Creates a SAML element corresponding to the given class name and with the specified qualified name
     * @param elementClassName The class for which to make an element
     * @param qname The qualified name for the element to create
     * @return An object of class <code>elementClassName</code>.
     * @throws IllegalAccessException 
     * @throws NoSuchFieldException 
     * @throws IllegalArgumentException 
     * @throws SecurityException 
     */
    @SuppressWarnings("unchecked")
    public static <T> T createElement(Class<T> elementClassName, QName qname) throws SAMLException {
        try {
        	if (qname == null)
				return createElement(elementClassName);
        } catch (SecurityException e) {
				throw new SAMLException(e);
		} catch (IllegalArgumentException e) {
			throw new SAMLException(e);
		}
        return (T) ((XMLObjectBuilder<? extends XMLObject>) Configuration.getBuilderFactory().getBuilder(qname)).buildObject(qname);
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T createElement(Class<T> elementClassName, QName elementName, QName typeName) {
        return (T) ((XMLObjectBuilder<? extends XMLObject>) Configuration.getBuilderFactory().getBuilder(typeName)).buildObject(elementName, typeName);
    }
    
    /**
     * Creates a SAML element corresponding to the given class name and with the specified qualified name
     * @param elementClassName The class for which to make an element. Takes DEFAULT_ELEMENT_NAME.
     * @return An object of class <code>elementClassName</code>.
     * @throws NoSuchFieldException 
     * @throws SecurityException 
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    public static <T> T createElement(Class<T> elementClassName) throws SAMLException {
		try {
			Field field = elementClassName.getDeclaredField("DEFAULT_ELEMENT_NAME");
			field.setAccessible(true);
			return SAMLHelper.createElement(elementClassName, (QName) field.get(null));
		} catch (SecurityException e) {
			throw new SAMLException(e);
		} catch (NoSuchFieldException e) {
			throw new SAMLException(e);
		} catch (IllegalArgumentException e) {
			throw new SAMLException(e);
		} catch (IllegalAccessException e) {
			throw new SAMLException(e);
		}
    }
    
    @SuppressWarnings("unchecked")
	public static <T> T processString(String message, Class<T> className) throws SAMLException {
    	try {
	        DocumentBuilderFactory newFactory = DocumentBuilderFactory.newInstance();
	        newFactory.setNamespaceAware(true);
	        DocumentBuilder builder = newFactory.newDocumentBuilder();
	        Document doc = builder.parse(new ByteArrayInputStream(message.getBytes("UTF-8")));
	        Element samlElement = doc.getDocumentElement();            
	        Unmarshaller unmarshaller = Configuration.getUnmarshallerFactory().getUnmarshaller(samlElement);
	        if (unmarshaller == null) {
	            throw new ElementProcessingException(samlElement.toString(), "No unmarshaller");
	        }
	        XMLObject samlObject = unmarshaller.unmarshall(samlElement);
	        if (!SAMLHelper.isSpecifiedElement(samlObject, className)) {
	            throw new ServiceParameterException("Parameter " + message + " is not of type AttributeQuery");
	        }
	        return (T) samlObject;
    	} catch (ParserConfigurationException e) {
			throw new SAMLException(e);
		} catch (UnsupportedEncodingException e) {
			throw new SAMLException(e);
		} catch (SAXException e) {
			throw new SAMLException(e);
		} catch (IOException e) {
			throw new SAMLException(e);
		} catch (ElementProcessingException e) {
			throw new SAMLException(e);
		} catch (UnmarshallingException e) {
			throw new SAMLException(e);
		} catch (SecurityException e) {
			throw new SAMLException(e);
		} catch (IllegalArgumentException e) {
			throw new SAMLException(e);
		} catch (ServiceParameterException e) {
			throw new SAMLException(e);
		}
    }

    private static <T> boolean isSpecifiedElement(XMLObject message, Class<T> className) throws SAMLException {
    	try {
	        Field field = className.getDeclaredField("DEFAULT_ELEMENT_LOCAL_NAME");
	        field.setAccessible(true); 
	        return message.getElementQName().getLocalPart().equals((field.get(null)));
    	} catch (NoSuchFieldException e) {
			throw new SAMLException(e);
		} catch (IllegalAccessException e) {
			throw new SAMLException(e);
		} 
    }
    
    public static Boolean verifyResponse(Response authnResponse) {
        try {
            authnResponse.validate(true);
        } catch (ValidationException ex) {
            return false;
        }
        if (!authnResponse.getStatus().getStatusCode().getValue().equals(StatusCode.SUCCESS_URI)) {
            return false;
        }
        if (!SAMLHelper.verifySignature(authnResponse.getSignature())) {
            return false;
        }
        for (Assertion ass: authnResponse.getAssertions()) {
            // Checks on assertions of the authentication response are done here
            if (!SAMLHelper.verifyAssertion(ass)) {
                return false;
            }
        }
        return true;
    }
    
    public static Boolean verifyAssertion(Assertion attrResponse) {
        try {
            attrResponse.validate(true);
        } catch (ValidationException ex) {
            return false;
        }
        if (attrResponse.getIssueInstant() != null && attrResponse.getIssueInstant().isAfterNow()) {
            return false;
        }
        if (!SAMLHelper.verifySignature(attrResponse.getSignature())) {
            return false;
        }
        return true;
    }
    
    public static Boolean verifySignature(Signature sig) {
        return true; // LATER Signature verification module here
    }
}
