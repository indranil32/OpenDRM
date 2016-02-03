package com.open.drm.endpoint;

import java.io.File;
import java.io.FileNotFoundException;

public class ClientEndPointImpl implements EndPoint{
	
	private EndPoint endpoint;
	
	public ClientEndPointImpl() {

	}

	public ClientEndPointImpl(EndPoint endpoint) {
		this.endpoint = endpoint;
	}
	
	public File getContent(String content) throws FileNotFoundException {
		return ((EndPointImpl)this.endpoint).getContent(content);
	}

}
