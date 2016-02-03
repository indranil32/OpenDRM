package com.open.drm.packager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;

import com.open.drm.endpoint.EndPoint;
import com.open.drm.endpoint.EndPointImpl;
import com.open.drm.packager.model.PackagingDetails;
import com.open.drm.utils.DBManager;
import com.open.drm.utils.PublicPrivateKeyGen;
import com.open.drm.utils.UtilityMethods;
import com.open.drm.utils.model.SetUpObject;

public class Packager {

	private PublicPrivateKeyGen keyGen;
	private DBManager dbManager;
	private EndPointImpl endPoint;
	private SetUpObject setup;

	public Packager(PublicPrivateKeyGen keyGen, DBManager dbManager,
			EndPoint endPoint, SetUpObject setup) {
		this.keyGen = keyGen;
		this.dbManager = dbManager;
		this.endPoint = (EndPointImpl) endPoint;
		this.setup = setup;
	}

	private PackagingDetails encrypt(File in, File out) throws IOException,
			GeneralSecurityException {
		byte[] aeskey = keyGen.makeAESKey();
		System.out.println(aeskey);
		FileInputStream is = new FileInputStream(in);
		CipherOutputStream os = new CipherOutputStream(
				new FileOutputStream(out), keyGen.getCipher(
						Cipher.ENCRYPT_MODE, aeskey, setup.aes));
		UtilityMethods.copy(is, os);
		os.close();
		is.close();
		PackagingDetails element = new PackagingDetails();
		element.encryptedSourceContentFile = out;
		element.clearSourceContentFile = in;
		element.createdDate = new Date();
		element.algo = "AES";
		element.key = aeskey;
		element.id = setup.contentId;
		element.keyFile = UtilityMethods.byteArrayToFile(aeskey, setup.clearSourceKeyFile);
		return element;
	}

	private void publish(PackagingDetails element) {
		PackagingDetails dbElement = (PackagingDetails) dbManager.get(element);
		dbManager.add(dbElement);
		endPoint.publish(dbElement);
	}

	public void startPackaging() throws IOException, GeneralSecurityException {
		File sourceFile = new File(setup.clearSourceFile);
		File encryptedFile = new File(setup.encryptedSourceFile);
		PackagingDetails element = encrypt(sourceFile, encryptedFile);
		// TODO drm-codec
		dbManager.add(element);
		publish(element);
	}
}
