package it.polimi.ingsw.Network;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

public class Lock implements Serializable {
    private boolean isLocked = false;

    private Timer timer = new Timer();


    /**
     *
     * @return the lock
     */
    public synchronized boolean getLock(){ return isLocked; }

    /**
     * while is locked, wait
     * @throws InterruptedException
     */
    public synchronized void acquire() throws InterruptedException, RemoteException {
        while (isLocked) {
            wait();
        }
        isLocked = true;
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                release();
            }
        };
        timer.schedule(task, 30000);
    }

    /**
     * release the lock
     */
    public synchronized void release() {
        timer.cancel();
        isLocked = false;
        // wait to update the server
        try{
            Thread.sleep(500);
        }catch(InterruptedException e){}
        notify();
    }

}
