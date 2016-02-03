package com.open.drm.domain.controller;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.PublicKey;


public class ClientDomainManagerImpl implements DomainManager{
	
	private DomainManager domain;
	
	public ClientDomainManagerImpl () {
		
	}
	
	public ClientDomainManagerImpl (DomainManager domain) {
		this.domain = domain;
	}
	
	public void register(String uniqueIdentifier) {
		((DomainManagerImpl)domain).registration(uniqueIdentifier);
	}

	public void authorize(String uniqueIdentifier) {
		((DomainManagerImpl)domain).checkDomain(uniqueIdentifier);
	}
	
	public byte[] getLicense(String content, PublicKey  publicKey) throws InvalidKeyException, IOException {
		return ((DomainManagerImpl)domain).license(content, publicKey);
	}
}
