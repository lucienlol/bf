package runner;

import java.rmi.RemoteException;
import java.util.Scanner;

import service.ExecuteService;
import service.IOService;
import service.UserService;
import serviceImpl.ExecuteServiceImpl;
import serviceImpl.IOServiceImpl;
import serviceImpl.UserServiceImpl;

public class Test {
	public static void main(String[] args) throws RemoteException {
		IOService ser = new IOServiceImpl();
		UserService user = new UserServiceImpl();
		if(user.login("admin1", "111111"))
			System.out.println("登陆成功");
	}

}
