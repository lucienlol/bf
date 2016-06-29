package serviceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import service.IOService;

public class IOServiceImpl implements IOService{
	
	@Override
	public boolean writeFile(String file, String userId, String fileName) {
		File f = new File(userId + "_" + fileName);
		try {
			FileWriter fw = new FileWriter(f, false);
			fw.write(file);
			fw.flush();
			fw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String readFile(String userId, String fileName) {
		File f = new File(userId + "_" + fileName);
		String file = "";
		String text = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			while((text = br.readLine()) != null) {
				file += text;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	@Override
	public ArrayList<String> readFileList(String userId) {
		ArrayList<String> nameList = new ArrayList<String>();
		try {
			File path = new File("./");
			File[] fileList = path.listFiles();
			for(int i = 0; i < fileList.length; i++) {
				File f = fileList[i];
				String name = f.getName();
				String[] s1 = name.split("_");
				if(s1[0].equals(userId))
					nameList.add(s1[0]);
			}			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return nameList;
	}
	
}
