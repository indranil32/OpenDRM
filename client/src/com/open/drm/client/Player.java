package com.open.drm.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.spec.SecretKeySpec;

import com.open.drm.endpoint.ClientEndPointImpl;
import com.open.drm.endpoint.EndPoint;
import com.open.drm.test.ExceptionFreeProcess;
import com.open.drm.utils.model.SetUpObject;

public class Player {
	
	private ClientEndPointImpl endpoint;
	private DRMAgent agent;
	private SetUpObject setup; 
	
	public Player(DRMAgent agent, EndPoint endpoint, SetUpObject setup) {
		this.endpoint = (ClientEndPointImpl) endpoint;
		this.agent = agent;
		this.setup = setup;
	}

	private File getContent(String content) throws FileNotFoundException {
		return endpoint.getContent(content);
	}

	public void play() throws IOException, GeneralSecurityException {
		File in = getContent(setup.contentId);
		byte[] aeskey = null;
		PublicKey publicKey = null;
		PrivateKey privateKey = null;
		agent.sharePublicKey(aeskey, publicKey, privateKey);
		byte[] license = agent.getLicense(setup.contentId, publicKey);
		byte[] lic = agent.decryptLicenseKey(license, privateKey);
		File out = new File(setup.clearClientFile);
		agent.decryptContent(in, out, lic);
		testffPlay(setup.clearClientFile);
	}

	private void testffPlay(String inputFile) {
		System.out.println("Testing the newly encoded video by playing it using ffplay");
		ProcessBuilder pb = new ProcessBuilder("cmd.exe","/c","start","D:\\Dropbox\\github\\video-engineering\\ffmpeg\\bin\\ffplay.exe","-i", inputFile);
		ExceptionFreeProcess.process(pb);
	}
}
