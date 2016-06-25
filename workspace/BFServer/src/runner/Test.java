package runner;

import java.rmi.RemoteException;
import java.util.Scanner;

import service.ExecuteService;
import serviceImpl.ExecuteServiceImpl;

public class Test {
	public static void main(String[] args) throws RemoteException {
		ExecuteService exe = new ExecuteServiceImpl();
		String code = null;
		String param = null;
		String result = null;
		Scanner sc = new Scanner(System.in);
		System.out.println("Input code here:");
		code = sc.nextLine();
		System.out.println("Input param here:");
		param = sc.nextLine();
		result = exe.execute(code, param);
		System.out.println(result);
	}

}
