package com.lightweight.drm.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;

import com.lightweight.drm.packager.PublicPrivateKeyGen;
import com.lightweight.drm.utils.UtilityMethods;


/**
 * + device
 * + content
 * + domain
 * + user
 * 
 * @author indranilm
 *
 */
public class DRMAgent {
	
	private String uniqueIdentifier;
	private String domainControllerURL;
	
	/**
	 * Wake up the DRMAgent
	 * 
	 * @param uniqueIdentifier - This is device specific
	 * @param domainControllerURL - URL to connect to the domain controller
	 */
	public DRMAgent(String uniqueIdentifier, String domainControllerURL) {
		this.domainControllerURL = domainControllerURL;
		this.uniqueIdentifier = uniqueIdentifier;
	}

	
	public void domainAuthorization() {
		// check if belongs to domain 
	}
	
	/**
	 * Share public key with domain controller
	 * keep the private keep safe
	 * @return
	 */
	public String sharePublicKey() {
		// generate public-private key
		// share public with domain controller
		return null;
	}

	private void decryptLicenseKey() {
		// use the private key to get the License key 
	}
	
	/**
	 * Decrypts and then copies the contents of a given file.
	 * @throws GeneralSecurityException 
	 */
	public void decryptPayload(File in, File out) throws IOException,
			GeneralSecurityException {
		CipherInputStream is = new CipherInputStream(new FileInputStream(in),
				PublicPrivateKeyGen.getInstance()
						.getCipher(Cipher.DECRYPT_MODE));
		FileOutputStream os = new FileOutputStream(out);
		UtilityMethods.copy(is, os);
		is.close();
		os.close();
	}


	public void getLicense() {
		
	}

}
