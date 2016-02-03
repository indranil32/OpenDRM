package com.open.drm.domain.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;

import com.open.drm.domain.controller.model.Domain;
import com.open.drm.packager.model.PackagingDetails;
import com.open.drm.utils.DBManager;
import com.open.drm.utils.PublicPrivateKeyGen;
import com.open.drm.utils.UtilityMethods;
import com.open.drm.utils.model.SetUpObject;

public class DomainManagerImpl implements DomainManager{

	private DBManager dbManager;
	private DBManager registryManager;
	private SetUpObject setup;
	private PublicPrivateKeyGen keyGen;
	
	public DomainManagerImpl(DBManager dbManager, DBManager registryManager, SetUpObject setup, PublicPrivateKeyGen keyGen) {
		this.dbManager = dbManager;
		this.registryManager = registryManager;
		this.setup = setup;
		this.keyGen = keyGen;
	}

	public void registration(String uniqueIdentifier) {
		Domain domain = new Domain();
		domain.id = uniqueIdentifier;
		registryManager.add(domain);
	}

	public byte[] license(String content, PublicKey publicKey) throws InvalidKeyException, IOException {
		PackagingDetails element = new PackagingDetails();
		element.id = setup.contentId;
		element = (PackagingDetails) dbManager.get(element);
		element.licenseFile = encryptKey(element.keyFile, publicKey);
		element.license = UtilityMethods.fileToByteArray(element.licenseFile.getAbsolutePath());
		dbManager.update(element);
		return element.license;
	}

	private File encryptKey(File in, PublicKey publicKey) throws InvalidKeyException, IOException {
		FileInputStream is = new FileInputStream(in);
		File out = new File(setup.encryptedSourceKeyFile);
		CipherOutputStream os = new CipherOutputStream(new FileOutputStream(out), keyGen.getRSACipher(Cipher.ENCRYPT_MODE, publicKey));
		UtilityMethods.copy(is, os);
		os.close();
		is.close();
		return out;
	}

	public void checkDomain(String uniqueIdentifier) {
		Domain domain = new Domain();
		domain.id = uniqueIdentifier;
		registryManager.get(domain);
	}
	
	public void customValidation() {

	}
	
}
