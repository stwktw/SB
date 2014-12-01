package soft.stwktw.SB.listener;

import org.apache.http.HttpResponse;

public interface SBErrorTaskListener {
	public void onErrorTaskPostExecute(HttpResponse result);
}
