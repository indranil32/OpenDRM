package com.open.drm.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;

import com.open.drm.domain.controller.ClientDomainManagerImpl;
import com.open.drm.domain.controller.DomainManager;
import com.open.drm.utils.PublicPrivateKeyGen;
import com.open.drm.utils.UtilityMethods;
import com.open.drm.utils.model.SetUpObject;

/**
 * + device + contentId + domain + user
 * 
 * @author indranilm
 *
 */
public class DRMAgent {

	private ClientDomainManagerImpl domain;
	private PublicPrivateKeyGen keyGen;
	private String uniqueIdentifier;
	private SetUpObject setup;

	public DRMAgent(String uniqueIdentifier, PublicPrivateKeyGen keyGen, DomainManager domain,
			SetUpObject setup) {
		this.domain = (ClientDomainManagerImpl) domain;
		this.uniqueIdentifier = uniqueIdentifier;
		this.setup = setup;
		this.keyGen = keyGen;
	}
	
	public void register() {
		domain.register(this.uniqueIdentifier);
	}
	
	public void domainAuthorization() {
		domain.authorize(this.uniqueIdentifier);
	}

	
	public void sharePublicKey(byte[] aeskey, PublicKey publicKey, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
		aeskey = keyGen.makeAESKey();
		keyGen.makeRSAKeyPair(aeskey, publicKey, privateKey);
	}

	public byte[] getLicense(String content, PublicKey publicKey) throws InvalidKeyException, IOException {
		return  domain.getLicense(content, publicKey);
	}
	
	
	public byte[] decryptLicenseKey(byte[] license, PrivateKey privateKey) throws InvalidKeyException, IOException {
		File in = new File(setup.encryptedClientKeyFile);
		FileOutputStream tmp = new FileOutputStream(in);
		tmp.write(license);
		tmp.close();
		FileInputStream is = new FileInputStream(in);
		File out = new File(setup.clearClientKeyFile);
		CipherOutputStream os = new CipherOutputStream(new FileOutputStream(out), keyGen.getRSACipher(Cipher.DECRYPT_MODE, privateKey));
		UtilityMethods.copy(is, os);
		os.close();
		is.close();
		return UtilityMethods.fileToByteArray(setup.encryptedSourceKeyFile);
	}
	
	public void decryptContent(File in, File out, byte[] aeskey)
			throws IOException, GeneralSecurityException {
		FileInputStream is = new FileInputStream(in);
		CipherInputStream cis = new CipherInputStream(is, keyGen.getCipher(Cipher.DECRYPT_MODE, aeskey, "AES"));
		FileOutputStream os =  new FileOutputStream(out);
		UtilityMethods.copy(cis, os);
		cis.close();
		os.close();
	}
	
	

}
