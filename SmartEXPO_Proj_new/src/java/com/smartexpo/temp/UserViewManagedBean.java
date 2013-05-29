/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.temp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Boy
 */
@ManagedBean
@RequestScoped
public class UserViewManagedBean implements Serializable {

    private List<User> users;
    static final Logger logger = Logger.getLogger(UserViewManagedBean.class.getName());

    /**
     * Creates a new instance of UserViewManagedBean
     */
    public UserViewManagedBean() {
        users = new ArrayList<User>();
        for (int i = 0; i < 20; i++) {
            boolean[] pers = new boolean[User.permissionCount];
            for (int j = 0; j < pers.length; j++) {
                pers[j] = Math.random() > 0.5 ? true : false;
            }
            users.add(new User("user " + i, "pass " + i, pers));
        }
    }

    /**
     * @return the users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }

    public int getCount() {
        return users.size();
    }

    public void onEdit(RowEditEvent event) {
        logger.log(Level.WARNING, "Edit");
        FacesMessage msg = new FacesMessage("Edited", ((User) event.getObject()).getUsername());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCancel(RowEditEvent event) {
        logger.log(Level.WARNING, "Canceled");
        FacesMessage msg = new FacesMessage("Canceled", ((User) event.getObject()).getUsername());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
