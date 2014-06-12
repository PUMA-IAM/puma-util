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
package puma.util.saml.encoding;

import javax.servlet.http.HttpServletRequest;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.BasicSAMLMessageContext;
import org.opensaml.saml2.binding.decoding.HTTPRedirectDeflateDecoder;
import org.opensaml.saml2.core.Response;
import org.opensaml.ws.message.decoder.MessageDecodingException;
import org.opensaml.ws.transport.http.HttpServletRequestAdapter;
import puma.util.exceptions.flow.ResponseProcessingException;

/**
 *
 * @author jasper
 */
public abstract class ResponseHandler {
    protected Response decodeMessage(HttpServletRequest request) throws MessageDecodingException, SecurityException, org.opensaml.xml.security.SecurityException {
        // Decode
        BasicSAMLMessageContext<Response, ? extends SAMLObject, ? extends SAMLObject> messageContext = new BasicSAMLMessageContext<Response, SAMLObject, SAMLObject>();
        messageContext.setInboundMessageTransport(new HttpServletRequestAdapter(request));
        HTTPRedirectDeflateDecoder decoder = new HTTPRedirectDeflateDecoder();
        decoder.decode(messageContext);
        // Fetch attributes
        return messageContext.getInboundSAMLMessage();
    }
    
    public abstract Object interpret(HttpServletRequest request) throws MessageDecodingException, org.opensaml.xml.security.SecurityException, ResponseProcessingException;
}
