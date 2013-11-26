package com.fitnotif.common;

/**
 * Interface para servidores
 * @author malgia
 * @version 1.0
 */
public abstract class Server extends Thread{
    public abstract void getConnection();
    public abstract void closeConnection();
}
