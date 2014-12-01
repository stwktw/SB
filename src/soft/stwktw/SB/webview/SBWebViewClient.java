/**
 * (c)Copyright 2011-2012, AhnLab. All rights reserved
 *
 * FileName   : SBWebViewClient.java
 * Project    : SB
 * Date       : 2012. 7. 10. 
 * Written by : stwktw(Taewoong Kim)
 */
package soft.stwktw.SB.webview;

import java.io.IOException;

import soft.stwktw.SB.filter.SBURLFilter;
import soft.stwktw.SB.listener.SBClientListener;
import android.content.Context;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SBWebViewClient extends WebViewClient {
	
	private Context mContext;
	
	public SBWebViewClient(Context context) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
	}

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		// TODO Auto-generated method stub
		
		SBURLFilter msgburlfilter = new SBURLFilter(mContext);
		try {
			msgburlfilter.filter(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.shouldOverrideUrlLoading(view, url);
	}

	@Override
	public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
		// TODO Auto-generated method stub
		return super.shouldInterceptRequest(view, url);
	}

	@Override
	public void onPageFinished(WebView view, String url) {
		// TODO Auto-generated method stub
		super.onPageFinished(view, url);
		SBClientListener  msgbWebViewClientListener = (SBClientListener) this.mContext;
		msgbWebViewClientListener.OnPageFinished(view, url);
	}

	@Override
	public void onLoadResource(WebView view, String url) {
		// TODO Auto-generated method stub
		super.onLoadResource(view, url);
	}
	
}
