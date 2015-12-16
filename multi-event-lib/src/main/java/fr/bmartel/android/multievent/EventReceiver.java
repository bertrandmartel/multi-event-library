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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author Bertrand Martel
 */
public class EventReceiver extends BroadcastReceiver {

    private String TAG = EventReceiver.class.getName();

    private MultiEvent manager = null;

    public EventReceiver(MultiEvent manager) {
        this.manager = manager;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        if (action.equals(Intent.ACTION_SCREEN_ON)) {
            if (manager != null && manager.getScreenStateListenerList() != null) {
                for (int i = 0; i < manager.getScreenStateListenerList().size(); i++) {
                    manager.getScreenStateListenerList().get(i).onScreenOn();
                }
            }
        } else if (action.equals(Intent.ACTION_SCREEN_OFF)) {
            if (manager != null && manager.getScreenStateListenerList() != null) {
                for (int i = 0; i < manager.getScreenStateListenerList().size(); i++) {
                    manager.getScreenStateListenerList().get(i).onScreenOff();
                }
            }
        } else if (action.equals("android.provider.Telephony.SMS_RECEIVED")) {
            if (manager != null && manager.getSmsListenerList() != null) {
                for (int i = 0; i < manager.getSmsListenerList().size(); i++) {
                    manager.getSmsListenerList().get(i).onSmsReceived();
                }
            }
        }
    }
}
