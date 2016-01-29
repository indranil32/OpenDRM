package com.lightweight.drm.utils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import com.lightweight.drm.client.DRMAgent;
import com.lightweight.drm.domain.controller.DomainManager;
import com.lightweight.drm.packager.Packager;
import com.lightweight.drm.packager.PublicPrivateKeyGen;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Test {

	public static void main(String[] args) throws IOException,
			GeneralSecurityException {
		
		String domainControllerURL = httpServer();
		String source = "D:\\Dropbox\\github\\video-engineering\\resources\\live.wmv";
		String tmpFolder = "D:\\Dropbox\\github\\video-engineering\\tmp\\";
		String endPointURL = "http://end-point-url//dummy";
		String jdbcConnectionURL = "in-memory";
		
		// server
		PublicPrivateKeyGen keyGen = PublicPrivateKeyGen.getInstance();
		DBManager dbManager = new DBManagerImpl(jdbcConnectionURL);
		DomainManager domain = new DomainManager(domainControllerURL, dbManager);
		EndPoint cdn = new EndPoint(endPointURL, dbManager);
		Packager packager = new Packager(keyGen, tmpFolder, dbManager, domain, cdn);
		packager.startPackage(source);
		
		
		// Client on startup
		UUID uniqueId = UUID.randomUUID();
		DRMAgent agent = new DRMAgent(uniqueId.toString(), domainControllerURL);
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

	private static void feedPlayer(File chunk) { // decrypted for testing
		Player.chunk = chunk;
		Player.launch();
	}

	private static String httpServer() throws IOException {
		StringBuffer url = new StringBuffer("http://localhost/domain-controller/");
		HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
		server.createContext("/domain-controller/", new CustomHTTPHandler());
		server.setExecutor(null); // creates a default executor
		server.start();
		return url.toString();
	}

}

class Player extends Application {

	public static File chunk = null;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		final Media m = new Media(chunk.toURI().toString());
		final MediaPlayer mp = new MediaPlayer(m);
		final MediaView mv = new MediaView(mp);

		final DoubleProperty width = mv.fitWidthProperty();
		final DoubleProperty height = mv.fitHeightProperty();

		width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
		height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));

		mv.setPreserveRatio(true);

		StackPane root = new StackPane();
		root.getChildren().add(mv);

		final Scene scene = new Scene(root, 960, 540);
		scene.setFill(Color.BLACK);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Full Screen Video Player");
		primaryStage.setFullScreen(true);
		primaryStage.show();

		mp.play();
	}

}


class CustomHTTPHandler implements HttpHandler {

	private String response;

	@Override
	public void handle(HttpExchange paramHttpExchange) throws IOException {
		paramHttpExchange.sendResponseHeaders(200, getResponse().length());
		OutputStream os = paramHttpExchange.getResponseBody();
		os.write(getResponse().getBytes());
		os.close();
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
}
