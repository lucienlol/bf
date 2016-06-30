package file;

public class File {
	String version;
	String code;
	
	public File(String s) {
		String[] s1 = s.split(" ");
		version = s1[0];
		code = s1[1];
	}
	
	public String getVersion(){
		return version;
	}
	
	public String getCode(){
		return code;
	}
}
