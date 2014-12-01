/**
 * (c)Copyright 2011-2012, AhnLab. All rights reserved
 *
 * FileName   : SBWebChromeClient.java
 * Project    : SB
 * Date       : 2012. 7. 12. 
 * Written by : stwktw (Taewoong Kim)
 */
package soft.stwktw.SB.webview;

import soft.stwktw.SB.listener.SBWebChromeClientListener;
import soft.stwktw.SB.log.SBLog;
import android.content.Context;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class SBWebChromeClient extends WebChromeClient {
	
	private static final String TAG = SBWebChromeClient.class.getName();
	
	private Context context;
	
	public SBWebChromeClient(Context context) {
		super();
		this.context = context;
	}

	@Override
	public void onProgressChanged(WebView view, int newProgress) {
		// TODO Auto-generated method stub
		super.onProgressChanged(view, newProgress);
		SBWebChromeClientListener listener = (SBWebChromeClientListener) this.context;
		listener.OnProgressChanged(view, newProgress);
		SBLog.d(TAG, "onProgressChanged: " + newProgress);
	}
	
}
