package com.fitnotif.processors;

import com.fitnotif.notification.Request;

/**
 * Interface que implementan todos los Procesadores
 * @author malgia
 * @version 1.0
 */
public interface Processor {
     
    public Request process(Request request);
}
