package soft.stwktw.SB.listener;

import org.apache.http.HttpResponse;

public interface SBGetWebSourceTaskListener {
	public void onGetWebSourceTaskPostExecute(HttpResponse result);
	public void onGetWebSourceTaskError(HttpResponse result);
	public void onGetWebSourceTaskCancelled();
	public void alive();
}
