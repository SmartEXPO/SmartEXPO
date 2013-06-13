/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.models;

import java.util.Date;

/**
 * All the java beans which need to be pushed to the browser client need to implement this interface.
 * @author ben
 */
public interface ServerSentMessage {
    /**
     * This should be implemented to parse the bean content to json format.
     * @return the json string sent to the client.
     */
    public String parseJSON();
    /**
     * Return the event id (as the id attribute of the Event instance of JS)
     * @return the event id
     */
    public int getId();
    /**
     * Return the event time.
     * @return the event time
     */
    public Date getTime();
    /**
     * In the bean class implemented this interface, a counter is needed.
     * 计数器用于标记时间，数字越大表示消息发送的越晚。
     * @param num 
     */
    public void setNum(long num);
    
    /**
     * In the bean class implemented this interface, a counter is needed.
     * 计数器用于标记时间，数字越大表示消息发送的越晚。
     * @return 
     */
    public long getNum();
}
