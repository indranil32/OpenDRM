package com.lightweight.drm.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class UtilityMethods {

	/**
	 * Copies a stream.
	 */
	public final static void copy(InputStream is, OutputStream os) throws IOException {
		int i;
		byte[] b = new byte[1024];
		while((i=is.read(b))!=-1) {
			os.write(b, 0, i);
		}
	}
	
}
