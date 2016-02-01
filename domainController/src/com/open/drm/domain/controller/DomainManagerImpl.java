package com.open.drm.domain.controller;

import com.open.drm.utils.DBManager;
import com.open.drm.utils.model.SetUpObject;

public class DomainManagerImpl implements DomainManager{

	private DBManager dbManager;
	private DBManager registryManager;
	private SetUpObject setup;
	
	public DomainManagerImpl(DBManager dbManager, DBManager registryManager, SetUpObject setup) {
		this.dbManager = dbManager;
		this.registryManager = registryManager;
		this.setup = setup;
	}

	public void registration(String uniqueIdentifier) {
		
	}

	public void license(String uniqueIdentifier) {
		
	}

	public String encryptKey(String publicKey) {
		return null;
	}

	public void checkDomain() {
		
	}
	
	public void customValidation() {
		
	}
	
}
