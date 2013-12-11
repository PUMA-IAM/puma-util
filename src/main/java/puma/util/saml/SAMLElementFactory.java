package puma.util.saml;

import org.opensaml.xml.XMLObject;

import puma.util.exceptions.SAMLException;

public interface SAMLElementFactory<T extends XMLObject> {
    public T produce() throws SAMLException;
}
