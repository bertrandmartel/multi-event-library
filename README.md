# Droid Phone Event library #

http://akinaru.github.io/droid-phone-event/

<i>Update 03/08/2015</i>

Easily catch a range of event type on Android devices :
* Volume level change event for Media / System / Ring / DTMF / Voice call / Notification
* SMS reception event
* Incoming phone call event
* phone hook-off event
* screen state change event (screen on/off) 

Access to these events is enabled with listeners

* access current value for volume media / System / Ring / DTMF / Voice call / Notification
* access current screen state value

<hr/>

<h3>Last release</h3>

[Droid phone event library](https://github.com/akinaru/droid-phone-event/releases/)

<h3>Project structure</h3>

* phone-event-app : testing application
* phone-event-lib : library

can be built on Android Studio

<hr/>

<h2>How to use ? </h2>

First initialize DroidEvent object with your application context :

```
import fr.bmartel.droidphoneevent.DroidEvent;

.......

droidEvent = new DroidEvent(this);

```

<h3>Volume level event listener</h3>

* System volume change listener

```
droidEvent.addSystemVolumeListener(new IVolumeListener() {
    @Override
    public void onVolume(byte oldVolume, byte newVolume) {
        Log.i(TAG, "[VOLUME SYSTEM] old volume : " + oldVolume + " | " + "new volume : " + newVolume);
    }
});
```

* Media volume change listener

```
droidEvent.addMediaVolumeListener(new IVolumeListener() {
    @Override
    public void onVolume(byte oldVolume, byte newVolume) {
        Log.i(TAG, "[VOLUME MEDIA] old volume : " + oldVolume + " | " + "new volume : " + newVolume);
    }
});
```

* DTMF volume change listener

```
droidEvent.addDtmfVolumeListener(new IVolumeListener() {
    @Override
    public void onVolume(byte oldVolume, byte newVolume) {
        Log.i(TAG, "[VOLUME DTMF] old volume : " + oldVolume + " | " + "new volume : " + newVolume);
    }
});
```

* Notification volume change listener

```
droidEvent.addNotificationVolumeListener(new IVolumeListener() {
	@Override
	public void onVolume(byte oldVolume, byte newVolume) {
	    Log.i(TAG, "[VOLUME NOTIF] old volume : " + oldVolume + " | " + "new volume : " + newVolume);
	}
});
```

* Ring volume change listener

```
droidEvent.addRingVolumeListener(new IVolumeListener() {
	@Override
	public void onVolume(byte oldVolume, byte newVolume) {
	    Log.i(TAG, "[VOLUME RING] old volume : " + oldVolume + " | " + "new volume : " + newVolume);
	}
});
```

* Voice call volume change listener

```
droidEvent.addVoiceCallVolumeListener(new IVolumeListener() {
	@Override
	public void onVolume(byte oldVolume, byte newVolume) {
	    Log.i(TAG, "[VOLUME VOICE CALL] old volume : " + oldVolume + " | " + "new volume : " + newVolume);
	}
});
```

With respective import : ``import fr.bmartel.droidphoneevent.listener.IVolumeListener;``

<h3>SMS reception event listener</h3>

```
import fr.bmartel.droidphoneevent.listener.ISmsListener;

.......

droidEvent.addSmsListener(new ISmsListener() {
	@Override
	public void onSmsReceived() {
	    Log.i(TAG, "[SMS RECEIVED]");
	}
});
```

<h3>Incoming call/hook-off event listener</h3>

```
import fr.bmartel.droidphoneevent.listener.IPhoneCallListener;

.......

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
```

<h3>screen state change event listener</h3>

```
import fr.bmartel.droidphoneevent.listener.IScreenStateListener;

.......

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
```
<h3>close all listeners / unregister library objects</h3>

In your onDestroy() you have to call the close() method
```
@Override
public void onDestroy() {
	super.onDestroy();
	droidEvent.close();
}
```

You can remove a single listener : ``droidEvent.removeListeners(listener)``

Or remove all listener you have created :  ``droidEvent.removeAllListeners()``

<hr/>

<h3>Access raw value directly</h3>

You may want to have raw value at a precise time. 

You can access all volume state type value :

```
Log.i(TAG, "Media volume        : " + droidEvent.getMediaVolume());
Log.i(TAG, "System volume       : " + droidEvent.getSystemVolume());
Log.i(TAG, "Ring volume         : " + droidEvent.getRingVolume());
Log.i(TAG, "Notification volume : " + droidEvent.getNotificationVolume());
Log.i(TAG, "DTMF volume         : " + droidEvent.getDtmfVolume());
Log.i(TAG, "Voice call volume   : " + droidEvent.getVoiceCallVolume());
```

You can access screen state value :

```
Log.i(TAG, "Screen state        : " + droidEvent.getScreenState());
```

<hr/>

Tested on :
* Galaxy S4 (4.4)
