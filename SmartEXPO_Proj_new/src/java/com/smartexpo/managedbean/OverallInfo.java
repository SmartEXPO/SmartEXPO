/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.managedbean;

import com.smartexpo.models.ServerSentMessage;
import com.sun.istack.logging.Logger;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * This bean is used to store some buffered information for server sent message.
 *
 * @author ben
 */
@ManagedBean(eager = true)
@ApplicationScoped
public class OverallInfo {

    private int threshold = 500;
    private LinkedList<ServerSentMessage> msgbuffer = new LinkedList<ServerSentMessage>();
    private long num = 0;

    /**
     * Creates a new instance of OverallInfo
     */
    public OverallInfo() {
//        updateComment(new CommentInfo(1, "codinfox", new Date(), "this is a test, this is a test, this is a test"));
    }

    /**
     * Get the buffered messages
     *
     * @return
     */
    public ServerSentMessage[] getMessage() {
        ServerSentMessage[] rtrn = new ServerSentMessage[msgbuffer.size()];
        msgbuffer.toArray(rtrn);
//        LOG.log(Level.WARNING, "getComment()" + rtrn.length);
        return rtrn;
    }

    /**
     * Update the message buffer
     *
     * @param info the new message to be sent
     */
    public void updateMessage(ServerSentMessage info) {
        info.setNum(num++);
        Date cmp = info.getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(cmp);
        calendar.add(Calendar.MILLISECOND, -threshold);
        cmp = calendar.getTime();
        boolean duplicate = false;
        for (int i = 0; i < msgbuffer.size(); i++) {
            if (info.getId() == msgbuffer.get(i).getId()) {
                duplicate = true;
                msgbuffer.set(i, info);
            } else if (cmp.after(msgbuffer.get(i).getTime())) {
                msgbuffer.remove(i);
            }
        }
        if (!duplicate) {
            msgbuffer.add(info);
        }
    }

    public int getThreshold() {
        return threshold;
    }

    /**
     * Set push frequency (ms)
     *
     * @param threshold
     */
    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
}
