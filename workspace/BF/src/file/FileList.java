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

	/**
	 * 获取最新版本
	 * @return 最新版本字符串
	 */
	public String getLastVersion() {
		System.out.println(ar.size());
		File f = ar.get(ar.size() - 1);
		return f.getVersion();
	}
	
	/**
	 * 获取最新版本的代码
	 * @return 最新版本的代码字符串
	 */
	public String getLastCode() {
		File f = ar.get(ar.size() - 1);
		return f.getCode();
	}
	
	/**
	 * 获取文件的所有版本
	 * @return 所有版本的字符串列表
	 */
	public ArrayList<String> getVersions() {
		ArrayList<String> versions = new ArrayList<String>();
		for(int i = 0; i < ar.size(); i++) {
			versions.add(ar.get(i).getVersion());
		}
		return versions;
	}
	
	/**
	 * 获取某个版本的代码
	 * @param version 版本号
	 * @return 代码
	 */
	public String getCode(String version) {
		String code = "";
		for(int i = 0; i < ar.size(); i++) {
			File f = ar.get(i);
			if(version.equals(f.getVersion()))
				code = f.getCode();
		}
		return code;
	}
}
