package runner;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import rmi.RemoteHelper;
import service.IOService;
import ui.MainFrame;

public class ClientRunner {
	private RemoteHelper remoteHelper;
	
	public ClientRunner() {
		linkToServer();
		initGUI();
		test();
	}
	
	private void linkToServer() {
		try {
			remoteHelper = RemoteHelper.getInstance();
			remoteHelper.setRemote(Naming.lookup("rmi://localhost:8888/DataRemoteObject"));
			System.out.println("linked");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	private void initGUI() {
		new MainFrame();
	}
	
	public void test(){
		String user = "admin";
		IOService io = remoteHelper.getIOService();
		try {
			ArrayList<String> ar = io.readFileList(user);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args){
		new ClientRunner();
//		ClientRunner cr = new ClientRunner();
//		cr.test();
	}
}
