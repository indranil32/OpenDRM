package com.lightweight.drm.packager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;

import com.lightweight.drm.domain.controller.DomainManager;
import com.lightweight.drm.utils.DBManager;
import com.lightweight.drm.utils.EndPoint;
import com.lightweight.drm.utils.UtilityMethods;
import com.lightweight.drm.packager.model.*;

public class Packager {

	private PublicPrivateKeyGen keyGen;
	private String tmpFolder;
	private DBManager dbManager;
	
	public Packager(PublicPrivateKeyGen keyGen, String tmpFolder, DBManager dbManager) {
		this.keyGen = keyGen;
		this.tmpFolder = tmpFolder;
		this.dbManager = dbManager;
	}

	private void encrypt(File in, File out) throws IOException,
			GeneralSecurityException {
		FileInputStream is = new FileInputStream(in);
		CipherOutputStream os = new CipherOutputStream(
				new FileOutputStream(out),
				keyGen.getCipher(Cipher.ENCRYPT_MODE));
		UtilityMethods.copy(is, os);
		os.close();
		PackagingDetails element = new PackagingDetails();
		
		dbManager.add(element);
	}

	public void publish(DomainManager d) {

	}

	public void publish(EndPoint cdn) {

	}

	public void startPackage(String source) throws IOException,
			GeneralSecurityException {
		File sourceFile = new File(source);
		File encryptedFile = new File(tmpFolder + File.separator + sourceFile.getName());
		encrypt(sourceFile, encryptedFile);
	}
}
