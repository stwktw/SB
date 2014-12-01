package soft.stwktw.SB.filemanager;

import java.io.File;

import soft.stwktw.SB.log.SBLog;
import android.os.Environment;

public class SBFileManager {
	private final static String TAG = SBFileManager.class.getName(); 
	
	static public File getBaseDirectory(){
		File file = new File(Environment.getExternalStorageDirectory(), "SB");
		return file;
	}
	
	static public void makeDirectory() {
		// TODO Auto-generated method stub
		getBaseDirectory().mkdir();
	}

	static public void deleteDirectorty() {
		// TODO Auto-generated method stub
		File file = getBaseDirectory();
		if(file != null){
			if(file.isFile() == true){
				file.delete();
			}else if(file.isDirectory() == true){
				removeDIR(file);
			}
		}
	}
	
	static public void removeDIR(File file){
		File[] listFile = file.listFiles(); 
		try{
			if(listFile.length > 0){
				for(int i = 0 ; i < listFile.length ; i++){
					if(listFile[i].isFile()){
						listFile[i].delete(); 
					}else{
						removeDIR(listFile[i]);
					}
					listFile[i].delete();
				}
			}else {
				file.delete();
			}
		}catch(Exception e){
			SBLog.e(TAG, e.toString());
		}	
	}
}
