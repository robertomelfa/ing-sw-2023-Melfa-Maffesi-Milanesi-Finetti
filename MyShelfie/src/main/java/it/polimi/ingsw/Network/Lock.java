package it.polimi.ingsw.Network;

import java.io.Serializable;

public class Lock implements Serializable {
    private boolean isLocked = false;

    public synchronized boolean getLock(){ return isLocked; }

    public synchronized void acquire() throws InterruptedException {
        while (isLocked) {
            wait();
        }
        isLocked = true;
    }

    public synchronized void release() {
        isLocked = false;
        notify();
    }

}
