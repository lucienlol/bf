package serviceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import service.UserService;

public class UserServiceImpl implements UserService{
	
	String user = "user";

	@Override
	public boolean login(String username, String password) throws RemoteException {
		File userfile = new File(user);
		String filetext = "";
		String text = null;
		String[] str1;
		String[] str2;
		Map map = new HashMap();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(userfile));
			while((text = br.readLine()) != null){
				filetext += text;
			}
			str1 = filetext.split("#");
			for(int i = 0; i < str1.length; i++) {
				if(!str1[i].equals("")) {
					str2 = str1[i].split(":");
					map.put(str2[0], str2[1]);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(map.containsKey(username)){
			if(map.get(username).equals(password))
				return true;
		}
		return false;
	}

	@Override
	public boolean logout(String username) throws RemoteException {
		return true;
	}

}
