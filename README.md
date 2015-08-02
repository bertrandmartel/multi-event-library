# Droid Phone Event library #

http://akinaru.github.io/droid-phone-event/

<i>Update 03/08/2015</i>

Easily catch a range of event type on Android devices :
* Volume level change event for Audio / System / Ring / DTMF / Voice call / Notification
* SMS reception event
* Incoming phone call event
* phone hook-off event
* screen state change event (screen on/off) 

Access to these events is enabled with listeners

<hr/>

<h3>Last release</h3>

[Droid phone event library](http://akinaru.github.io/droid-phone-event/releases/)

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
<hr/>

Tested on :
* Galaxy S4 (4.4)