//需要客户端的Stub
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserService extends Remote{
	
	/**
	 * 用户登入
	 * @param username 用户名
	 * @param password 密码
	 * @return 是否登出
	 * @throws RemoteException
	 */
	public boolean login(String username, String password) throws RemoteException;

	/**
	 * 用户登出
	 * @param username 用户名
	 * @return 是否登出
	 * @throws RemoteException
	 */
	public boolean logout(String username) throws RemoteException;
}
