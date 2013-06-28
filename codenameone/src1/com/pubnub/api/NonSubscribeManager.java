package com.pubnub.api;

import java.util.Hashtable;
import java.util.Vector;

class NonSubscribeManager extends AbstractNonSubscribeManager {

    public NonSubscribeManager(String name, int connectionTimeout,
            int requestTimeout) {
        super(name, connectionTimeout, requestTimeout);
    }

    @Override
    public void clearRequestQueue() {
        
    }

    public void setConnectionTimeout(int timeout) {
        this.connectionTimeout = timeout;
        for (int i = 0; i < _workers.length; i++) {
            _workers[i].setConnectionTimeout(timeout);
        }
    }

    public void setRequestTimeout(int timeout) {
        this.requestTimeout = timeout;
        for (int i = 0; i < _workers.length; i++) {
            _workers[i].setRequestTimeout(timeout);
        }
    }

    private static int _maxWorkers = 1;
    protected Vector _waiting = new Vector();
    protected Worker _workers[];
    protected String name;
    protected volatile int connectionTimeout;
    protected volatile int requestTimeout;
    protected Hashtable headers;
    private static int count = 0;

    protected static Logger log = new Logger(RequestManager.class);

    public static int getWorkerCount() {
        return _maxWorkers;
    }

    public abstract Worker getWorker();

    private void initManager(int maxCalls, String name) {

    }

    private void interruptWorkers() {

    }

    @Override
    public void resetWorkers() {

    }

    public void setHeader(String key, String value) {
        this.headers.put(key, value);
    }

    public void resetHttpManager() {
        clearRequestQueue();
        resetWorkers();
    }

    @Override
    public void abortClearAndQueue(HttpRequest hreq) {

    }

    @Override
    public void queue(HttpRequest hreq) {

    }

    public static void setWorkerCount(int count) {
       
    }

    @Override
    public void stop() {
    }
    
}
