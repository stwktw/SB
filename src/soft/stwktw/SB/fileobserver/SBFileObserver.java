package soft.stwktw.SB.fileobserver;

import soft.stwktw.SB.listener.SBFileObserverListener;
import android.content.Context;
import android.os.FileObserver;

public class SBFileObserver extends FileObserver {
	
	private static SBFileObserver sbFileObserver;
	private Context context;

	private SBFileObserver(Context context, String path) {
		// TODO Auto-generated constructor stub		
		super(path);
		this.context = context;
	}
	
	public static synchronized SBFileObserver getInstance(Context context, String path){
		if(sbFileObserver == null){
			sbFileObserver = new SBFileObserver(context, path);
		}
		return sbFileObserver;
	}

	@Override
	public void onEvent(int event, String path) {
		// TODO Auto-generated method stub
		SBFileObserverListener sbFileObserverListener = (SBFileObserverListener) this.context;
		sbFileObserverListener.OnFileStateChanged(event, path);
	}
}
