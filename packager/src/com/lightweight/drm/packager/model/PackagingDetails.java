package com.lightweight.drm.packager.model;

import com.lightweight.drm.utils.DBElement;

public class PackagingDetails implements DBElement{
	
	String encryptedFile;
	String sourceFile;
	String algo;
	String license;
	String createdDate;
	String updatedDate;
	String publisingURL;
	String domainControllerURL;
}
