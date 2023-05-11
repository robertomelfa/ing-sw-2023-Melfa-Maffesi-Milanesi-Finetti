package it.polimi.ingsw.Network;

import java.io.Serializable;

public class Lock implements Serializable {
    private boolean isLocked = false;

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
    }

    /**
     * release the lock
     */
    public synchronized void release() {
        isLocked = false;
        notify();
    }

}
