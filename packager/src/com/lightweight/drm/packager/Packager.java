package com.lightweight.drm.packager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;

import com.lightweight.drm.domain.controller.DomainManager;
import com.lightweight.drm.endpoint.EndPoint;
import com.lightweight.drm.utils.DBManager;
import com.lightweight.drm.utils.UtilityMethods;
import com.lightweight.drm.packager.model.*;

public class Packager {

	private PublicPrivateKeyGen keyGen;
	private String tmpFolder;
	private DBManager dbManager;
	private DomainManager domainManager;
	private EndPoint endPoint;
	
	public Packager(PublicPrivateKeyGen keyGen, String tmpFolder, DBManager dbManager, DomainManager domainManager, EndPoint endPoint) {
		this.keyGen = keyGen;
		this.tmpFolder = tmpFolder;
		this.dbManager = dbManager;
		this.domainManager = domainManager;
		this.endPoint = endPoint;
	}

	private PackagingDetails encrypt(File in, File out) throws IOException,
			GeneralSecurityException {
		FileInputStream is = new FileInputStream(in);
		CipherOutputStream os = new CipherOutputStream(
				new FileOutputStream(out),
				keyGen.getCipher(Cipher.ENCRYPT_MODE));
		UtilityMethods.copy(is, os);
		os.close();
		PackagingDetails element = new PackagingDetails();
		return element;
	}

	private void publish(DomainManager d, PackagingDetails element) {
		PackagingDetails dbElement = (PackagingDetails) dbManager.get(element);
		// add details
		dbManager.add(dbElement);
	}

	private void publish(EndPoint endPoint, PackagingDetails element) {
		PackagingDetails dbElement = (PackagingDetails) dbManager.get(element);
		// add details
		dbManager.add(dbElement);
	}

	public void startPackaging(String source) throws IOException,
			GeneralSecurityException {
		File sourceFile = new File(source);
		File encryptedFile = new File(tmpFolder + File.separator + sourceFile.getName());
		PackagingDetails element = encrypt(sourceFile, encryptedFile);
		// add details
		dbManager.add(element);
		publish(domainManager, element);
		publish(endPoint, element);
	}
}
