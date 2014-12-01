package soft.stwktw.SB.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;

import soft.stwktw.SB.filemanager.SBFileManager;
import soft.stwktw.SB.listener.SBGetWebSourceTaskListener;
import soft.stwktw.SB.log.SBLog;
import android.content.Context;
import android.os.AsyncTask;

public class GetWebSourceTask extends AsyncTask<String, Void, HttpResponse>{
	
	private final static String TAG = GetWebSourceTask.class.getName();
	
	private Context mContext;
	
    public GetWebSourceTask(Context context) {
		// TODO Auto-generated constructor stub
    	this.mContext = context;
	}

	@Override
    protected HttpResponse doInBackground(String... param) {
         
    	try {
    		SBLog.d(TAG, "getWebDoInBackground(0)");
    		SBGetWebSourceTaskListener listener = (SBGetWebSourceTaskListener) mContext;
    		listener.alive();
    		
    		String getURL = null;
			String downloadFileName = null;
			
    		if(param != null && param.length == 2){
    			getURL = param[0];
    			downloadFileName = param[1];
    		}else {
    			SBLog.d(TAG, "no param");
    		}
    		
			HttpClient client = new DefaultHttpClient();  
			client.getParams().setParameter(CoreProtocolPNames.USER_AGENT, "Mozilla/5.0 (Linux; U; Android 4.0.4; ko-kr; SHV-E210S Build/IMM76D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
		    
			HttpGet get = new HttpGet(getURL);
		    HttpResponse responseGet;
		    responseGet = client.execute(get);
			HttpEntity resEntityGet = responseGet.getEntity();
			if (resEntityGet != null)
	        {  
				File file = null;
				
				if(downloadFileName != null){
//					String downloadFileNameEncode = Base64.encodeToString(downloadFileName.getBytes(), 0);
					file = new File(SBFileManager.getBaseDirectory(), downloadFileName);
				}else{
//					Header header[] = responseGet.getAllHeaders();
//					String fileName = null;
//					String arg0 = getURL;
//					String arg2 = null;
//					String arg3 = null;
//					for(int i = 0; i < header.length ; i++){	
//						if(header[i].getName().equals("Content-Disposition")){
//							arg2 = header[i].getValue();
//						}else if(header[i].getName().equals("Content-Type")){
//							arg3 = header[i].getValue();
//						}
//					}
//					
//					String temp1 = URLUtil.guessFileName(arg0, null, null);
//					String temp2 = URLUtil.guessFileName(arg0, arg2, null);
//					String temp3 = URLUtil.guessFileName(arg0, arg2, arg3);
//					
//					MimeTypeMap mm = MimeTypeMap.getSingleton();
//					String mimeType = mm.getMimeTypeFromExtension("apk");
//					
//					if(mimeType.equals(mm.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(temp2)))){
//						fileName = temp2;
//					}else if(mimeType.equals(mm.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(temp3)))){
//						fileName = temp3;
//					}else if(mimeType.equals(mm.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(temp1)))){
//						fileName = temp1;
//					}else{
//						fileName = "websource.txt";
//					}
					file = new File(SBFileManager.getBaseDirectory(), "websource.txt");
				}
//				MSGBOutputStream msgbfos = new MSGBOutputStream(file);
//				resEntityGet.writeTo(msgbfos);
//				msgbfos.close();
				FileOutputStream fos = new FileOutputStream(file);
				resEntityGet.writeTo(fos);
				fos.close();
				checkDownloadFile(file);
				SBLog.d(TAG, "getWebDoInBackground(1)");
				return responseGet;
		    }
				
		} catch (Exception e) {
			e.printStackTrace();
			SBLog.e(TAG, e.toString());
		}
         
        return null;
    }

	@Override
	protected void onPostExecute(HttpResponse result) {
		super.onPostExecute(result);  
			SBGetWebSourceTaskListener listener = (SBGetWebSourceTaskListener) mContext;
			listener.onGetWebSourceTaskPostExecute(result);
	}

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
		SBGetWebSourceTaskListener listener = (SBGetWebSourceTaskListener) mContext;
		listener.onGetWebSourceTaskCancelled();
	}
	
	private void checkDownloadFile(File file) {
		// TODO Auto-generated method stub
		
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			byte[] buffer = new byte[2];
			fis.read(buffer, 0, 2);
			fis.close();
			SBLog.d(TAG, "[" + buffer[0] + "]" + "[" + buffer[1] + "]");
			if(buffer[0]==0x50 && buffer[1]==0x4b){
				String ext = ".apk";
				SBLog.d(TAG, file.getAbsolutePath().toLowerCase(Locale.ENGLISH));
				if(file.getAbsolutePath().toLowerCase(Locale.ENGLISH).endsWith(ext) == false){
					SBLog.d(TAG, "change filename txt->apk");
					file.renameTo(new File(SBFileManager.getBaseDirectory(), "websource.apk"));
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
