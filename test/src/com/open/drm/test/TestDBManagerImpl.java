package com.open.drm.test;

import java.util.HashMap;
import java.util.Map;

import com.open.drm.utils.DBManager;
import com.open.drm.utils.model.DBElement;


public class TestDBManagerImpl implements DBManager{

	// for testing
	Map<String, Object> inmemoryDB = null;

	public TestDBManagerImpl() {
			inmemoryDB = new HashMap<String, Object>();
	}

	@Override
	public void add(DBElement element) {
		inmemoryDB.put(element.getKey(), element.get());
	}

	@Override
	public void update(DBElement element) {
		inmemoryDB.put(element.getKey(), element.get());
	}

	@Override
	public void delete(DBElement element) {
		inmemoryDB.remove(element.getKey());
	}

	@Override
	public boolean test() {
		return true;
	}

	@Override
	public DBElement get(DBElement element) {
		return (DBElement) inmemoryDB.get(element.getKey());
	}
	
}
