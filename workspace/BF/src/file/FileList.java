package file;

import java.util.ArrayList;

/**
 * 文件列表类，包含同一文件名下所有版本的文件列表
 */
public class FileList {
	private ArrayList<File> fileList = new ArrayList<File>();
	
	/**
	 * FileList构造函数
	 * @pfileListam s 文件字符串
	 */
	public FileList(String s) {
		String[] files = s.split("#");
		for(int i = 0; i < files.length; i++) {
			String line = files[i];
			if(!(line.equals(""))) {
				File f = new File(line);
				fileList.add(f);
			}
		}
	}

	/**
	 * 获取最新版本
	 * @return 最新版本字符串
	 */
	public String getLastVersion() {
		File f = fileList.get(fileList.size() - 1);
		return f.getVersion();
	}
	
	/**
	 * 获取最新版本的代码
	 * @return 最新版本的代码字符串
	 */
	public String getLastCode() {
		File f = fileList.get(fileList.size() - 1);
		return f.getCode();
	}
	
	/**
	 * 获取文件的所有版本
	 * @return 所有版本的字符串列表
	 */
	public ArrayList<String> getVersions() {
		ArrayList<String> versions = new ArrayList<String>();
		for(int i = 0; i < fileList.size(); i++) {
			versions.add(fileList.get(i).getVersion());
		}
		return versions;
	}
	
	/**
	 * 获取某个版本的代码
	 * @pfileListam version 版本号
	 * @return 代码
	 */
	public String getCode(String version) {
		String code = "";
		for(int i = 0; i < fileList.size(); i++) {
			File f = fileList.get(i);
			if(version.equals(f.getVersion()))
				code = f.getCode();
		}
		return code;
	}
}
