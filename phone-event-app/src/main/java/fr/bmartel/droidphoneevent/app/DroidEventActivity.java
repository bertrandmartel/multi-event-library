package fr.bmartel.droidphoneevent.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import fr.bmartel.droidphoneevent.DroidEvent;
import fr.bmartel.droidphoneevent.listener.IPhoneCallListener;
import fr.bmartel.droidphoneevent.listener.IScreenStateListener;
import fr.bmartel.droidphoneevent.listener.ISmsListener;
import fr.bmartel.droidphoneevent.listener.IVolumeListener;


public class DroidEventActivity extends Activity {

    private DroidEvent droidEvent = null;

    private String TAG = DroidEventActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        droidEvent = new DroidEvent(this);

        droidEvent.addSystemVolumeListener(new IVolumeListener() {
            @Override
            public void onVolume(byte oldVolume, byte newVolume) {
                Log.i(TAG, "[VOLUME SYSTEM] old volume : " + oldVolume + " | " + "new volume : " + newVolume);
            }
        });

        droidEvent.addMediaVolumeListener(new IVolumeListener() {
            @Override
            public void onVolume(byte oldVolume, byte newVolume) {
                Log.i(TAG, "[VOLUME MEDIA] old volume : " + oldVolume + " | " + "new volume : " + newVolume);
            }
        });

        droidEvent.addDtmfVolumeListener(new IVolumeListener() {
            @Override
            public void onVolume(byte oldVolume, byte newVolume) {
                Log.i(TAG, "[VOLUME DTMF] old volume : " + oldVolume + " | " + "new volume : " + newVolume);
            }
        });
        droidEvent.addNotificationVolumeListener(new IVolumeListener() {
            @Override
            public void onVolume(byte oldVolume, byte newVolume) {
                Log.i(TAG, "[VOLUME NOTIF] old volume : " + oldVolume + " | " + "new volume : " + newVolume);
            }
        });
        droidEvent.addRingVolumeListener(new IVolumeListener() {
            @Override
            public void onVolume(byte oldVolume, byte newVolume) {
                Log.i(TAG, "[VOLUME RING] old volume : " + oldVolume + " | " + "new volume : " + newVolume);
            }
        });

        droidEvent.addVoiceCallVolumeListener(new IVolumeListener() {
            @Override
            public void onVolume(byte oldVolume, byte newVolume) {
                Log.i(TAG, "[VOLUME VOICE CALL] old volume : " + oldVolume + " | " + "new volume : " + newVolume);
            }
        });

        droidEvent.addScreenStateListener(new IScreenStateListener() {
            @Override
            public void onScreenOn() {
                Log.i(TAG, "[SCREEN ON]");
            }

            @Override
            public void onScreenOff() {
                Log.i(TAG, "[SCREEN OFF]");
            }
        });
        droidEvent.addSmsListener(new ISmsListener() {
            @Override
            public void onSmsReceived() {
                Log.i(TAG, "[SMS RECEIVED]");
            }
        });
        droidEvent.addPhoneCallListener(new IPhoneCallListener() {
            @Override
            public void onIncomingCall(String phoneNumber) {
                Log.i(TAG, "[INCOMING CALL]");
            }

            @Override
            public void onOffHook() {
                Log.i(TAG, "[OFF HOOK]");
            }
        });

        Log.i(TAG, "Current states");
        Log.i(TAG, "Media volume        : " + droidEvent.getMediaVolume());
        Log.i(TAG, "System volume       : " + droidEvent.getSystemVolume());
        Log.i(TAG, "Ring volume         : " + droidEvent.getRingVolume());
        Log.i(TAG, "Notification volume : " + droidEvent.getNotificationVolume());
        Log.i(TAG, "DTMF volume         : " + droidEvent.getDtmfVolume());
        Log.i(TAG, "Voice call volume   : " + droidEvent.getVoiceCallVolume());
        Log.i(TAG, "Screen state        : " + droidEvent.getScreenState());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
        droidEvent.close();
    }

}
