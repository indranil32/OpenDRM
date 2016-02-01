package com.open.drm.packager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;

import com.open.drm.endpoint.EndPoint;
import com.open.drm.endpoint.EndPointImpl;
import com.open.drm.packager.model.PackagingDetails;
import com.open.drm.utils.DBManager;
import com.open.drm.utils.UtilityMethods;
import com.open.drm.utils.model.SetUpObject;

public class Packager {

	private PublicPrivateKeyGen keyGen;
	private String tmpFolder;
	private DBManager dbManager;
	private EndPointImpl endPoint;
	private SetUpObject setup;
	
	public Packager(PublicPrivateKeyGen keyGen, String tmpFolder, DBManager dbManager, EndPoint endPoint, SetUpObject setup) {
		this.keyGen = keyGen;
		this.tmpFolder = tmpFolder;
		this.dbManager = dbManager;
		this.endPoint = (EndPointImpl) endPoint;
		this.setup = setup;
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

	private void publish(PackagingDetails element) {
		PackagingDetails dbElement = (PackagingDetails) dbManager.get(element);
		// add details
		dbManager.add(dbElement);
		endPoint.publish(dbElement);
	}

	public void startPackaging(String source) throws IOException,
			GeneralSecurityException {
		File sourceFile = new File(source);
		File encryptedFile = new File(tmpFolder + File.separator + sourceFile.getName());
		// encrypt
		PackagingDetails element = encrypt(sourceFile, encryptedFile);
		// TODO drm-codec 
		// put packaging details in DB
		dbManager.add(element);
		// publish to end point
		publish(element);
	}
}
