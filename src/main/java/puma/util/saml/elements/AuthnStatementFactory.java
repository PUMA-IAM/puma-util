package puma.util.saml.elements;

import org.joda.time.DateTime;
import org.opensaml.saml2.core.AuthnContext;
import org.opensaml.saml2.core.AuthnStatement;

import puma.util.exceptions.SAMLException;
import puma.util.saml.ObjectFactory;
import puma.util.saml.SAMLHelper;

public class AuthnStatementFactory implements ObjectFactory<AuthnStatement> {
	private DateTime authnInstant;
	private AuthnContext context;
		
	public AuthnStatementFactory(DateTime authnInstant, AuthnContext context) throws SAMLException {
		SAMLHelper.initialize();
		this.authnInstant = authnInstant;
		this.context = context;
	}
	
	@Override
	public AuthnStatement produce() throws SAMLException {
		AuthnStatement result = SAMLHelper.createElement(AuthnStatement.class);
		result.setAuthnInstant(this.authnInstant);
		if (this.context != null) {
			result.setAuthnContext(context);
		}
		return result;
	}

}
