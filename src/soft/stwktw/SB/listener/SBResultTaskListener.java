package soft.stwktw.SB.listener;

import org.apache.http.HttpResponse;

public interface SBResultTaskListener {
	public void onResultTaskPostExecute(HttpResponse result);
}
