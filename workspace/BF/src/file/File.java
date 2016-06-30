package file;

/**
 * 文件类，代表某个固定版本的文件
 */
public class File {
	private String version;
	private String code;
	
	/**
	 * File构造函数，传入文件某一版本的字符串
	 * @param s 字符串
	 */
	public File(String s) {
		String[] s1 = s.split(" ");
		version = s1[0];
		code = s1[1];
	}
	
	/**
	 * 获取文件版本
	 * @return 文件版本
	 */
	public String getVersion(){
		return version;
	}
	
	/**
	 * 获取文件代码
	 * @return 文件代码
	 */
	public String getCode(){
		return code;
	}
}
