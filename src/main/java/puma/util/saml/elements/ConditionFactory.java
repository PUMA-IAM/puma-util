package puma.util.saml.elements;

import org.opensaml.saml2.core.Condition;

import puma.util.exceptions.SAMLException;
import puma.util.saml.ObjectFactory;
import puma.util.saml.SAMLHelper;

public class ConditionFactory implements ObjectFactory<Condition>{

	public ConditionFactory() throws SAMLException {
		SAMLHelper.initialize();
	}
	
	
	@Override
	public Condition produce() throws SAMLException {
		Condition result = SAMLHelper.createElement(Condition.class);
		// LATER Produce Condition object
		return result;
	}
}
