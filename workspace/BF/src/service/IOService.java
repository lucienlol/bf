//服务器IOService的Stub，内容相同
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
public interface IOService extends Remote{
	
	/**
	 * 将代码文件持久化
	 * @param file 代码文件
	 * @param userId 用户名
	 * @param fileName 文件名
	 * @return 是否写入成功
	 * @throws RemoteException
	 */
	public boolean writeFile(String file, String userId, String fileName)throws RemoteException;
	
	/**
	 * 读取代码文件（默认为最新版本）
	 * @param userId 用户名
	 * @param fileName 文件名
	 * @return 代码文件字符串
	 * @throws RemoteException
	 */
	public String readFile(String userId, String fileName)throws RemoteException;
	
	/**
	 * 读取用户文件列表
	 * @param userId 用户名
	 * @return 文件列表
	 * @throws RemoteException
	 */
	public ArrayList<String> readFileList(String userId)throws RemoteException;
}
