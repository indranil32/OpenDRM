package com.lightweight.drm.packager.model;

import com.lightweight.drm.utils.DBElement;

public class PackagingDetails implements DBElement{
	
	public String key;
	public String encryptedFile;
	public String sourceFile;
	public String algo;
	public String license;
	public String createdDate;
	public String updatedDate;
	public String publisingURL;
	public String domainControllerURL;
	
	@Override
	public String getKey() {
		return key;
	}
	@Override
	public DBElement get() {
		// TODO Auto-generated method stub
		return this;
	}
	@Override
	public void set(DBElement element) {
		//
	}
}
