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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import org.opensaml.saml2.core.Advice;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.AuthnStatement;
import org.opensaml.saml2.core.AuthzDecisionStatement;
import org.opensaml.saml2.core.Condition;
import org.opensaml.saml2.core.Conditions;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.Subject;
import org.opensaml.xml.XMLObject;
import puma.util.exceptions.ElementNotFoundException;
import puma.util.exceptions.SAMLException;
import puma.util.saml.CompoundFactory;
import puma.util.saml.CompoundFactoryHolder;
import puma.util.saml.ObjectFactory;
import puma.util.saml.RetrievableCompoundFactory;
import puma.util.saml.SAMLElementFactory;
import puma.util.saml.SAMLHelper;

public class AssertionFactory implements ObjectFactory<Assertion>, CompoundFactory<Condition> {
	private String assertionIdentifier;
	private Issuer issuer;
	private DateTime issueInstant;
	private Advice advice;
	private Subject subject;
	private Conditions conditionsObject;
	private List<SAMLElementFactory<? extends Condition>> conditionFactories;
	private List<AttributeStatementFactory> attributeStatementFactories;
	private Map<Class<? extends XMLObject>, CompoundFactoryHolder<RetrievableCompoundFactory<? extends SAMLElementFactory<?>>>> iterators;

	public AssertionFactory(String assertionId, Issuer issuer) throws SAMLException {
		this(assertionId, issuer, new DateTime());
	}
	
	public AssertionFactory(String assertionId, Issuer issuer, DateTime issueInstant) throws SAMLException {
		this(assertionId, issuer, issueInstant, null, null);
	}
	
	public AssertionFactory(String assertionId, Issuer issuer, DateTime issueInstant, Advice advice) throws SAMLException {
		this(assertionId, issuer, issueInstant, null, advice);
	}
	
	public AssertionFactory(String assertionId, Issuer issuer, DateTime issueInstant, Subject subject) throws SAMLException {
		this(assertionId, issuer, issueInstant, subject, null);
	}
	
	public AssertionFactory(String assertionId, Issuer issuer, DateTime issueInstant, Subject subject, Advice advice) throws SAMLException {
		SAMLHelper.initialize();
		this.assertionIdentifier = assertionId;
		this.issuer = issuer;
		this.issueInstant = issueInstant;
		this.advice = advice;
		this.subject = subject;
		this.conditionFactories = new ArrayList<SAMLElementFactory<? extends Condition>>();
		this.attributeStatementFactories = new ArrayList<AttributeStatementFactory>();
		this.conditionsObject = SAMLHelper.createElement(Conditions.class);
		this.iterators = new HashMap<Class<? extends XMLObject>, CompoundFactoryHolder<RetrievableCompoundFactory<? extends SAMLElementFactory<?>>>>();
		this.iterators.put(AttributeStatement.class, null); // LATER Add new CompoundFactoryHolder<AttributeStatementFactory>()
		this.iterators.put(AuthnStatement.class, null); // LATER Add AuthnStatementFactory
		this.iterators.put(AuthzDecisionStatement.class, null); // LATER Add AuthzDecisionFactory
	}
	
	
	@Override
	public Assertion produce() throws SAMLException {
		Assertion result = SAMLHelper.createElement(Assertion.class);
		result.setID(this.assertionIdentifier);
		if (this.advice != null)
			result.setAdvice(this.advice);
		result.setIssueInstant(this.issueInstant);
		result.setIssuer(this.issuer);
		if (this.subject != null)
			result.setSubject(this.subject);
		for (SAMLElementFactory<? extends Condition> factory: this.conditionFactories) 
			this.conditionsObject.getConditions().add(factory.produce());
		/*
		for (CompoundFactoryHolder<RetrievableCompoundFactory<? extends SAMLElementFactory<?>>> nextHolder: this.iterators.values()) {
			while (nextHolder.hasNext()) {
				RetrievableCompoundFactory<? extends SAMLElementFactory<?>> compoundFactory = nextHolder.next();
				for (SAMLElementFactory<?> factory: compoundFactory.retrieveFactories()) {
					factory.produce();	// LATER Use reflection to put these elements in their right place
				}
			}
		}
		*/
		result.setConditions(this.conditionsObject);
		// The code below should be outphased in favor of the more generic use of CompoundFactoryHolders (later)
		for (AttributeStatementFactory factory: this.attributeStatementFactories) {
			result.getAttributeStatements().add(factory.produce());
		}
		return result;
	}

	@Override
	public void addFactory(SAMLElementFactory<? extends Condition> element) {
		this.conditionFactories.add(element);		
	}
	
	public void setConditionProperties(DateTime notBefore, DateTime notOnOrAfter) {
		if (notBefore != null)
			this.conditionsObject.setNotBefore(notBefore);
		if (notOnOrAfter != null)
			this.conditionsObject.setNotOnOrAfter(notOnOrAfter);
		// LATER this.conditionsObject.getAudienceRestrictions().add(null);
	}

	public <T> CompoundFactoryHolder<?> getIterators(Class<T> classObject) throws ElementNotFoundException {
		if (!this.iterators.containsKey(classObject))
			throw new ElementNotFoundException(classObject.getName());
		return this.iterators.get(classObject);
	}
	
	public void addAttributeStatementFactory(AttributeStatementFactory element) {
		this.attributeStatementFactories.add(element);
	}
}
