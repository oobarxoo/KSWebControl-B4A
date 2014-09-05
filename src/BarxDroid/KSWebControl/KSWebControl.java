package BarxDroid.KSWebControl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BA.Author;
import anywheresoftware.b4a.BA.Events;
import anywheresoftware.b4a.BA.ShortName;
import anywheresoftware.b4a.BA.Version;

//@DependsOn(values = {"android-support-v4"})
@Events(values = {"Response(Tag As String, Success as Boolean)"})
@ShortName("KSWebControl")
@Author("BarxDroid")
@Version(1.0F)
public class KSWebControl {
	private static final String FTP_START				= "ru.kslabs.ksweb.CMD.FTP_START";
	private static final String FTP_STOP				= "ru.kslabs.ksweb.CMD.FTP_STOP";
	
	private static final String LIGHTTPD_START			= "ru.kslabs.ksweb.CMD.LIGHTTPD_START";
	private static final String LIGHTTPD_STOP			= "ru.kslabs.ksweb.CMD.LIGHTTPD_STOP";
	
	private static final String NGINX_START				= "ru.kslabs.ksweb.CMD.NGINX_START";
	private static final String NGINX_STOP				= "ru.kslabs.ksweb.CMD.NGINX_STOP";
	
	private static final String MYSQL_START				= "ru.kslabs.ksweb.CMD.MYSQL_START";
	private static final String MYSQL_STOP				= "ru.kslabs.ksweb.CMD.MYSQL_STOP";
	
	private static final String KSWEB_CLOSE				= "ru.kslabs.ksweb.CMD.KSWEB_CLOSE";
	private static final String KSWEB_START				= "ru.kslabs.ksweb.CMD.KSWEB_START";
	private static final String KSWEB_FINISH_ACTIVITY	= "ru.kslabs.ksweb.CMD.KSWEB_FINISH_ACTIVITY";
	
	private static final String MYSQL_SET_CONFIG			= "ru.kslabs.ksweb.CMD.MYSQL_SET_CONFIG";
	private static final String PHP_SET_CONFIG			= "ru.kslabs.ksweb.CMD.PHP_SET_CONFIG";
	private static final String LIGHTTPD_SET_CONFIG		= "ru.kslabs.ksweb.CMD.LIGHTTPD_SET_CONFIG";
	private static final String NGINX_SET_CONFIG			= "ru.kslabs.ksweb.CMD.NGINX_SET_CONFIG";
	
	private static final String NGINX_ADD_HOST			= "ru.kslabs.ksweb.CMD.NGINX_ADD_HOST";
	private static final String NGINX_DELETE_HOST		= "ru.kslabs.ksweb.CMD.NGINX_DELETE_HOST";
	
	private static final String LIGHTTPD_ADD_HOST		= "ru.kslabs.ksweb.CMD.LIGHTTPD_ADD_HOST";
	private static final String LIGHTTPD_DELETE_HOST		= "ru.kslabs.ksweb.CMD.LIGHTPD_DELETE_HOST";
	
	private static final String RESPOND_OK				= "ru.kslabs.ksweb.CMD.RESPOND_OK";
	private static final String RESPOND_ERROR			= "ru.kslabs.ksweb.CMD.RESPOND_ERROR";
	
	private static final String TAG_KEY					= "TAG";
	private static final String DATA_KEY				= "DATA";
	//private static final String CMD_KEY				= "CMD";
	
	boolean ReceiverRegistered = false;
	IntentFilter filter = new IntentFilter();
	String mEventname;
	BA mBA;
	
	private BroadcastReceiver receiver = new BroadcastReceiver() {
		    @Override
		    public void onReceive(Context context, Intent intent) {
		    	BA.Log("Response Received");
		    	if (mBA.subExists(mEventname + "_response") ) {
		    		boolean success = false;
		    		if (intent.getAction() == RESPOND_OK) {
		    			success = true;
		    		}
		    		
		    		mBA.raiseEvent(mBA.context, mEventname + "_response", new Object[] {intent.getExtras().getString(TAG_KEY), success});
		    	}
			}
	    };	
	
	/**
	 * Initializes the object.
	 */
	public void Initialize(final BA ba, String Eventname) {
		mBA = ba;
		mEventname = Eventname.toLowerCase(BA.cul);
		filter.addAction(RESPOND_OK);
		filter.addAction(RESPOND_ERROR);
	}
	
	/**
	 * Registers the BroadcastReceiver.
	 * Used to get the response returned from KSWeb
	 * Be sure to call unRegister once done. i.e. in Activity_Pause
	 */
	public void Register() {
		ReceiverRegistered = true;
		BA.applicationContext.registerReceiver(receiver, filter);
		BA.Log("BroadcastReceiver registered");
	}
	
	/**
	 * Call to unregister the BroadcaseReceiver once you are done.
	 */
	public void Unregister() {
		ReceiverRegistered = false;
		BA.applicationContext.unregisterReceiver(receiver);
		BA.Log("BroadcastReceiver unregistered");
	}
	
	/**
	 * Returns whether the BroadcastReceiver is registered (true) or not (false)
	 */
	public boolean isRegistered() {
		return ReceiverRegistered;
	}
		
	public static void lighttpdDeleteHost(String tag, String hostname, String port, String rootDir) {
		Intent intent = new Intent();
		intent.setAction(LIGHTTPD_DELETE_HOST);
		intent.putExtra(TAG_KEY, tag);
		intent.putExtra(DATA_KEY, new String[] { hostname, port, rootDir });
		BA.applicationContext.sendBroadcast(intent);
	}
		
	public static void lighttpdAddHost(String tag, String hostname, String port, String rootDir) {
		Intent intent = new Intent();
		intent.setAction(LIGHTTPD_ADD_HOST);
		intent.putExtra(TAG_KEY, tag);
		intent.putExtra(DATA_KEY, new String[] { hostname, port, rootDir });
		BA.applicationContext.sendBroadcast(intent);
	}
		
	public static void nginxDeleteHost(String tag, String hostname, String port, String rootDir) {
		Intent intent = new Intent();
		intent.setAction(NGINX_DELETE_HOST);
		intent.putExtra(TAG_KEY, tag);
		intent.putExtra(DATA_KEY, new String[] { hostname, port, rootDir });
		BA.applicationContext.sendBroadcast(intent);
	}
		
	public static void nginxAddHost(String tag, String hostname, String port, String rootDir) {
		Intent intent = new Intent();
		intent.setAction(NGINX_ADD_HOST);
		intent.putExtra(TAG_KEY, tag);
		intent.putExtra(DATA_KEY, new String[] { hostname, port, rootDir });
		BA.applicationContext.sendBroadcast(intent);
	}
		
	public static void nginxSetConfig(String tag, String configTxt) {
		Intent intent = new Intent();
		intent.setAction(NGINX_SET_CONFIG);
		intent.putExtra(TAG_KEY, tag);
		intent.putExtra(DATA_KEY, new String[] { configTxt });
		BA.applicationContext.sendBroadcast(intent);
	}
		
	public static void lighttpdSetConfig(String tag, String configTxt) {
		Intent intent = new Intent();
		intent.setAction(LIGHTTPD_SET_CONFIG);
		intent.putExtra(TAG_KEY, tag);
		intent.putExtra(DATA_KEY, new String[] { configTxt });
		BA.applicationContext.sendBroadcast(intent);
	}
		
	public static void phpSetConfig(String tag, String configTxt) {
		Intent intent = new Intent();
		intent.setAction(PHP_SET_CONFIG);
		intent.putExtra(TAG_KEY, tag);
		intent.putExtra(DATA_KEY, new String[] { configTxt });
		BA.applicationContext.sendBroadcast(intent);
	}
		
	public static void mysqlSetConfig(String tag, String configTxt) {
		Intent intent = new Intent();
		intent.setAction(MYSQL_SET_CONFIG);
		intent.putExtra(TAG_KEY, tag);
		intent.putExtra(DATA_KEY, new String[] { configTxt });
		BA.applicationContext.sendBroadcast(intent);
	}
		
	public static void kswebClose(String tag) {
		Intent intent = new Intent();
		intent.setAction(KSWEB_CLOSE);
		intent.putExtra(TAG_KEY, tag);
		BA.applicationContext.sendBroadcast(intent);
	}
		
	public static void kswebStart(String tag) {
		Intent intent = new Intent();
		intent.setAction(KSWEB_START);
		intent.putExtra(TAG_KEY, tag);
		BA.applicationContext.sendBroadcast(intent);
	}
	
	public static void kswebFinishActivity(String tag) {
		Intent intent = new Intent();
		intent.setAction(KSWEB_FINISH_ACTIVITY);
		intent.putExtra(TAG_KEY, tag);
		BA.applicationContext.sendBroadcast(intent);
	}
	
	public static void mysqlStop(String tag) {
		Intent intent = new Intent();
		intent.setAction(MYSQL_STOP);
		intent.putExtra(TAG_KEY, tag);
		BA.applicationContext.sendBroadcast(intent);
	}
	
	public static void mysqlStart(String tag) {
		Intent intent = new Intent();
		intent.setAction(MYSQL_START);
		intent.putExtra(TAG_KEY, tag);
		BA.applicationContext.sendBroadcast(intent);
	}
	
	public static void nginxStop(String tag) {
		Intent intent = new Intent();
		intent.setAction(NGINX_STOP);
		intent.putExtra(TAG_KEY, tag);
		BA.applicationContext.sendBroadcast(intent);
	}
	
	public static void nginxStart(String tag) {
		Intent intent = new Intent();
		intent.setAction(NGINX_START);
		intent.putExtra(TAG_KEY, tag);
		BA.applicationContext.sendBroadcast(intent);
	}
	
	public static void lighttpdStop(String tag) {
		Intent intent = new Intent();
		intent.setAction(LIGHTTPD_STOP);
		intent.putExtra(TAG_KEY, tag);
		BA.applicationContext.sendBroadcast(intent);
	}
	
	public static void lighttpdStart(String tag) {
		Intent intent = new Intent();
		intent.setAction(LIGHTTPD_START);
		intent.putExtra(TAG_KEY, tag);
		BA.applicationContext.sendBroadcast(intent);
	}
	
	public static void ftpStop(BA ba, String tag) {
		Intent intent = new Intent();
		intent.setAction(FTP_STOP);
		intent.putExtra(TAG_KEY, tag);
		BA.applicationContext.sendBroadcast(intent);
	}
	
	public static void ftpStart(BA ba, String tag) {
		Intent intent = new Intent();
		intent.setAction(FTP_START);
		intent.putExtra(TAG_KEY, tag);
		BA.applicationContext.sendBroadcast(intent);
	}
	
}
