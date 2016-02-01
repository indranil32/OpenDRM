package com.open.drm.endpoint;

import com.open.drm.utils.DBManager;
import com.open.drm.utils.model.SetUpObject;

public class ClientEndPointImpl implements EndPoint{
	
	private DBManager dbManager; 
	private SetUpObject setup;
	
	public ClientEndPointImpl(DBManager dbManager, SetUpObject setup) {
		this.dbManager = dbManager;
		this.setup = setup;
	}

	public void getContent(String content) {
		
	}

}
