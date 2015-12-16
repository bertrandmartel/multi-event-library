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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * @author Bertrand Martel
 */
public class ConnectivityReceiver extends BroadcastReceiver {

    private final static String TAG = ConnectivityReceiver.class.getSimpleName();

    private MultiEvent manager = null;

    private NetworkInfo.State wifiState = NetworkInfo.State.UNKNOWN;
    private NetworkInfo.State ethernetState = NetworkInfo.State.UNKNOWN;

    public ConnectivityReceiver(Context context, MultiEvent manager) {
        this.manager = manager;

        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo ethernet = connManager.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
        if (wifi != null)
            wifiState = wifi.getState();
        if (ethernet != null)
            ethernetState = ethernet.getState();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo ethernet = conMan.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
        if (wifi != null) {
            if (wifiState != wifi.getState()) {

                NetworkInfo.State oldState = wifiState;

                Log.i(TAG, "wifi state has changed");
                wifiState = wifi.getState();
                for (int i = 0; i < manager.getConnectivityListenerList().size(); i++) {
                    manager.getConnectivityListenerList().get(i).onWifiStateChange(oldState, wifiState);
                }
            }
        }
        if (ethernet != null) {
            if (ethernetState != ethernet.getState()) {

                NetworkInfo.State oldState = ethernetState;

                Log.i(TAG, "ethernet state has changed");
                ethernetState = ethernet.getState();
                for (int i = 0; i < manager.getConnectivityListenerList().size(); i++) {
                    manager.getConnectivityListenerList().get(i).onEthernetStateChange(oldState, ethernetState);
                }
            }
        }
    }

    public NetworkInfo.State getWifiState() {
        return wifiState;
    }

    public NetworkInfo.State getEthernetState() {
        return ethernetState;
    }
}
