package org.springboot.demo.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

/**
 * 用于同步的一个服务
 * @author shijia
 *
 */
public class SynchronousService {
	public static void main(String[] args) throws Exception {
		String ip = "192.168.0.162";
		Integer port = 22;
		String name = "qinz";
		String password = "1";
		String remoteTargetDirectory = "/data/nfs-share/fastup/test";
		String imgUrl = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1080760116,732088640&fm=27&gp=0.jpg";
		SynchronousFile(ip, port, name, password, imgUrl, remoteTargetDirectory);
		System.out.println("成功");
	}
	
	/**
	 * 将网络上的文件同步到内网的某个服务器
	 * @param ip
	 * @param port
	 * @param name
	 * @param password
	 * @param imgUrl
	 * @param remoteTargetDirectory
	 * @throws MalformedURLException
	 * @throws ProtocolException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws Exception
	 */
	public static void SynchronousFile(String ip,Integer port,String name,String password,String imgUrl,String remoteTargetDirectory) throws MalformedURLException, ProtocolException, FileNotFoundException, IOException, Exception{
		File imageFile = FileService.getFileFromWeb(imgUrl);
		SSHService.uploadFile(ip, port, name, password, imageFile, remoteTargetDirectory, null);
		imageFile.delete();
	}
}
