package it.polimi.ingsw.Network;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

public class Lock implements Serializable {
    private boolean isLocked = false;

    private Timer lockTimer = new Timer();

    /**
     *
     * @return the lock
     */
    public synchronized boolean getLock(){ return isLocked; }

    /**
     * while is locked, wait
     * @throws InterruptedException
     */
    public synchronized void acquire() throws InterruptedException {
        while (isLocked) {
            wait();
        }
        isLocked = true;
        setLockTimer();
    }

    /**
     * release the lock
     */
    public synchronized void release() {
        lockTimer.cancel();
        isLocked = false;
        notify();
    }

    /**
     * start a 30 seconds timer with a task that will release the lock
     * when the timer ends
     */
    public synchronized void setLockTimer() {
        lockTimer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                release();
            }
        };
        lockTimer.schedule(task, 30000);
    }

}
