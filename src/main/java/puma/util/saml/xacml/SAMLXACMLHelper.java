/*******************************************************************************
 * Copyright 2013 KU Leuven Research and Developement - IBBT - Distrinet 
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
 *  Copyright 2012 KU Leuven Research and Developement - IBBT - Distrinet 
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 *  Administrative Contact: dnet-project-office@cs.kuleuven.be
 *  Technical Contact: maarten.decat@cs.kuleuven.be
 *  Author: maarten.decat@cs.kuleuven.be
 */

package puma.util.saml.xacml;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.joda.time.DateTime;
import org.opensaml.Configuration;
import org.opensaml.DefaultBootstrap;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeQuery;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.AttributeValue;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.Status;
import org.opensaml.saml2.core.StatusCode;
import org.opensaml.saml2.core.Subject;
import org.opensaml.saml2.core.impl.AttributeQueryMarshaller;
import org.opensaml.saml2.core.impl.ResponseMarshaller;
import org.opensaml.xacml.ctx.RequestType;
import org.opensaml.xacml.ctx.ResponseType;
import org.opensaml.xacml.ctx.impl.RequestTypeImplBuilder;
import org.opensaml.xacml.ctx.impl.ResponseTypeImplBuilder;
import org.opensaml.xacml.profile.saml.XACMLAuthzDecisionQueryType;
import org.opensaml.xacml.profile.saml.XACMLAuthzDecisionStatementType;
import org.opensaml.xacml.profile.saml.impl.XACMLAuthzDecisionStatementTypeMarshaller;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.XMLObjectBuilder;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.schema.XSAny;
import org.opensaml.xml.util.XMLHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Provides some useful helper functions for handling SAML messages.
 * 
 * @author maartend
 * 
 */
public class SAMLXACMLHelper {

	private DocumentBuilder builder;

	private static final Logger logger = Logger.getLogger(SAMLXACMLHelper.class
			.getName());

	public SAMLXACMLHelper() throws ParserConfigurationException,
			ConfigurationException {
		// always do this
		DefaultBootstrap.bootstrap();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		builder = factory.newDocumentBuilder();
	}

	/**************************************
	 * CREATING STUFF
	 **************************************/

	/**
	 * Creates a SAML object of type T with given QName.
	 * 
	 * @param cls
	 * @param qname
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T create(Class<T> cls, QName qname) {
		return (T) ((XMLObjectBuilder) Configuration.getBuilderFactory()
				.getBuilder(qname)).buildObject(qname);
	}

	/**
	 * Creates an Issuer.
	 */
	public Issuer createIssuer(String value) {
		Issuer issuer = create(Issuer.class, Issuer.DEFAULT_ELEMENT_NAME);
		issuer.setValue(value);
		return issuer;
	}

	/**
	 * Creates a NameID.
	 */
	public NameID createNameID(String value) {
		NameID nameID = create(NameID.class, NameID.DEFAULT_ELEMENT_NAME);
		nameID.setValue(value);
		return nameID;
	}

	/**
	 * Creates a subject.
	 */
	public Subject createSubject(String name) {
		Subject subject = create(Subject.class, Subject.DEFAULT_ELEMENT_NAME);
		subject.setNameID(createNameID(name));
		return subject;
	}

	/**
	 * Creates an assertion with issue instant now and no statements yet.
	 */
	public Assertion createAssertion(String issuer, String subjectName) {
		Assertion assertion = create(Assertion.class,
				Assertion.DEFAULT_ELEMENT_NAME);
		assertion.setSubject(createSubject(subjectName));
		assertion.setIssuer(createIssuer(issuer));

		DateTime now = new DateTime();
		assertion.setIssueInstant(now);

		return assertion;
	}

	/**
	 * Creates an Attribute without value, with NameFormat:
	 * urn:oasis:names:tc:SAML:2.0:attrname-format:uri.
	 */
	public Attribute createAttribute(String friendlyName, String name) {
		Attribute attribute = create(Attribute.class,
				Attribute.DEFAULT_ELEMENT_NAME);
		attribute.setFriendlyName(friendlyName);
		attribute.setName(name);
		attribute
				.setNameFormat("urn:oasis:names:tc:SAML:2.0:attrname-format:uri");
		return attribute;
	}

	/**
	 * Creates an Attribute with given string value, with NameFormat:
	 * urn:oasis:names:tc:SAML:2.0:attrname-format:uri.
	 */
	public Attribute createAttribute(String name, String friendlyName,
			String... values) {
		Attribute attribute = createAttribute(name, friendlyName);

		@SuppressWarnings("rawtypes")
		XMLObjectBuilder builder = Configuration.getBuilderFactory()
				.getBuilder(XSAny.TYPE_NAME);
		List<XMLObject> attributeValues = attribute.getAttributeValues();
		for (String value : values) {
			XSAny v = (XSAny) builder
					.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME);
			v.setTextContent(value);
			attributeValues.add(v);
		}
		return attribute;
	}

	/**
	 * Creates an attribute statement with given attributes.
	 */
	public AttributeStatement createAttributeStatement(Attribute... attributes) {
		AttributeStatement statement = create(AttributeStatement.class,
				AttributeStatement.DEFAULT_ELEMENT_NAME);
		for (Attribute a : attributes) {
			statement.getAttributes().add(a);
		}
		return statement;
	}

	/**
	 * Creates an AttributeQuery without attributes asked for.
	 */
	public AttributeQuery createAttributeQuery(String subjectName) {
		AttributeQuery query = create(AttributeQuery.class,
				AttributeQuery.DEFAULT_ELEMENT_NAME);
		query.setSubject(createSubject(subjectName));
		return query;
	}

	/**
	 * Creates a Status object with given value.
	 */
	public Status createStatus(String value) {
		Status status = create(Status.class, Status.DEFAULT_ELEMENT_NAME);
		StatusCode statusCode = create(StatusCode.class,
				StatusCode.DEFAULT_ELEMENT_NAME);
		statusCode.setValue(value);
		status.setStatusCode(statusCode);
		return status;
	}

	/**
	 * Creates a Response with issue instant now.
	 */
	public Response createResponse(String issuer, String statusValue) {
		Response response = create(Response.class,
				Response.DEFAULT_ELEMENT_NAME);
		response.setIssuer(createIssuer(issuer));
		response.setStatus(createStatus(statusValue));

		DateTime now = new DateTime();
		response.setIssueInstant(now);

		return response;
	}

	/**
	 * Creates a request from a Node representing this request.
	 * 
	 * @throws JAXBException
	 */
	public RequestType createRequestType(Element requestRoot) {
		RequestTypeImplBuilder builder = new RequestTypeImplBuilder();
		RequestType request = builder.buildObject();
		request.setDOM(requestRoot);
		return request;
	}

	public XACMLAuthzDecisionQueryType createXACMLAuthzDecisionQuery(
			Element requestRoot, String providerId) {
		XACMLAuthzDecisionQueryType authzQuery = create(
				XACMLAuthzDecisionQueryType.class,
				XACMLAuthzDecisionQueryType.DEFAULT_ELEMENT_NAME_XACML20);
		authzQuery.setIssuer(createIssuer(providerId));
		authzQuery.setRequest(createRequestType(requestRoot));
		return authzQuery;
	}

	public XACMLAuthzDecisionStatementType createXACMLAuthzDecisionStatement(ResponseType response) {
		XACMLAuthzDecisionStatementType decision = create(
				XACMLAuthzDecisionStatementType.class,
				XACMLAuthzDecisionStatementType.DEFAULT_ELEMENT_NAME_XACML20);
		decision.setResponse(response);
		return decision;
	}
	
	public ResponseType createResponseType(Element responseRoot) {
		ResponseTypeImplBuilder builder = new ResponseTypeImplBuilder();
		ResponseType response = builder.buildObject();
		response.setDOM(responseRoot);
		return response;		
	}

	/**************************************
	 * PRINTING STUFF
	 **************************************/

	/**
	 * Returns the XMLObject as DOM Document.
	 * 
	 * @param object
	 * @return
	 * @throws IOException
	 * @throws MarshallingException
	 * @throws TransformerException
	 * @throws ParserConfigurationException
	 */
	public Document asDOMDocument(XMLObject object) throws IOException,
			MarshallingException, TransformerException,
			ParserConfigurationException {
		Document document = builder.newDocument();
		Marshaller out = Configuration.getMarshallerFactory().getMarshaller(
				object);
		out.marshall(object, document);
		return document;
	}

	/**
	 * Formants and returns an AttributeQuery object.
	 * 
	 * @throws MarshallingException
	 */
	public String format(AttributeQuery query) throws MarshallingException {
		Marshaller marshaller = new AttributeQueryMarshaller();
		return XMLHelper.prettyPrintXML(marshaller.marshall(query));
	}

	/**
	 * Formats and returns a Response object.
	 * 
	 * @throws MarshallingException
	 */
	public String format(Response response) throws MarshallingException {
		Marshaller marshaller = new ResponseMarshaller();
		return formatNode(marshaller.marshall(response));
	}

	public String format(XACMLAuthzDecisionQueryType authzQuery)
			throws MarshallingException {
		Marshaller marshaller = new XACMLAuthzDecisionStatementTypeMarshaller();
		return formatNode(marshaller.marshall(authzQuery));
	}

	/**
	 * Helper method to read an XML object from a DOM element.
	 */
	public static XMLObject fromElement(Element element) throws IOException,
			UnmarshallingException, SAXException {
		return Configuration.getUnmarshallerFactory().getUnmarshaller(element)
				.unmarshall(element);
	}

	/**
	 * Formats a general DOM node.
	 */
	public static String formatNode(Node node) {
		return XMLHelper.prettyPrintXML(node);
	}

	/**
	 * Helper method to read an XML object from a file.
	 */
	public XMLObject fromString(String xml) throws IOException,
			UnmarshallingException, SAXException {
		Document doc = builder.parse(new InputSource(new StringReader(xml)));
		return fromElement(doc.getDocumentElement());
	}
}
