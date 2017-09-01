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

import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;

/**
 * @author Bertrand Martel
 */
public class SettingsObserver extends ContentObserver {

    /**
     * Application context
     */
    private Context context = null;

    /**
     * Previous selected volume for all audio type
     */
    private byte oldVolumeMedia = 0;
    private byte oldVolumeSystem = 0;
    private byte oldVolumeRing = 0;
    private byte oldVolumeNotification = 0;
    private byte oldVolumeDtmf = 0;
    private byte oldVolumeVoiceCall = 0;

    private byte maxVolumeMedia = 0;
    private byte maxVolumeSystem = 0;
    private byte maxVolumeRing = 0;
    private byte maxVolumeNotification = 0;
    private byte maxVolumeDtmf = 0;
    private byte maxVolumeVoiceCall = 0;

    private MultiEvent manager = null;

    /**
     * build AudioContentObserver
     *
     * @param context retrieve application context to look for Audio Manager service
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public SettingsObserver(Context context, Handler handler, MultiEvent manager) {
        super(handler);

        this.context = context;
        this.manager = manager;

        //retrieve Audio Manager service
        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (audio != null) {
            oldVolumeMedia = (byte) audio.getStreamVolume(AudioManager.STREAM_MUSIC);
            oldVolumeSystem = (byte) audio.getStreamVolume(AudioManager.STREAM_SYSTEM);
            oldVolumeRing = (byte) audio.getStreamVolume(AudioManager.STREAM_RING);
            oldVolumeNotification = (byte) audio.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
            oldVolumeDtmf = (byte) audio.getStreamVolume(AudioManager.STREAM_DTMF);
            oldVolumeVoiceCall = (byte) audio.getStreamVolume(AudioManager.STREAM_VOICE_CALL);

            maxVolumeMedia = (byte) audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            maxVolumeSystem = (byte) audio.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
            maxVolumeRing = (byte) audio.getStreamMaxVolume(AudioManager.STREAM_RING);
            maxVolumeNotification = (byte) audio.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);
            maxVolumeDtmf = (byte) audio.getStreamMaxVolume(AudioManager.STREAM_DTMF);
            maxVolumeVoiceCall = (byte) audio.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
        }
    }

    @Override
    public boolean deliverSelfNotifications() {
        return super.deliverSelfNotifications();
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);

        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        if (audio != null && manager != null) {
            byte newVolumeMedia = (byte) audio.getStreamVolume(AudioManager.STREAM_MUSIC);
            byte newVolumeSystem = (byte) audio.getStreamVolume(AudioManager.STREAM_SYSTEM);
            byte newVolumeRing = (byte) audio.getStreamVolume(AudioManager.STREAM_RING);
            byte newVolumeNotification = (byte) audio.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
            byte newVolumeDtmf = (byte) audio.getStreamVolume(AudioManager.STREAM_DTMF);
            byte newVolumeVoiceCall = (byte) audio.getStreamVolume(AudioManager.STREAM_VOICE_CALL);

            boolean changeVolumeMedia = ((oldVolumeMedia - newVolumeMedia) != 0) ? true : false;
            boolean changeVolumeSystem = ((oldVolumeSystem - newVolumeSystem) != 0) ? true : false;
            boolean changeVolumeRing = ((oldVolumeRing - newVolumeRing) != 0) ? true : false;
            boolean changeVolumeNotification = ((oldVolumeNotification - newVolumeNotification) != 0) ? true : false;
            boolean changeVolumeDtmf = ((oldVolumeDtmf - newVolumeDtmf) != 0) ? true : false;
            boolean changeVolumeVoiceCall = ((oldVolumeVoiceCall - newVolumeVoiceCall) != 0) ? true : false;

            if (changeVolumeMedia) {
                for (int i = 0; i < manager.getVolumeMediaListenerList().size(); i++) {
                    manager.getVolumeMediaListenerList().get(i).onVolume((byte) (oldVolumeMedia * 100 / maxVolumeMedia), (byte) (newVolumeMedia * 100 / maxVolumeMedia));
                }
            }
            if (changeVolumeSystem) {
                for (int i = 0; i < manager.getVolumeSystemtListenerList().size(); i++) {
                    manager.getVolumeSystemtListenerList().get(i).onVolume((byte) (oldVolumeSystem * 100 / maxVolumeSystem), (byte) (newVolumeSystem * 100 / maxVolumeSystem));
                }
            }
            if (changeVolumeRing) {
                for (int i = 0; i < manager.getVolumeRingtListenerList().size(); i++) {
                    manager.getVolumeRingtListenerList().get(i).onVolume((byte) (oldVolumeRing * 100 / maxVolumeRing), (byte) (newVolumeRing * 100 / maxVolumeRing));
                }
            }
            if (changeVolumeNotification) {
                for (int i = 0; i < manager.getVolumeNotificationtListenerList().size(); i++) {
                    manager.getVolumeNotificationtListenerList().get(i).onVolume((byte) (oldVolumeNotification * 100 / maxVolumeNotification), (byte) (newVolumeNotification * 100 / maxVolumeNotification));
                }
            }
            if (changeVolumeDtmf) {
                for (int i = 0; i < manager.getVolumeDtmftListenerList().size(); i++) {
                    manager.getVolumeDtmftListenerList().get(i).onVolume((byte) (oldVolumeDtmf * 100 / maxVolumeDtmf), (byte) (newVolumeDtmf * 100 / maxVolumeDtmf));
                }
            }
            if (changeVolumeVoiceCall) {
                for (int i = 0; i < manager.getVolumeVoiceCalltListenerList().size(); i++) {
                    manager.getVolumeVoiceCalltListenerList().get(i).onVolume((byte) (oldVolumeVoiceCall * 100 / maxVolumeVoiceCall), (byte) (newVolumeVoiceCall * 100 / maxVolumeVoiceCall));
                }
            }
        }
        oldVolumeMedia = (byte) audio.getStreamVolume(AudioManager.STREAM_MUSIC);
        oldVolumeSystem = (byte) audio.getStreamVolume(AudioManager.STREAM_SYSTEM);
        oldVolumeRing = (byte) audio.getStreamVolume(AudioManager.STREAM_RING);
        oldVolumeNotification = (byte) audio.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
        oldVolumeDtmf = (byte) audio.getStreamVolume(AudioManager.STREAM_DTMF);
        oldVolumeVoiceCall = (byte) audio.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
    }

    public int getVolume(int type) {
        return (manager.getAudioManager().getStreamVolume(type) * 100 / manager.getAudioManager().getStreamMaxVolume(type));
    }

    public void mute(int type, boolean state) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.getAudioManager().adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, state ? AudioManager.ADJUST_MUTE : AudioManager.ADJUST_UNMUTE, AudioManager.FLAG_SHOW_UI);
        } else {
            manager.getAudioManager().setStreamMute(type, state);
        }
    }

    public void setVolume(int type, int value) {
        if (value < 0) {
            value = 0;
        } else if (value > 100) {
            value = 100;
        }
        int vol = (value * manager.getAudioManager().getStreamMaxVolume(type)) / 100;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            vol = vol == 0 ? 1 : vol;
        }
        manager.getAudioManager().setStreamVolume(type, vol, AudioManager.FLAG_SHOW_UI);
    }

    public void volumeUp(int type) {
        manager.getAudioManager().adjustStreamVolume(type, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
    }

    public void volumeDown(int type) {
        manager.getAudioManager().adjustStreamVolume(type, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
    }
}
