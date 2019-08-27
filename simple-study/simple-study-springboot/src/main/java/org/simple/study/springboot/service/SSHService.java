package org.simple.study.springboot.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.SCPInputStream;
import ch.ethz.ssh2.SCPOutputStream;

/**
 * 远程操作linux系统
 * @author shijia
 *
 */
public class SSHService {
	/**
	 * 下载文件
	 * 
	 * @param ip
	 * @param port
	 * @param name
	 * @param password
	 * @param remoteFile
	 * @param remoteTargetDirectory
	 * @param newPath
	 */
	public static void download(String ip, Integer port, String name, String password, String remoteFile,
			String remoteTargetDirectory, String newPath) {
		Connection connection = new Connection(ip, port);

		try {
			connection.connect();
			boolean isAuthenticated = connection.authenticateWithPassword(name, password);
			if (isAuthenticated) {
				SCPClient scpClient = connection.createSCPClient();
				SCPInputStream sis = scpClient.get(remoteTargetDirectory + "/" + remoteFile);
				File f = new File(newPath);
				if (!f.exists()) {
					f.mkdirs();
				}
				File newFile = new File(newPath + remoteFile);
				FileOutputStream fos = new FileOutputStream(newFile);
				byte[] b = new byte[4096];
				int i;
				while ((i = sis.read(b)) != -1) {
					fos.write(b, 0, i);
				}
				fos.flush();
				fos.close();
				sis.close();
			} else {
				System.err.println("连接建立失败");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param ip
	 * @param port
	 * @param name
	 * @param password
	 * @param f
	 * @param remoteTargetDirectory
	 * @param mode                  默认为null,源码会自动设置成0600，表示owner的读写权限，无执行权限。如果需要权限，可以设置成0777
	 */
	public static void uploadFile(String ip, Integer port, String name, String password, File f,
			String remoteTargetDirectory, String mode) {
		Connection connection = new Connection(ip, port);

		try {
			connection.connect();
			boolean isAuthenticated = connection.authenticateWithPassword(name, password);
			if (!isAuthenticated) {
				System.out.println("连接建立失败");
				return;
			}
			SCPClient scpClient = new SCPClient(connection);
			SCPOutputStream os = scpClient.put(f.getName(), f.length(), remoteTargetDirectory, mode);
			byte[] b = new byte[4096];
			FileInputStream fis = new FileInputStream(f);
			int i;
			while ((i = fis.read(b)) != -1) {
				os.write(b, 0, i);
			}
			os.flush();
			fis.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}
	
}
