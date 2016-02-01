package com.open.drm.endpoint;

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
		
		// publish to CDN
		// add the publishing time to DB
		
	}

}
