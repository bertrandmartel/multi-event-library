# Multi Event library #

[![Build Status](https://travis-ci.org/bertrandmartel/multi-event-library.svg?branch=master)](https://travis-ci.org/bertrandmartel/multi-event-library)
[ ![Download](https://api.bintray.com/packages/bertrandmartel/maven/multi-event-library/images/download.svg) ](https://bintray.com/bertrandmartel/maven/multi-event-library/_latestVersion)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/fr.bmartel/multi-event-library/badge.svg)](https://maven-badges.herokuapp.com/maven-central/fr.bmartel/multi-event-library)
[![Javadoc](http://javadoc-badge.appspot.com/fr.bmartel/multi-event-library.svg?label=javadoc)](http://javadoc-badge.appspot.com/fr.bmartel/multi-event-library)
[![License](http://img.shields.io/:license-mit-blue.svg)](LICENSE.md)

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
* mute volume
* current screen state value
* curent wifi state value
* current ethernet state value

## Compatibility

For Android 1.0+ except for the `isScreenOn()` only accessible from Android 2.1+ but notification can be received anyway

## Include in your project

Grab from Bintray maven repository :

```groovy
compile 'akinaru:multi-event-library:0.33'
```

## How to use ?

First initialize `MultiEvent` object with your application context :

```java
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

```java
eventManager.addSystemVolumeListener(new IVolumeListener() {
    @Override
    public void onVolume(byte oldVolume, byte newVolume) {
        Log.i(TAG, "[VOLUME SYSTEM] old volume : " + oldVolume + " | " + "new volume : " + newVolume);
    }
});
```

### Media volume

```java
eventManager.addMediaVolumeListener(new IVolumeListener() {
    @Override
    public void onVolume(byte oldVolume, byte newVolume) {
        Log.i(TAG, "[VOLUME MEDIA] old volume : " + oldVolume + " | " + "new volume : " + newVolume);
    }
});
```

### DTMF volume

```java
eventManager.addDtmfVolumeListener(new IVolumeListener() {
    @Override
    public void onVolume(byte oldVolume, byte newVolume) {
        Log.i(TAG, "[VOLUME DTMF] old volume : " + oldVolume + " | " + "new volume : " + newVolume);
    }
});
```

### Notification volume

```java
eventManager.addNotificationVolumeListener(new IVolumeListener() {
	@Override
	public void onVolume(byte oldVolume, byte newVolume) {
	    Log.i(TAG, "[VOLUME NOTIF] old volume : " + oldVolume + " | " + "new volume : " + newVolume);
	}
});
```

### Ring volume

```java
eventManager.addRingVolumeListener(new IVolumeListener() {
	@Override
	public void onVolume(byte oldVolume, byte newVolume) {
	    Log.i(TAG, "[VOLUME RING] old volume : " + oldVolume + " | " + "new volume : " + newVolume);
	}
});
```

### Voice call volume

```java
eventManager.addVoiceCallVolumeListener(new IVolumeListener() {
	@Override
	public void onVolume(byte oldVolume, byte newVolume) {
	    Log.i(TAG, "[VOLUME VOICE CALL] old volume : " + oldVolume + " | " + "new volume : " + newVolume);
	}
});
```

With respective import : ``import fr.bmartel.android.multievent.listener.IVolumeListener;``

## SMS reception

```java
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

```java
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

```java
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

```java
import fr.bmartel.android.multievent.listener.IConnectivityListener;

.......

eventManager.addConnectivityChangeListener(new IConnectivityListener() {

    @Override
    public void onWifiStateChange(NetworkInfo.State formerState, NetworkInfo.State newState) {
        Log.i(TAG,"[WIFI STATE CHANGE] from state " + formerState + " to " + newState);
    }

    @Override
    public void onEthernetStateChange(NetworkInfo.State formerState, NetworkInfo.State newState) {
        Log.i(TAG,"[ETHERNET STATE CHANGE] from state " + formerState + " to " + newState);
    }
});
```

## Close all listeners / unregister receivers

In your onDestroy() you have to call the close() method

```java
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

* access all volume value in % :

```java
Log.v(TAG, "Media volume        : " + eventManager.getMediaVolume());
Log.v(TAG, "System volume       : " + eventManager.getSystemVolume());
Log.v(TAG, "Ring volume         : " + eventManager.getRingVolume());
Log.v(TAG, "Notification volume : " + eventManager.getNotificationVolume());
Log.v(TAG, "DTMF volume         : " + eventManager.getDtmfVolume());
Log.v(TAG, "Voice call volume   : " + eventManager.getVoiceCallVolume());
```

* mute volume :

```java
eventManager.muteMedia(true);
eventManager.muteSystem(true);
eventManager.muteAlarm(true);
eventManager.muteRing(true);
```

* access screen state value :

```java
Log.v(TAG, "Screen state        : " + eventManager.getScreenState());
```

* access Wifi connectivity state :

```java
Log.v(TAG, "Wifi state          : " + eventManager.getWifiState());
```

* access Ethernet connectivity state :

```java
Log.v(TAG, "Ethernet state      : " + eventManager.getEthernetState());
```

## License

The MIT License (MIT) Copyright (c) 2015-2017 Bertrand Martel
