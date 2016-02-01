package com.open.drm.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import com.open.drm.endpoint.ClientEndPointImpl;
import com.open.drm.endpoint.EndPoint;

public class Player extends Application{
	
	private File media;
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
		String[] args = null;
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Media");
	    Group root = new Group();
	    Media media = new Media("dummyURL");
	    MediaPlayer mediaPlayer = new MediaPlayer(media);
	    mediaPlayer.play();

	    MediaView mediaView = new MediaView(mediaPlayer);

	    root.getChildren().add(mediaView);
	    Scene scene = SceneBuilder.create().width(500).height(500).root(root)
	            .fill(Color.WHITE).build();
	    primaryStage.setScene(scene);
	    primaryStage.show();
	}
}
