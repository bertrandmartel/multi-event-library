package fr.bmartel.droidphoneevent;

import android.telephony.PhoneStateListener;

/**
 * @author Bertrand Martel
 */
public class PhoneListener extends PhoneStateListener {

    private DroidEvent manager = null;
    
    public PhoneListener(DroidEvent manager) {
        this.manager = manager;
    }

    public void onCallStateChanged(int state, String incomingNumber) {

        if (state == 1) {
            if (manager != null && manager.getPhoneCallListenerList() != null) {
                //Phone is rining
                for (int i = 0; i < manager.getPhoneCallListenerList().size(); i++) {
                    manager.getPhoneCallListenerList().get(i).onIncomingCall(incomingNumber);
                }
            }
        } else if (state == 2) {
            if (manager != null && manager.getPhoneCallListenerList() != null) {
                //call has been taken
                for (int i = 0; i < manager.getPhoneCallListenerList().size(); i++) {
                    manager.getPhoneCallListenerList().get(i).onOffHook();
                }
            }
        }
    }
}
