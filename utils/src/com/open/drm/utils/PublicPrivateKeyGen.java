package com.open.drm.utils;

import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class PublicPrivateKeyGen {
	
	public static final int AES_Key_Size = 256;
	// client keys
	private Cipher rsaCipher; 
	// server keys
	private Cipher aesCipher;
	
	
	public PublicPrivateKeyGen() throws GeneralSecurityException {
		// create RSA public key cipher
		rsaCipher = Cipher.getInstance("RSA");
	    // create AES shared key cipher
	    aesCipher = Cipher.getInstance("AES");
	}
	
	/**
	 * The key to encrypt the content
	 * SecretKeySpec aeskeySpec = new SecretKeySpec(aesKey, "AES");
	 */
	public byte[] makeAESKey() throws NoSuchAlgorithmException {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
	    kgen.init(AES_Key_Size);
	    SecretKey key = kgen.generateKey();
	    return key.getEncoded();
	}
	
	/**
	 * The key to encrypt the above key
	 * @throws InvalidKeySpecException 
	 */
	public void makeRSAKeyPair(byte[] encodedKey, PublicKey publicKey, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeyFactory kf = KeyFactory.getInstance("RSA");
		
		// create public key
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedKey);
		publicKey = kf.generatePublic(publicKeySpec);
		
		// create private key
		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedKey);
		privateKey = kf.generatePrivate(privateKeySpec);
	}

	public final Cipher getCipher(int MODE, byte[] key, String algo) throws InvalidKeyException {
		SecretKeySpec keySpec = new SecretKeySpec(key, algo);
		if (algo.equalsIgnoreCase("AES")) {
			aesCipher.init(MODE, keySpec);
			return aesCipher;
		} else if (algo.equalsIgnoreCase("RSA")){
			rsaCipher.init(MODE, keySpec);
			return aesCipher;
		} else {
			throw new InvalidKeyException();
		}
	}
	
	public final Cipher getRSACipher(int MODE, Key key) throws InvalidKeyException {
		rsaCipher.init(MODE, key);
		return rsaCipher;		
	}

	/**
	 * Decrypts the AES key using an RSA private key
	 *//*
	public void loadKey(File in, File privateKeyFile) throws GeneralSecurityException, IOException {
		// read private key to be used to decrypt the AES key
		byte[] encodedKey = new byte[(int)privateKeyFile.length()];
		new FileInputStream(privateKeyFile).read(encodedKey);
		
		// create private key
		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedKey);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PrivateKey pk = kf.generatePrivate(privateKeySpec);
		
		// read AES key
		rsaCipher.init(Cipher.DECRYPT_MODE, pk);
		byte [] aesKey = new byte[AES_Key_Size/8];
		CipherInputStream is = new CipherInputStream(new FileInputStream(in), rsaCipher);
		is.read(aesKey);
		SecretKeySpec aeskeySpec = new SecretKeySpec(aesKey, "AES");
	}*/
	
	/**
	 * Encrypts the AES key to a file using an RSA public key
	 */
	/*public void saveKey(File out, File publicKeyFile) throws IOException, GeneralSecurityException {
		// read public key to be used to encrypt the AES key
		byte[] encodedKey = new byte[(int)publicKeyFile.length()];
		new FileInputStream(publicKeyFile).read(encodedKey);
		
		// create public key
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedKey);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PublicKey pk = kf.generatePublic(publicKeySpec);
		
		// write AES key
		rsaCipher.init(Cipher.ENCRYPT_MODE, pk);
		CipherOutputStream os = new CipherOutputStream(new FileOutputStream(out), rsaCipher);
		//os.write(aesKey);
		os.close();
	}*/
	
}
