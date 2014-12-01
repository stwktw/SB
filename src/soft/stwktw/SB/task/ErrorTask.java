package soft.stwktw.SB.task;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.http.HttpResponse;

import soft.stwktw.SB.listener.SBErrorTaskListener;
import soft.stwktw.SB.log.SBLog;
import android.content.Context;
import android.os.AsyncTask;

public class ErrorTask extends AsyncTask<String, Void, HttpResponse> {

	private static final String TAG = ErrorTask.class.getName();
	private Context mContext;
	
    public ErrorTask(Context context) {
		// TODO Auto-generated constructor stub
    	this.mContext = context;
	}
	
	@Override
	protected HttpResponse doInBackground(String... param) {
		// TODO Auto-generated method stub
		String getURL = null;
		String port = null;
		 
		if(param != null && param.length == 2){
			getURL = param[0];
			port = param[1];
		}else {
			SBLog.d(TAG, "no param");
		}
		
		Socket socket;
		try {
			SBLog.d(TAG, "ErrorTask address:" + getURL + ", port:" + port);
			socket = new Socket(getURL, Integer.parseInt(port));
			socket.close();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			SBLog.e(TAG, e.toString());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			SBLog.e(TAG, e.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			SBLog.e(TAG, e.toString());
		}
		
		
		return null;
		
		/*
				MSGLog.d(TAG, "errorDoInBackground(0)");
				String getURL = null;
				String number = null;
				 
				if(param != null && param.length == 2){
					getURL = param[0];
					number = param[1];
				}else {
					MSGLog.d(TAG, "no param");
				}
				
				HttpClient client = new DefaultHttpClient();  
				
				HttpGet get = new HttpGet(getURL + "?number=" + number + "&state=fail");
			    HttpResponse responseGet = null;
			    try {
					responseGet = client.execute(get);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					MSGLog.e(TAG, e.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					MSGLog.e(TAG, e.toString());
				}
			    MSGLog.d(TAG, "errorDoInBackground(1)");
				return responseGet;
				*/
	}

	@Override
	protected void onPostExecute(HttpResponse result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		SBErrorTaskListener listener = (SBErrorTaskListener) mContext;
		listener.onErrorTaskPostExecute(result);
	}

}
