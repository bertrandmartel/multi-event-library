package fr.bmartel.droidphoneevent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author Bertrand Martel
 */
public class EventReceiver extends BroadcastReceiver {

    private String TAG = EventReceiver.class.getName();

    private DroidEvent manager = null;

    public EventReceiver(DroidEvent manager) {
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
