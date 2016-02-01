package com.open.drm.test;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.UUID;

import com.open.drm.client.DRMAgent;
import com.open.drm.client.Player;
import com.open.drm.domain.controller.ClientDomainManagerImpl;
import com.open.drm.domain.controller.DomainManager;
import com.open.drm.domain.controller.DomainManagerImpl;
import com.open.drm.endpoint.ClientEndPointImpl;
import com.open.drm.endpoint.EndPoint;
import com.open.drm.endpoint.EndPointImpl;
import com.open.drm.packager.Packager;
import com.open.drm.packager.PublicPrivateKeyGen;
import com.open.drm.utils.DBManager;
import com.open.drm.utils.model.SetUpObject;


public class Test {

	public static void main(String[] args) throws IOException,
			GeneralSecurityException {
		
		String content = "live.wmv";
		String source = "D:\\Dropbox\\github\\video-engineering\\resources\\live.wmv";
		String tmpFolder = "D:\\Dropbox\\github\\video-engineering\\tmp\\";
		UUID uniqueId = UUID.randomUUID(); // deviceId
		DBManager dbManager = new TestDBManagerImpl(); // in-memory DB for testing
		DBManager registryManager = new TestRegistryManagerImpl(); // in-memory domain registry
		PublicPrivateKeyGen keyGen = PublicPrivateKeyGen.getInstance();
		
		// UI setup object
		SetUpObject setup = new SetUpObject();
		
		// Start end point
		EndPoint endpoint = new EndPointImpl(dbManager, setup); 
		// Start domain controller
		DomainManager domain = new DomainManagerImpl(dbManager, registryManager, setup);
		
		// packaging
		Packager packager = new Packager(keyGen, tmpFolder, dbManager, endpoint, setup);
		packager.startPackaging(source);
		
		// Start DRM agent
		domain = new ClientDomainManagerImpl();
		DRMAgent agent = new DRMAgent(uniqueId.toString(),domain, setup);
		agent.register();
		agent.domainAuthorization();
		// initiate playback
		endpoint = new ClientEndPointImpl(dbManager, setup);
		Player player = new Player(agent, endpoint);
		player.play(content);
	}
}