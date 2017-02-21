package org.rapid.util.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;

import org.rapid.util.common.Callback;

public abstract class FileReader {
	
	/**
	 * 递归对文件夹及其子文件夹下的指定文件进行操作
	 * 
	 * @param path 指定的文件夹路径
	 * @param filter 文件过滤器
	 * @param callback 具体的对文件执行的逻辑
	 * @throws FileSystemException
	 */
	public static final void deepProcess(String path, FilenameFilter filter, Callback<File, Void> callback) throws Exception { 
		File file = ResourceUtil.getFile(path);
		if (!file.exists()) 
			throw new NoSuchFileException(path);
		if (!file.isDirectory()) 
			throw new NotDirectoryException(path);
		_scanning(file, filter, callback);
	}
	
	private static final void _scanning(File file, FilenameFilter filter, Callback<File, Void> callback) throws Exception { 
		File[] files = file.listFiles(filter);
		if (null != files) {
			for (File f : files) 
				callback.invoke(f);
		}
		files = file.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return file.isDirectory();
			}
		});
		if (null == files) 
			return;
		
		for (File f : files) 
			_scanning(f, filter, callback);
	}
	
	/**
	 * 适用于读取小型文件
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] bufferRead(File file) throws IOException { 
		BufferedInputStream in = null;
		byte[] buffer = null;
		try {
			in = new BufferedInputStream(new FileInputStream(file));
			buffer = new byte[in.available()];
			in.read(buffer);
		} finally {
			if (null != in)
				in.close();
		}
		return buffer;
	}
	
	/**
	 * 适用于读取小型文件，从 jar 文件读取文件
	 * 
	 * @param clazz
	 * @param name
	 * @return
	 * @throws IOException
	 */
	public static final byte[] bufferReadFromClassOrJar(Class<?> clazz, String name) throws IOException { 
		BufferedInputStream in = null;
		byte[] buffer = null;
		try {
			in = new BufferedInputStream(clazz.getResourceAsStream(name));
			buffer = new byte[in.available()];
			in.read(buffer);
		} finally {
			if (null != in)
				in.close();
		}
		return buffer;
	}
}
