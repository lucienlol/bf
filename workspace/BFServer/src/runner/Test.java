package runner;

import java.io.File;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import service.ExecuteService;
import service.IOService;
import service.UserService;
import serviceImpl.ExecuteServiceImpl;
import serviceImpl.IOServiceImpl;
import serviceImpl.UserServiceImpl;

public class Test {
	public static void main(String[] args) throws RemoteException {
		IOService ioService = new IOServiceImpl();
		System.out.println(ioService.readFile("admin1", "file1"));
	}
}
