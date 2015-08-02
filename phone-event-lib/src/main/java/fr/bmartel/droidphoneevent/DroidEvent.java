package fr.bmartel.droidphoneevent;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.bmartel.droidphoneevent.listener.IPhoneCallListener;
import fr.bmartel.droidphoneevent.listener.IScreenStateListener;
import fr.bmartel.droidphoneevent.listener.ISmsListener;
import fr.bmartel.droidphoneevent.listener.IVolumeListener;

/**
 * @author Bertrand Martel
 */
public class DroidEvent {

    private String TAG = DroidEvent.class.getName();

    /**
     * list of volume listeners
     */
    List<IVolumeListener> volumeMediaListenerList = new ArrayList<>();
    List<IVolumeListener> volumeSystemListenerList = new ArrayList<>();
    List<IVolumeListener> volumeRingListenerList = new ArrayList<>();
    List<IVolumeListener> volumeNotificationListenerList = new ArrayList<>();
    List<IVolumeListener> volumeVoiceCallListenerList = new ArrayList<>();
    List<IVolumeListener> volumeDtmfListenerList = new ArrayList<>();

    List<IPhoneCallListener> phoneCallListenerList = new ArrayList<>();
    List<IScreenStateListener> screenStateListenerList = new ArrayList<>();
    List<ISmsListener> smsListenerList = new ArrayList<>();

    private Context context = null;

    private SettingsObserver audioObserver = null;
    private EventReceiver eventReceiver = null;

    public DroidEvent(Context context) {

        if (context != null) {
            this.context = context;

            //set content observer
            audioObserver = new SettingsObserver(context, new Handler(), this);

            //register content observer
            context.getContentResolver().registerContentObserver(android.provider.Settings.System.CONTENT_URI, true, audioObserver);

            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager != null) {
                PhoneListener PhoneListener = new PhoneListener(this);
                telephonyManager.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
            } else {
                Log.e(TAG, "Telephony manager service is not available");
            }

            eventReceiver = new EventReceiver(this);

            IntentFilter screenStatusFilter = new IntentFilter();
            screenStatusFilter.addAction(Intent.ACTION_SCREEN_ON);
            screenStatusFilter.addAction(Intent.ACTION_SCREEN_OFF);
            screenStatusFilter.addAction("android.provider.Telephony.SMS_RECEIVED");

            context.registerReceiver(eventReceiver, screenStatusFilter);

        } else {
            Log.e(TAG, "Error application context is null");
        }
    }

    public void addMediaVolumeListener(IVolumeListener listener) {
        volumeMediaListenerList.add(listener);
    }

    public void addVoiceCallVolumeListener(IVolumeListener listener) {
        volumeVoiceCallListenerList.add(listener);
    }

    public void addSystemVolumeListener(IVolumeListener listener) {
        volumeSystemListenerList.add(listener);
    }

    public void addRingVolumeListener(IVolumeListener listener) {
        volumeRingListenerList.add(listener);
    }

    public void addNotificationVolumeListener(IVolumeListener listener) {
        volumeNotificationListenerList.add(listener);
    }

    public void addDtmfVolumeListener(IVolumeListener listener) {
        volumeDtmfListenerList.add(listener);
    }

    public void addPhoneCallListener(IPhoneCallListener listener) {
        phoneCallListenerList.add(listener);
    }

    public void addScreenStateListener(IScreenStateListener listener) {
        screenStateListenerList.add(listener);
    }

    public void addSmsListener(ISmsListener listener) {
        smsListenerList.add(listener);
    }

    public void removeListeners(IVolumeListener listener) {
        volumeMediaListenerList.remove(listener);
        volumeVoiceCallListenerList.remove(listener);
        volumeSystemListenerList.remove(listener);
        volumeRingListenerList.remove(listener);
        volumeNotificationListenerList.remove(listener);
        volumeDtmfListenerList.remove(listener);

        phoneCallListenerList.remove(listener);
        screenStateListenerList.remove(listener);
        smsListenerList.remove(listener);
    }

    public void removeAllListeners() {
        volumeMediaListenerList.clear();
        volumeVoiceCallListenerList.clear();
        volumeSystemListenerList.clear();
        volumeRingListenerList.clear();
        volumeNotificationListenerList.clear();
        volumeDtmfListenerList.clear();

        phoneCallListenerList.clear();
        screenStateListenerList.clear();
        smsListenerList.clear();
    }

    public void close() {
        removeAllListeners();
        context.getContentResolver().unregisterContentObserver(audioObserver);
        context.unregisterReceiver(eventReceiver);
    }

    public List<IVolumeListener> getVolumeMediaListenerList() {
        return volumeMediaListenerList;
    }

    public List<IVolumeListener> getVolumeVoiceCalltListenerList() {
        return volumeVoiceCallListenerList;
    }

    public List<IVolumeListener> getVolumeSystemtListenerList() {
        return volumeSystemListenerList;
    }

    public List<IVolumeListener> getVolumeRingtListenerList() {
        return volumeRingListenerList;
    }

    public List<IVolumeListener> getVolumeNotificationtListenerList() {
        return volumeNotificationListenerList;
    }

    public List<IVolumeListener> getVolumeDtmftListenerList() {
        return volumeDtmfListenerList;
    }

    public List<IPhoneCallListener> getPhoneCallListenerList() {
        return phoneCallListenerList;
    }

    public List<IScreenStateListener> getScreenStateListenerList() {
        return screenStateListenerList;
    }

    public List<ISmsListener> getSmsListenerList() {
        return smsListenerList;
    }
}
