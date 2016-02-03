package com.open.drm.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

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
	
	public final static byte[] fileToByteArray(String pathStr) throws IOException {
		Path path = Paths.get(pathStr);
		byte[] data = Files.readAllBytes(path);
		return data;
	}
	
	public final static File byteArrayToFile(byte[] bytes, String pathStr) throws IOException {
		Path path = Paths.get(pathStr);
		path = Files.write(path, bytes, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
		return path.toFile();
	}
}
