package com.lightweight.drm.utils;

public interface DBManager {
	public void add(DBElement element);
	public void update(DBElement element);
	public void delete(DBElement element);
	public boolean test();
}
