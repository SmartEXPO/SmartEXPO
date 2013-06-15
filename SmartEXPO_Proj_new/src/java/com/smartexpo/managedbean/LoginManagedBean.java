/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.managedbean;

import com.smartexpo.controls.GetInfo;
import com.smartexpo.models.Manager;
import com.smartexpo.util.MD5;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Boy
 */
@ManagedBean(eager = true)
@SessionScoped
public class LoginManagedBean implements Serializable {

    @PersistenceContext(unitName = "SmartEXPO_ProjPU")
    EntityManager em;
    @PersistenceUnit(unitName = "SmartEXPO_ProjPU")
    EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;
    private GetInfo gi;
    // LoginManagedBean Field
    private String username;
    private String password;
    private boolean[] permissions;
    @ManagedProperty(value = "false")
    private boolean status;

    /**
     * Creates a new instance of LoginManagedBean
     */
    public LoginManagedBean() {
        permissions = new boolean[6];
        for (int i = 0; i <= 5; ++i) {
            permissions[i] = false;
        }
    }

    @PostConstruct
    public void postConstruct() {
        if (gi == null) {
            gi = new GetInfo(emf, utx);
        }
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SignUpManagedBean signUpManagedBean = (SignUpManagedBean) facesContext
                .getELContext().getELResolver()
                .getValue(facesContext.getELContext(), null, "signUpManagedBean");

        username = signUpManagedBean.getUsername();
        password = signUpManagedBean.getPassword();

        if (username != null && !username.equals("")
                && password != null && !password.equals("")) {
            // 数据库验证
            setStatus(true);
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(false);
            session.setAttribute("user", username);
        }
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the permissions
     */
    public boolean[] getPermissions() {
        return permissions;
    }

    /**
     * @param permissions the permissions to set
     */
    public void setPermissions(boolean[] permissions) {
        this.permissions = permissions;
    }

    /**
     * @return the status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Verify the user
     *
     * @return item.xhtml or error.xhtml
     */
    public void verify(AjaxBehaviorEvent event) {
        if (isPass(username, password)) { // 数据库验证
            setStatus(true);
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(false);
            session.setAttribute("user", username);

            if (true) { // choose auto log in
//                recordCookie(username);
            }

            RequestContext.getCurrentInstance()
                    .execute("vanish();void(0);"); // Close login pannel
        } else {
            RequestContext.getCurrentInstance()
                    .execute(("alert('Username or password wrong')"));
        }
    }

    public String adminLogin() {
        if (isPass(username, password)) {
            setStatus(true);
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(false);
            session.setAttribute("user", username);

            return "item_view?faces-redirect=true";
        }

        return null;
    }

    public void logout(ActionEvent event) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        session.invalidate();

        for (int i = 1; i <= 5; ++i) {
            permissions[i] = false;
        }
        username = password = null;
        setStatus(false);

        // 从数据库删除username和session的tuple，保证下次不会自动登录
        RequestContext.getCurrentInstance()
                .execute(("alert('Log out successfully!');location.reload(true);"));
    }

    private boolean isPass(String user, String pass) {
        boolean result = false;

        String encryptPassword = MD5.md5(pass);
        List<Manager> managers = gi.getManagerByName(user);
        if (managers == null) {
        } else {
            Manager manager = managers.get(0);
            if (manager.getPassword().equals(encryptPassword)) {
                permissions[1] = manager.isPermission1();
                permissions[2] = manager.isPermission2();
                permissions[3] = manager.isPermission3();
                permissions[4] = manager.isPermission4();
                permissions[5] = manager.isPermission5();

                result = true;
            }
        }
        return result;
    }

    private void recordCookie(String user) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        HttpServletResponse response = (HttpServletResponse) FacesContext
                .getCurrentInstance().getExternalContext().getResponse();

        Cookie usernameCookie, sessionIDCookie;
        usernameCookie = new Cookie("username", user);
        usernameCookie.setMaxAge(60 * 60 * 24 * 14);
        response.addCookie(usernameCookie);

        String sessionID = session.getId();
        sessionIDCookie = new Cookie("sessionid", sessionID);
        sessionIDCookie.setMaxAge(60 * 60 * 24 * 14);
        response.addCookie(sessionIDCookie);

        // 将信息插入到数据库中
    }
}
