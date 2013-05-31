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
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Boy
 */
@ManagedBean
@SessionScoped
public class UserViewManagedBean implements Serializable {

    private static final Logger LOG = Logger.getLogger(UserViewManagedBean.class.getName());
    private List<User> users;
    private User selectedUser;

    /**
     * Creates a new instance of UserViewManagedBean
     */
    public UserViewManagedBean() {
        users = new ArrayList<User>();
        for (int i = 0; i < 88; i++) {
            List<Boolean> permissions = new ArrayList<Boolean>();
            for (int j = 0; j < User.Count; j++) {
                permissions.add((Math.random() > 0.5 ? true : false));
            }
            users.add(new User("Username " + i, "Password " + i, permissions));
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

    public void onEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Car Edited", ((User) event.getObject()).getUsername());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Car Cancelled", ((User) event.getObject()).getUsername());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * @return the selectedUser
     */
    public User getSelectedUser() {
        return selectedUser;
    }

    /**
     * @param selectedUser the selectedUser to set
     */
    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }
    
    public void test() {
    }
}
