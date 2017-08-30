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

import android.app.Activity;
import android.media.AudioManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import fr.bmartel.android.multievent.listener.IConnectivityListener;
import fr.bmartel.android.multievent.listener.IPhoneCallListener;
import fr.bmartel.android.multievent.listener.IScreenStateListener;
import fr.bmartel.android.multievent.listener.ISmsListener;
import fr.bmartel.android.multievent.listener.IVolumeListener;
import fr.bmartel.phone_event_app.R;


public class DroidEventActivity extends Activity {

    private MultiEvent droidEvent = null;

    private String TAG = DroidEventActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_droid_event);

        droidEvent = new MultiEvent(this);

        droidEvent.addSystemVolumeListener(new IVolumeListener() {
            @Override
            public void onVolume(byte oldVolume, byte newVolume) {
                Toast.makeText(DroidEventActivity.this, "[VOLUME SYSTEM] old volume : " + oldVolume + " | " + "new volume : " + newVolume, Toast.LENGTH_LONG).show();
            }
        });

        droidEvent.addMediaVolumeListener(new IVolumeListener() {
            @Override
            public void onVolume(byte oldVolume, byte newVolume) {
                Toast.makeText(DroidEventActivity.this, "[VOLUME MEDIA] old volume : " + oldVolume + " | " + "new volume : " + newVolume, Toast.LENGTH_LONG).show();
            }
        });

        droidEvent.addDtmfVolumeListener(new IVolumeListener() {
            @Override
            public void onVolume(byte oldVolume, byte newVolume) {
                Toast.makeText(DroidEventActivity.this, "[VOLUME DTMF] old volume : " + oldVolume + " | " + "new volume : " + newVolume, Toast.LENGTH_LONG).show();
            }
        });
        droidEvent.addNotificationVolumeListener(new IVolumeListener() {
            @Override
            public void onVolume(byte oldVolume, byte newVolume) {
                Toast.makeText(DroidEventActivity.this, "[VOLUME NOTIF] old volume : " + oldVolume + " | " + "new volume : " + newVolume, Toast.LENGTH_LONG).show();
            }
        });
        droidEvent.addRingVolumeListener(new IVolumeListener() {
            @Override
            public void onVolume(byte oldVolume, byte newVolume) {
                Toast.makeText(DroidEventActivity.this, "[VOLUME RING] old volume : " + oldVolume + " | " + "new volume : " + newVolume, Toast.LENGTH_LONG).show();
            }
        });

        droidEvent.addVoiceCallVolumeListener(new IVolumeListener() {
            @Override
            public void onVolume(byte oldVolume, byte newVolume) {
                Toast.makeText(DroidEventActivity.this, "[VOLUME VOICE CALL] old volume : " + oldVolume + " | " + "new volume : " + newVolume, Toast.LENGTH_LONG).show();
            }
        });

        droidEvent.addScreenStateListener(new IScreenStateListener() {
            @Override
            public void onScreenOn() {
                Toast.makeText(DroidEventActivity.this, "[SCREEN ON]", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onScreenOff() {
                Toast.makeText(DroidEventActivity.this, "[SCREEN OFF]", Toast.LENGTH_LONG).show();
            }
        });
        droidEvent.addSmsListener(new ISmsListener() {
            @Override
            public void onSmsReceived() {
                Toast.makeText(DroidEventActivity.this, "[SMS RECEIVED]", Toast.LENGTH_LONG).show();
            }
        });
        droidEvent.addPhoneCallListener(new IPhoneCallListener() {
            @Override
            public void onIncomingCall(String phoneNumber) {
                Toast.makeText(DroidEventActivity.this, "[INCOMING CALL]", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onOffHook() {
                Toast.makeText(DroidEventActivity.this, "[OFF HOOK]", Toast.LENGTH_LONG).show();
            }
        });

        droidEvent.addConnectivityChangeListener(new IConnectivityListener() {

            @Override
            public void onWifiStateChange(NetworkInfo.State formerState, NetworkInfo.State newState) {
                Toast.makeText(DroidEventActivity.this, "[WIFI STATE CHANGE] from state " + formerState + " to " + newState, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onEthernetStateChange(NetworkInfo.State formerState, NetworkInfo.State newState) {
                Toast.makeText(DroidEventActivity.this, "[ETHERNET STATE CHANGE] from state " + formerState + " to " + newState, Toast.LENGTH_LONG).show();
            }
        });

        Log.i(TAG, "Current states");
        Log.i(TAG, "Media volume        : " + droidEvent.getVolume(AudioManager.STREAM_MUSIC));
        Log.i(TAG, "System volume       : " + droidEvent.getVolume(AudioManager.STREAM_SYSTEM));
        Log.i(TAG, "Ring volume         : " + droidEvent.getVolume(AudioManager.STREAM_RING));
        Log.i(TAG, "Notification volume : " + droidEvent.getVolume(AudioManager.STREAM_NOTIFICATION));
        Log.i(TAG, "DTMF volume         : " + droidEvent.getVolume(AudioManager.STREAM_DTMF));
        Log.i(TAG, "Voice call volume   : " + droidEvent.getVolume(AudioManager.STREAM_VOICE_CALL));
        Log.i(TAG, "Screen state        : " + droidEvent.getScreenState());
        Log.i(TAG, "Wifi state          : " + droidEvent.getWifiState());
        Log.i(TAG, "Ethernet state      : " + droidEvent.getEthernetState());

        Button buttonUp = (Button) findViewById(R.id.volume_up);
        buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                droidEvent.volumeUp(AudioManager.STREAM_MUSIC);
            }
        });

        Button buttonDown = (Button) findViewById(R.id.volume_down);
        buttonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                droidEvent.volumeDown(AudioManager.STREAM_MUSIC);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        droidEvent.close();
    }
}
