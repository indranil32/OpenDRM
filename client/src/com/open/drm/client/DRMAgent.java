package com.open.drm.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;

import com.open.drm.domain.controller.ClientDomainManagerImpl;
import com.open.drm.domain.controller.DomainManager;
import com.open.drm.packager.PublicPrivateKeyGen;
import com.open.drm.utils.UtilityMethods;
import com.open.drm.utils.model.SetUpObject;

/**
 * + device + content + domain + user
 * 
 * @author indranilm
 *
 */
public class DRMAgent {

	private ClientDomainManagerImpl domain;
	private String uniqueIdentifier;
	private SetUpObject setup;

	/**
	 * Wake up the DRMAgent
	 * 
	 * @param uniqueIdentifier
	 *            - This is device specific
	 * @param setup
	 * @param domainControllerURL
	 *            - URL to connect to the domain controller
	 */
	public DRMAgent(String uniqueIdentifier, DomainManager domain,
			SetUpObject setup) {
		this.domain = (ClientDomainManagerImpl) domain;
		this.uniqueIdentifier = uniqueIdentifier;
		this.setup = setup;
	}

	public void domainAuthorization() {
		domain.authorize();
	}

	/**
	 * Share public key with domain controller keep the private keep safe
	 * 
	 * @return
	 */
	public String sharePublicKey() {
		// generate public-private key
		// share public with domain controller
		String publicKey = "";
		return domain.share(publicKey);
	}

	private void decryptLicenseKey() {
		// use the private key to get the License key
	}

	/**
	 * Decrypts and then copies the contents of a given file.
	 * 
	 * @throws GeneralSecurityException
	 */
	public FileOutputStream decryptPayload(FileInputStream in)
			throws IOException, GeneralSecurityException {
		CipherInputStream is = new CipherInputStream(in, PublicPrivateKeyGen
				.getInstance().getCipher(Cipher.DECRYPT_MODE));
		FileOutputStream out =  new FileOutputStream(setup.decryptedStream);
		UtilityMethods.copy(is, out);
		return out;
	}

	public void getLicense() {
		domain.getLicense(this.uniqueIdentifier);
	}

	public void register() {
		domain.register(this.uniqueIdentifier);
	}

}
