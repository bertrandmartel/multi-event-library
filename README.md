# Multi Event library #

[![Build Status](https://travis-ci.org/akinaru/multi-event-library.svg)](https://travis-ci.org/akinaru/multi-event-library)
[![Download](https://api.bintray.com/packages/akinaru/maven/multi-event-library/images/download.svg) ](https://bintray.com/akinaru/maven/multi-event-library/_latestVersion)
[![License](http://img.shields.io/:license-mit-blue.svg)](LICENSE.md)

<hr/>

Small Android library to catch a range of event type on Android devices via listeners :

* Volume level change event for Media / System / Ring / DTMF / Voice call / Notification
* SMS reception event
* Incoming phone call event
* phone hook-off event
* screen state change event (screen on/off) 
* Wifi connection change event
* Ethernet connection change event (for Android box)

You can also access current value for specific properties :

* current value for volume media / System / Ring / DTMF / Voice call / Notification
* current screen state value
* curent wifi state value
* current ethernet state value

## Include in your project

Grab from Bintray maven repository :

```
compile 'akinaru:multi-event-library:0.3'
```

## How to use ?

First initialize `MultiEvent` object with your application context :

```
import fr.bmartel.android.multievent.MultiEvent;

.......

MultiEvent eventManager = new MultiEvent(context);
```

## Android permissions

According to which event you want to track, you may need some Android permissions :

| Event Type                                                            |    Permission                |
|------------------------------------------------------------------------|----------------------------|
| Incoming phone call                        | `<uses-permission android:name="android.permission.READ_PHONE_STATE"/>`  |
| SMS reception event                        | `<uses-permission android:name="android.permission.RECEIVE_SMS"/>`  |
| phone hook-off                             | `<uses-permission android:name="android.permission.READ_PHONE_STATE"/>`  |
| Wifi / Ethernet connection change          | `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>`           |
| Volume level change event                  |      |

## Volume level event listener

### System volume

```
eventManager.addSystemVolumeListener(new IVolumeListener() {
    @Override
    public void onVolume(byte oldVolume, byte newVolume) {
        Log.i(TAG, "[VOLUME SYSTEM] old volume : " + oldVolume + " | " + "new volume : " + newVolume);
    }
});
```

### Media volume

```
eventManager.addMediaVolumeListener(new IVolumeListener() {
    @Override
    public void onVolume(byte oldVolume, byte newVolume) {
        Log.i(TAG, "[VOLUME MEDIA] old volume : " + oldVolume + " | " + "new volume : " + newVolume);
    }
});
```

### DTMF volume

```
eventManager.addDtmfVolumeListener(new IVolumeListener() {
    @Override
    public void onVolume(byte oldVolume, byte newVolume) {
        Log.i(TAG, "[VOLUME DTMF] old volume : " + oldVolume + " | " + "new volume : " + newVolume);
    }
});
```

### Notification volume

```
eventManager.addNotificationVolumeListener(new IVolumeListener() {
	@Override
	public void onVolume(byte oldVolume, byte newVolume) {
	    Log.i(TAG, "[VOLUME NOTIF] old volume : " + oldVolume + " | " + "new volume : " + newVolume);
	}
});
```

### Ring volume

```
eventManager.addRingVolumeListener(new IVolumeListener() {
	@Override
	public void onVolume(byte oldVolume, byte newVolume) {
	    Log.i(TAG, "[VOLUME RING] old volume : " + oldVolume + " | " + "new volume : " + newVolume);
	}
});
```

### Voice call volume

```
eventManager.addVoiceCallVolumeListener(new IVolumeListener() {
	@Override
	public void onVolume(byte oldVolume, byte newVolume) {
	    Log.i(TAG, "[VOLUME VOICE CALL] old volume : " + oldVolume + " | " + "new volume : " + newVolume);
	}
});
```

With respective import : ``import fr.bmartel.android.multievent.listener.IVolumeListener;``

## SMS reception

```
import fr.bmartel.android.multievent.listener.ISmsListener;

.......

eventManager.addSmsListener(new ISmsListener() {
	@Override
	public void onSmsReceived() {
	    Log.i(TAG, "[SMS RECEIVED]");
	}
});
```

## Incoming call/hook-off

```
import fr.bmartel.android.multievent.listener.IPhoneCallListener;

.......

eventManager.addPhoneCallListener(new IPhoneCallListener() {
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

## Screen state

```
import fr.bmartel.android.multievent.listener.IScreenStateListener;

.......

eventManager.addScreenStateListener(new IScreenStateListener() {
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

## Connectivity state

```
import fr.bmartel.android.multievent.listener.IConnectivityListener;

.......

eventManager.addConnectivityChangeListener(new IConnectivityListener() {

    @Override
    public void onWifiStateChange(NetworkInfo.State formerState, NetworkInfo.State newState) {
        Log.i(TAG,[WIFI STATE CHANGE] from state " + formerState + " to " + newState);
    }

    @Override
    public void onEthernetStateChange(NetworkInfo.State formerState, NetworkInfo.State newState) {
        Log.i(TAG,[ETHERNET STATE CHANGE] from state " + formerState + " to " + newState);
    }
});
```

## Close all listeners / unregister receivers

In your onDestroy() you have to call the close() method
```
@Override
public void onDestroy() {
	super.onDestroy();
	eventManager.close();
}
```

You can remove a single listener : ``eventManager.removeListeners(listener)``

Or remove all listener you have created :  ``eventManager.removeAllListeners()``

## Access raw value directly

You may want to have raw value at a precise time. 

* access all volume state type value :

```
Log.i(TAG, "Media volume        : " + eventManager.getMediaVolume());
Log.i(TAG, "System volume       : " + eventManager.getSystemVolume());
Log.i(TAG, "Ring volume         : " + eventManager.getRingVolume());
Log.i(TAG, "Notification volume : " + eventManager.getNotificationVolume());
Log.i(TAG, "DTMF volume         : " + eventManager.getDtmfVolume());
Log.i(TAG, "Voice call volume   : " + eventManager.getVoiceCallVolume());
```

* access screen state value :

```
Log.i(TAG, "Screen state        : " + eventManager.getScreenState());
```

* access Wifi connectivity state :

```
Log.i(TAG, "Wifi state          : " + eventManager.getWifiState());
```

* access Ethernet connectivity state :

```
Log.i(TAG, "Ethernet state      : " + eventManager.getEthernetState());
```

## License

The MIT License (MIT) Copyright (c) 2015 Bertrand Martel
