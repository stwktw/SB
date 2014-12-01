package soft.stwktw.SB;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

import org.apache.http.HttpResponse;

import soft.stwktw.SB.filemanager.SBFileManager;
import soft.stwktw.SB.filter.SBURLFilter;
import soft.stwktw.SB.listener.SBGetWebSourceTaskListener;
import soft.stwktw.SB.listener.SBClientListener;
import soft.stwktw.SB.listener.SBErrorTaskListener;
import soft.stwktw.SB.listener.SBResultTaskListener;
import soft.stwktw.SB.listener.SBURLFilterListener;
import soft.stwktw.SB.listener.SBWebChromeClientListener;
import soft.stwktw.SB.log.SBLog;
import soft.stwktw.SB.task.ErrorTask;
import soft.stwktw.SB.task.GetWebSourceTask;
import soft.stwktw.SB.task.ResultTask;
import soft.stwktw.SB.webview.SBWebChromeClient;
import soft.stwktw.SB.webview.SBWebViewClient;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.Menu;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class SBActivity extends Activity implements SBURLFilterListener, SBClientListener, 
	SBWebChromeClientListener, DownloadListener, SBResultTaskListener,
	SBErrorTaskListener{

	private final static String TAG = SBActivity.class.getName();
	
	private WebView mWeb;
	private SBWebViewClient msgbWebViewClient;
	private SBWebChromeClient msgbWebChromeClient;
	
	private String mUrl;
	private String localhost;
//	private String number;
	private long port;
	private long timeoutvalue;
	private long timeoutcapture;
	private long limit;
	private long page;
	
	private StringBuffer mPages;
//	private int page = 0;
	private Context mContext;
	
	private String downloadFileName;
	
	private Handler handler;
//	private Handler timeOutHandler;
//	private Runnable timeoutRun;
//	private GetWebSourceTask getWebSourceTask;
	
//	private boolean continue_flag; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_msgb);
		this.mContext = this;
        
		SBFileManager.deleteDirectorty();
		SBFileManager.makeDirectory();
		
		Intent intent = getIntent();
		if(intent.getStringExtra("url") != null){
			String strUrl = intent.getStringExtra("url"); 
			String baseUrl = null;
			try{
				baseUrl = new String(Base64.decode(strUrl, Base64.URL_SAFE));
			}catch(Exception e){
				SBLog.e(TAG, e.toString());
				e.printStackTrace();
			}
			 
			if(baseUrl == null){
				finish();
				return;
			}
			this.mUrl = baseUrl;
			SBLog.d(TAG, "url: " + this.mUrl);
		}else {
			finish();
			return;
		}
		
		if(intent.getStringExtra("localhost") != null){
			this.localhost = intent.getStringExtra("localhost");
		}else {
			this.localhost = "10.0.2.2";
		}
		
		this.port = intent.getLongExtra("port", -1);
		this.timeoutvalue = intent.getLongExtra("timeout", 600000);
		this.timeoutcapture = intent.getLongExtra("timeoutcapture", 5000);
		this.limit = intent.getLongExtra("limit", 200);
		this.page = 0;
		
		SBLog.d(TAG, "localhost: " + this.localhost);
		SBLog.d(TAG, "port: " + this.port);
		SBLog.d(TAG, "limit: " + this.limit);
		SBLog.d(TAG, "timeout: " + this.timeoutvalue);
		
		mWeb = (WebView) findViewById(R.id.webview_1);
	    mWeb.getSettings().setJavaScriptEnabled(true);
	    msgbWebViewClient = new SBWebViewClient(this);
	    mWeb.setWebViewClient(msgbWebViewClient);
	    msgbWebChromeClient = new SBWebChromeClient(this);
	    mWeb.setWebChromeClient(msgbWebChromeClient);
	    mWeb.setDownloadListener(this);
	    
	    WebSettings webSettings = mWeb.getSettings();
		StringBuffer userAgent = new StringBuffer();
		userAgent.append("Mozilla/5.0 (Linux; U; Android 4.0.4; ko-kr; SHV-E210S Build/IMM76D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
		webSettings.setUserAgentString(userAgent.toString());
		loadUrl(this.mUrl);
	    
	    this.handler = new Handler();
	}
	
	private void loadUrl(String url){
		SBURLFilter msgburlfilter = new SBURLFilter(this);
		try {
			msgburlfilter.filter(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			SBLog.e(TAG, e.toString());
		}
		
		mWeb.loadUrl(url);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.msgb, menu);
		return true;
	}

	@Override
	public void OnURLFiltered(String url) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnLog(String url) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void OnUrlChanged(String url) {
		// TODO Auto-generated method stub
		SBLog.d(TAG, "OnUrlChanged:" + url);
		if(mPages == null){
			mPages = new StringBuffer();
		}
		mPages.append(url);
		mPages.append("\n");
		this.mUrl = url;
	}

	@Override
	public void OnPageFinished(WebView view, String url) {
		// TODO Auto-generated method stub
	    SBLog.d(TAG, "OnPageFinished:" + url);
	    handler.postDelayed(runGetWebSourceTaskPostExecute, 10000);
	}
	
	@Override
	public void OnProgressChanged(WebView view, int progress) {
		// TODO Auto-generated method stub
		SBLog.d(TAG, "OnProgressChanged: " + progress);
		handler.removeCallbacks(runGetWebSourceTaskPostExecute);
	}
	
	@Override
	public void onDownloadStart(String arg0, String arg1, String arg2,
			String arg3, long arg4) {
	}
	
	private void saveCapture(){
		Picture picture = mWeb.capturePicture();

		if(picture.getWidth() <= 0 || picture.getHeight() <= 0)
			return;

		int width = 2048;
		int height = 2048;
		
		if(picture.getWidth() < width){
			width = picture.getWidth();
		}
		
		if(picture.getHeight() < height){
			height = picture.getHeight();
		}
		
		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		picture.draw(canvas);
		
		String fileName = "capture.png";
		
		File file = new File(SBFileManager.getBaseDirectory(), fileName);
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			if(fos != null){
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
			} 
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			SBLog.e(TAG, e.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			SBLog.e(TAG, e.toString());
		}
		bitmap.recycle();
	}
	
	private void savePages(){
		String fileName = "redirectpages.txt";
		File file = new File(SBFileManager.getBaseDirectory(), fileName);
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			if(fos != null){
				fos.write(mPages.toString().getBytes());
			}
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			SBLog.e(TAG, e.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			SBLog.e(TAG, e.toString());
		}
	}

	private Runnable runGetWebSourceTaskPostExecute = new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(localhost != null && port != -1){
				SBLog.d(TAG, "ResultTask");
				ResultTask task = new ResultTask(mContext);
				task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, localhost, "" + port);
			}
		}
		
	};

	@Override
	public void onResultTaskPostExecute(HttpResponse result) {
		// TODO Auto-generated method stub
		((Activity) mContext).finish();
		SBLog.d(TAG, "ResultTaskPost");
		savePages();
		saveCapture();
		System.exit(0);
	}

	@Override
	public void onErrorTaskPostExecute(HttpResponse result) {
		// TODO Auto-generated method stub
		((Activity) mContext).finish();
		SBLog.d(TAG, "ErrorTaskPost");
		System.exit(0);
	}
}
