package com.open.drm.endpoint;

import java.io.File;
import java.util.Date;

import com.open.drm.packager.model.PackagingDetails;
import com.open.drm.utils.DBManager;
import com.open.drm.utils.model.SetUpObject;

public class EndPointImpl implements EndPoint{

	private DBManager dbManager; 
	private SetUpObject setup;
	
	public EndPointImpl(DBManager dbManager, SetUpObject setup) {
		this.dbManager = dbManager;
		this.setup = setup;
	}

	public void publish(PackagingDetails dbElement) {
		dbElement.publishingDate = new Date();
		// publish to CDN
		// add the publishing time to DB
		dbManager.update(dbElement);
	}

	public File getContent(String content) {
		PackagingDetails element = new PackagingDetails();
		element.id = content;
		element = (PackagingDetails) dbManager.get(element);
		return element.encryptedSourceContentFile;
	}

}
