package eirb.ohayak.pam.androidapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.concurrent.atomic.AtomicLong;

public class TimerService extends Service {
    private static AtomicLong counter;
    private Thread ticker;

    public TimerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int onStartCommand(Intent intent, int flag, int startId) {
        if (ticker == null) {
            counter = new AtomicLong(0);
            ticker = new Thread(new Ticker());
            ticker.start();
        }
        return START_STICKY;
    }

    public static long getCounter() {
        return counter.get();
    }

    private class Ticker implements Runnable {

        @Override
        public void run() {
            while (!ticker.isInterrupted()) {
                try {
                    Thread.sleep(1000);
                    counter.set(counter.get()+1L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
