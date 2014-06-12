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
package puma.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author jasper
 */
public class PasswordHasher {
	private final static Integer numberOfIterations = 2000;
	private final static Integer saltLength = 10;        
        private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";
    
	public static byte[] getHashValue(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
            return PasswordHasher.pbkdf2(password.toCharArray(), salt);
	}
	
        private static byte[] pbkdf2(char[] password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
            // http://crackstation.net/hashing-security.htm
            PBEKeySpec spec = new PBEKeySpec(password, salt, PasswordHasher.numberOfIterations, password.length + salt.length);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
            return skf.generateSecret(spec).getEncoded();
        }
        
	public static byte[] generateSalt() {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[PasswordHasher.saltLength];
            random.nextBytes(salt);
            return salt;
	}
	
	public static Boolean equalHash(byte[] one, byte[] two) {
        for (Integer i = 0; i < one.length && i < two.length; i++) {
            if (!((Byte) one[i]).equals((Byte) two[i])) {
                return false;
            }
        }
        if (one.length != two.length) {
            return false;
        }
        return true;
    }
}
