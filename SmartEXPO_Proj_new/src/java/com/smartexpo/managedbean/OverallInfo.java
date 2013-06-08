/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.managedbean;

import com.smartexpo.models.CommentInfo;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;

/**
 *
 * @author ben
 */
@ManagedBean(eager = true)
@ApplicationScoped
public class OverallInfo {

    private final static int threshold = 500;
    private LinkedList<CommentInfo> commentBuffer = new LinkedList<CommentInfo>();
    private long num = 0;

    /**
     * Creates a new instance of OverallInfo
     */
    public OverallInfo() {
//        updateComment(new CommentInfo(1, "codinfox", new Date(), "this is a test, this is a test, this is a test"));
    }

    /**
     * Get the latest comment by item id
     *
     * @param key is the item id
     * @return the latest comment for the specified item, or <code>null</code>
     * if there if no latest comment present.
     */
    public CommentInfo[] getComment() {
        CommentInfo[] rtrn = new CommentInfo[commentBuffer.size()];
        commentBuffer.toArray(rtrn);
//        LOG.log(Level.WARNING, "getComment()" + rtrn.length);
        return rtrn;
    }

    /**
     * Update the latest comment by item id
     *
     * @param key the item id
     * @param info the latest comment information
     */
    public void updateComment(CommentInfo info) {
        info.setNum(num++);
        Date cmp = info.getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(cmp);
        calendar.add(Calendar.MILLISECOND, -threshold);
        cmp = calendar.getTime();
        boolean duplicate = false;
        for (int i = 0; i < commentBuffer.size(); i++) {
            if (info.getId() == commentBuffer.get(i).getId()) {
                duplicate = true;
                commentBuffer.set(i, info);
            } else if (cmp.after(commentBuffer.get(i).getTime())) {
                commentBuffer.remove(i);
            }
        }
        if (!duplicate) {
            commentBuffer.add(info);
        }
    }
}
