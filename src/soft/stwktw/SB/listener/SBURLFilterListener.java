/**
 * (c)Copyright 2011-2012, AhnLab. All rights reserved
 *
 * FileName   : SBURLFilterListener.java
 * Project    : SB
 * Date       : 2012. 7. 10. 
 * Written by : stwktw(Taewoong Kim)
 */
package soft.stwktw.SB.listener;

public interface SBURLFilterListener {
	public void OnURLFiltered(String url);
	public void OnLog(String url);
	public void OnUrlChanged(String url);
}
