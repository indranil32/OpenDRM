package com.open.drm.client;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

import com.open.drm.endpoint.ClientEndPointImpl;
import com.open.drm.endpoint.EndPoint;


public class Player {
	
	private ClientEndPointImpl endpoint;
	private DRMAgent agent;

	public Player(DRMAgent agent, EndPoint endpoint) {
		this.endpoint = (ClientEndPointImpl) endpoint;
		this.agent = agent;
	}

	private FileInputStream getContent(String content) {
		endpoint.getContent(content);
		return null;
	}

	public void play(String content) throws IOException, GeneralSecurityException {
		FileInputStream fis = getContent(content);
		agent.sharePublicKey();
		agent.getLicense();
		FileOutputStream out = agent.decryptPayload(fis);
	}
}
