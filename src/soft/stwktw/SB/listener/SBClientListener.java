/**
 * (c)Copyright 2011-2012, AhnLab. All rights reserved
 *
 * FileName   : SBWebChromeClientListener.java
 * Project    : SB
 * Date       : 2012. 7. 12. 
 * Written by : stwktw (Taewoong Kim)
 */
package soft.stwktw.SB.listener;

import android.webkit.WebView;

public interface SBClientListener {
	public void OnPageFinished(WebView view, String url);
}
