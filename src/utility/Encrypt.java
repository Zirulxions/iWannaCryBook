package utility;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt {
private String hashtext;

	public Encrypt(String password) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] pDigest = md.digest(password.getBytes());
		BigInteger number = new BigInteger(1,pDigest);
		hashtext = number.toString(16);
	}
	
	public String returnEncrypt() {
		return hashtext;	
	}
	
}
