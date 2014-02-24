package controller.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Invite {

	public Invite(){
		
	}
	
	public static boolean check(String s){
		File file = new File(GotyaConst.inviteCodePath);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = br.readLine()) != null) {
				if (s.equals(line)){
					br.close();
					return true;
				}
			}
			br.close();
		} catch(Exception e){
			e.printStackTrace();
			Log.printErr(e);
		}
		return false;
	}
}
