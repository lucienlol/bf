package file;

import java.util.ArrayList;

public class FileList {
	private ArrayList<File> ar = new ArrayList<File>();
	
	public FileList(String s) {
		String[] s1 = s.split("#");
		for(int i = 0; i < s1.length; i++) {
			String line = s1[i];
			System.out.println("line:" + line);
			if(!(line.equals(""))) {
				File f = new File(line);
				ar.add(f);
			}
		}
	}

	public ArrayList<File> getArray() {
		return ar;
	}
	
	public String getLastVersion() {
		System.out.println(ar.size());
		File f = ar.get(ar.size() - 1);
		return f.getVersion();
	}
	
	public String getLastCode() {
		File f = ar.get(ar.size() - 1);
		return f.getCode();
	}
}
