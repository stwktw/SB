package soft.stwktw.SB.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.util.Log;

@SuppressLint("SimpleDateFormat")
public class SBLog {
	
	public static void d(String tag, String msg) {
		Log.d(tag,date() + " " + msg);
	}
	
	public static void w(String tag, String msg) {
		Log.w(tag,date() + " " + msg);
	}
	
	public static void e(String tag, String msg) {
		Log.e(tag,date() + " " + msg);
	}
	
	public static void i(String tag, String msg) {
		Log.i(tag,date() + " " + msg);
	}
	
	public static void v(String tag, String msg) {
		Log.v(tag,date() + " " + msg);
	}
	
	private static String date(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd(HH:mm:ss)");
        TimeZone tz = TimeZone.getTimeZone("Asia/Seoul"); 
        sdf.setTimeZone(tz);
        String today = sdf.format(date);;
        return today;
	}
}
