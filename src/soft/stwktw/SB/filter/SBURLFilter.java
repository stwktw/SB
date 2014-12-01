/**
 * (c)Copyright 2011-2012, AhnLab. All rights reserved
 *
 * FileName   : SBURLFilter.java
 * Project    : SB
 * Date       : 2012. 7. 10. 
 * Written by : stwktw(Taewoong Kim)
 */
package soft.stwktw.SB.filter;

import java.io.IOException;

import soft.stwktw.SB.listener.SBURLFilterListener;
import android.content.Context;

public class SBURLFilter {
	
	private Context mContext;
	
	public SBURLFilter(Context context) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
	}

	public void filter(String url) throws IOException{
		SBURLFilterListener sbUrlFilterListener = (SBURLFilterListener) this.mContext;
		sbUrlFilterListener.OnUrlChanged(url);
	}

}
