package com.open.drm.domain.controller.model;

import com.open.drm.utils.model.DBElement;

public class Domain implements DBElement{

	public String id;
	
	@Override
	public String getKey() {
		return id;
	}

	@Override
	public DBElement get() {
		return this;
	}

	@Override
	public void set(DBElement element) {
		
	}

}
