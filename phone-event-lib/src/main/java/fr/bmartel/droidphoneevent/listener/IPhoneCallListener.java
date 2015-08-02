package fr.bmartel.droidphoneevent.listener;

/**
 * @author Bertrand Martel
 */
public interface IPhoneCallListener {

    public void onIncomingCall(String phoneNumber);

    public void onOffHook();
}
