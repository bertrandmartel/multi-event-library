package fr.bmartel.droidphoneevent.listener;

/**
 * Listener for volume change
 *
 * @author Bertrand Martel
 */
public interface IVolumeListener {

    public void onVolume(byte oldVolume, byte newVolume);

}
