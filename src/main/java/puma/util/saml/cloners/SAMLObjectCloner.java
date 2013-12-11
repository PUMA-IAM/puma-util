package puma.util.saml.cloners;
import org.opensaml.common.SAMLObject;

import puma.util.exceptions.SAMLException;
	
public interface SAMLObjectCloner<T extends SAMLObject> {
	public T cloneElement(T item) throws SAMLException;
}
