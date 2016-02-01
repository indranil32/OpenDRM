package com.lightweight.drm.test;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.lightweight.drm.client.DRMAgent;
import com.lightweight.drm.domain.controller.DomainManager;
import com.lightweight.drm.endpoint.EndPoint;
import com.lightweight.drm.packager.Packager;
import com.lightweight.drm.packager.PublicPrivateKeyGen;
import com.lightweight.drm.utils.DBManager;

public class Test {

	public static void main(String[] args) throws IOException,
			GeneralSecurityException {
		
		String domainControllerURL = "http://localhost:5555/domain-controller/";
		String source = "D:\\Dropbox\\github\\video-engineering\\resources\\live.wmv";
		String tmpFolder = "D:\\Dropbox\\github\\video-engineering\\tmp\\";
		String endPointURL = "http://localhost:5555///endpoint";
		
		// server
		PublicPrivateKeyGen keyGen = PublicPrivateKeyGen.getInstance();
		DBManager dbManager = new TestDBManagerImpl();
		DomainManager domain = new DomainManager(domainControllerURL, dbManager);
		EndPoint cdn = new EndPoint(endPointURL, dbManager);
		Packager packager = new Packager(keyGen, tmpFolder, dbManager, domain, cdn);
		packager.startPackaging(source);
		
		
		// Client on startup
		UUID uniqueId = UUID.randomUUID();
		DRMAgent agent = new DRMAgent(uniqueId.toString(), domainControllerURL);
		agent.register();
		agent.domainAuthorization();
				
		// initiate playback
		String playlistURL = "";
		agent.sharePublicKey();
		// download playlist
		List<String> chunkURLs = downloadPlaylist(playlistURL);
		agent.getLicense();
		
		// download video chunks
		for (String chunkURL : chunkURLs) {
			File tmpChunklocation1 = downloadChunks(chunkURL);
			File tmpChunklocation2 = new File("tmp-location2");
			agent.decryptPayload(tmpChunklocation1, tmpChunklocation2);
			feedPlayer(tmpChunklocation2);
		}
	}

	private static List<String> downloadPlaylist(String playlistURL) {
		List<String> chunksURL = new ArrayList<String>();
		// download the master playlist and get all chunks info into list
		return chunksURL;
	}

	private static File downloadChunks(String chunkURL) {
		// download and convert into file
		return new File(chunkURL);
	}

	private static void feedPlayer(File chunk) { 
		// decrypted for testing
	}
}