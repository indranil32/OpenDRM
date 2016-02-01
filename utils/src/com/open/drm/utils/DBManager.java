package com.open.drm.utils;

import com.open.drm.utils.model.DBElement;

public interface DBManager {
	public void add(DBElement element);
	public void update(DBElement element);
	public void delete(DBElement element);
	public DBElement get(DBElement element);
	public boolean test();
}
