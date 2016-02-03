package com.open.drm.packager.model;

import java.io.File;
import java.util.Date;

import com.open.drm.utils.model.DBElement;

public class PackagingDetails implements DBElement{
	
	public String id;

	public File encryptedSourceContentFile;
	public File clearSourceContentFile;
	public String algo;
	public byte[] license;
	public byte[] key;
	public File licenseFile;
	public File keyFile;
	public Date createdDate;
	public Date publishingDate;
	public String publisingURL;
	public String domainControllerURL;
	
	@Override
	public String getKey() {
		return id;
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
