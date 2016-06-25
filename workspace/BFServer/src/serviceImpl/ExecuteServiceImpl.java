//请不要修改本文件名
package serviceImpl;

import java.rmi.RemoteException;

import service.ExecuteService;
import service.UserService;

public class ExecuteServiceImpl implements ExecuteService {

	/**
	 * 请实现该方法
	 */
	@Override
	public String execute(String code, String param) throws RemoteException {
		String result = "";
		char[] res = new char[1000];//结果字符数组
		char[] cod = null;//代码字符串转化成字符数组
		char[] par = null;//参数字符串转化成字符数组
		int res_poi = 0;//结果字符数组指针
		int par_poi = 0;//参数字符数组指针
		if(code != null) {
			code = codeSplit(code);
			cod = code.toCharArray();
			if(param != null)
				par = param.toCharArray();
			try {
				for(int i = 0; i < cod.length; i++) {
					char c = cod[i];
					if(c == '>'){
						res_poi++;
					}
					else if(c == '<'){
						res_poi--;
					}
					else if(c == '+'){
						res[res_poi]++;
					}
					else if(c == '-'){
						res[res_poi]--;
					}
					else if(c == '.'){
						result = result + res[res_poi];
					}
					else if(c == ','){
						if(par_poi < par.length){
							res[res_poi] = par[par_poi];
							par_poi++;
						}
						else
							res[res_poi] = '\0';
					}
					else if(c == '['){
						if(((int)res[res_poi]) == 0){
							int count = 0;
							while(((cod[i] == ']')&&(count == -1))){
								i++;
								if(cod[i] == '['){
									count++;
								}
								else if(cod[i] == ']'){
									count--;
								}
							}
						}
					}
					else if(c == ']'){
						if(((int)res[res_poi]) != 0){
							int count = 0;
							while(!((cod[i] == '[')&&(count == 1))){
								i--;
								if(cod[i] == '['){
									count++;
								}
								else if(cod[i] == ']'){
									count--;
								}
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "error!";
			}
		}
		return result;
	}

	/**
	 * 过滤代码字符串
	 * @param s 输入代码
	 * @return 过滤后的代码
	 */
	public String codeSplit(String s) {
		s.replaceAll(" ", "");//过滤掉空字符
		s.replaceAll("\t", "");//过滤掉Tab字符
		s.replaceAll("\n", "");//过滤掉换行字符
		return s;
	}
}
