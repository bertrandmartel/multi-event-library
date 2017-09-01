/**
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2015 Bertrand Martel
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package fr.bmartel.android.multievent;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.PowerManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.bmartel.android.multievent.listener.IConnectivityListener;
import fr.bmartel.android.multievent.listener.IPhoneCallListener;
import fr.bmartel.android.multievent.listener.IScreenStateListener;
import fr.bmartel.android.multievent.listener.ISmsListener;
import fr.bmartel.android.multievent.listener.IVolumeListener;

/**
 * @author Bertrand Martel
 */
public class MultiEvent {

    private String TAG = MultiEvent.class.getName();

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

    List<IConnectivityListener> connectivityListenerList = new ArrayList<>();

    private Context context = null;

    private SettingsObserver audioObserver = null;
    private EventReceiver eventReceiver = null;
    private AudioManager audioManager = null;
    private PowerManager powerManager = null;
    private ConnectivityReceiver connectivityReceiver = null;

    public MultiEvent(Context context) {

        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (audioManager == null)
            Log.e(TAG, "Error audio manager is null");

        powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (powerManager == null)
            Log.e(TAG, "Error power manager is null");

        if (context != null) {
            this.context = context;

            //set content observer
            audioObserver = new SettingsObserver(context, new Handler(), this);

            //register content observer
            context.getContentResolver().registerContentObserver(android.provider.Settings.System.CONTENT_URI, true, audioObserver);

            if (checkReadPhoneStatePermission(context, "android.permission.READ_PHONE_STATE")) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (telephonyManager != null) {
                    PhoneListener PhoneListener = new PhoneListener(this);
                    telephonyManager.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
                } else {
                    Log.e(TAG, "Telephony manager service is not available");
                }
            } else {
                Log.w(TAG, "permission android.permission.READ_PHONE_STATE has not been granted. TELEPHONY_SERVICE is not summonned");
            }

            eventReceiver = new EventReceiver(this);

            IntentFilter screenStatusFilter = new IntentFilter();
            screenStatusFilter.addAction(Intent.ACTION_SCREEN_ON);
            screenStatusFilter.addAction(Intent.ACTION_SCREEN_OFF);
            screenStatusFilter.addAction("android.provider.Telephony.SMS_RECEIVED");

            context.registerReceiver(eventReceiver, screenStatusFilter);

            connectivityReceiver = new ConnectivityReceiver(context, this);
            IntentFilter connectivityFilter = new IntentFilter();
            connectivityFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

            context.registerReceiver(connectivityReceiver, connectivityFilter);

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

    public void addConnectivityChangeListener(IConnectivityListener listener) {
        connectivityListenerList.add(listener);
    }

    /**
     * Check if a specific permission has been granted
     *
     * @param context    android context
     * @param permission permission name
     * @return
     */
    private boolean checkReadPhoneStatePermission(Context context, String permission) {
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
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
        connectivityListenerList.remove(listener);
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
        connectivityListenerList.clear();
    }

    public void close() {
        removeAllListeners();
        context.getContentResolver().unregisterContentObserver(audioObserver);
        context.unregisterReceiver(eventReceiver);
        context.unregisterReceiver(connectivityReceiver);
    }

    public List<IVolumeListener> getVolumeMediaListenerList() {
        return volumeMediaListenerList;
    }

    public List<IConnectivityListener> getConnectivityListenerList() {
        return connectivityListenerList;
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

    public AudioManager getAudioManager() {
        return audioManager;
    }

    public int getVolume(int type) {
        if (audioManager != null)
            return audioObserver.getVolume(type);
        else
            return 0;
    }

    public void mute(int type, boolean state) {
        if (audioManager != null)
            audioObserver.mute(type, state);
    }

    public void volumeUp(int type) {
        if (audioManager != null)
            audioObserver.volumeUp(type);
    }

    public void volumeDown(int type) {
        if (audioManager != null)
            audioObserver.volumeDown(type);
    }

    public void setVolume(int type, int value) {
        if (audioManager != null)
            audioObserver.setVolume(type, value);
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR_MR1)
    public boolean getScreenState() {
        if (powerManager != null && (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1)) {
            return powerManager.isScreenOn();
        } else
            return false;
    }

    public NetworkInfo.State getWifiState() {
        return connectivityReceiver.getWifiState();
    }

    public NetworkInfo.State getEthernetState() {
        return connectivityReceiver.getEthernetState();
    }
}
