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
