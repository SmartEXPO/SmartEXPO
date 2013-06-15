/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.managedbean.item;

import com.smartexpo.controls.GetInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.UserTransaction;

/**
 *
 * @author Boy
 */
@ManagedBean
@ViewScoped
public class Author implements Serializable {

    @PersistenceContext(unitName = "SmartEXPO_ProjPU")
    EntityManager em;
    @PersistenceUnit(unitName = "SmartEXPO_ProjPU")
    EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;
    private GetInfo gi = null;
    // Author fields
    private List<com.smartexpo.models.Author> authors;
    private List<Integer> ids;
    private List<String> names;
    private List<Date> birthdays;
    private List<Date> deathDates;
    private List<String> introductions;

    /**
     * Creates a new instance of Author
     */
    public Author() {
        ids = new ArrayList<Integer>();
        names = new ArrayList<String>();
        birthdays = new ArrayList<Date>();
        deathDates = new ArrayList<Date>();
        introductions = new ArrayList<String>();
    }

    @PostConstruct
    public void postConstruct() {
        if (gi == null) {
            gi = new GetInfo(emf, utx);
        }
        HttpServletRequest request = (HttpServletRequest) FacesContext
                .getCurrentInstance().getExternalContext().getRequest();

        int itemID = Integer.parseInt((String) request.getAttribute("id"));
        authors = gi.getAuthorsByItemID(itemID);

        setAllAuthorsInfo();
    }

    /**
     * @return the ids
     */
    public List<Integer> getIds() {
        return ids;
    }

    /**
     * @param ids the ids to set
     */
    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    /**
     * @return the names
     */
    public List<String> getNames() {
        return names;
    }

    /**
     * @param names the names to set
     */
    public void setNames(List<String> names) {
        this.names = names;
    }

    /**
     * @return the birthdays
     */
    public List<Date> getBirthdays() {
        return birthdays;
    }

    /**
     * @param birthdays the birthdays to set
     */
    public void setBirthdays(List<Date> birthdays) {
        this.birthdays = birthdays;
    }

    /**
     * @return the deathDates
     */
    public List<Date> getDeathDates() {
        return deathDates;
    }

    /**
     * @param deathDates the deathDates to set
     */
    public void setDeathDates(List<Date> deathDates) {
        this.deathDates = deathDates;
    }

    /**
     * @return the introductions
     */
    public List<String> getIntroductions() {
        return introductions;
    }

    /**
     * @param introductions the introductions to set
     */
    public void setIntroductions(List<String> introductions) {
        this.introductions = introductions;
    }

    private void setAllAuthorsInfo() {
        for (com.smartexpo.models.Author author : authors) {
            // 以下数据如若在数据库为null时可能还要进一步处理
            // Video, Audio, Description同样，不作不过标记
            addAuthorID(author);
            addAuthorName(author);
            addAuthorBirthdays(author);
            addAuthorDeathDates(author);
            addIntroduction(author);
        }
    }

    private void addAuthorID(com.smartexpo.models.Author author) {
        ids.add(author.getAuthorId());
    }

    private void addAuthorName(com.smartexpo.models.Author author) {
        names.add(author.getName());
    }

    private void addAuthorBirthdays(com.smartexpo.models.Author author) {
        birthdays.add(author.getBirthday());
    }

    private void addAuthorDeathDates(com.smartexpo.models.Author author) {
        deathDates.add(author.getDeathDate());
    }

    private void addIntroduction(com.smartexpo.models.Author author) {
        introductions.add(author.getIntroduction());
    }
}
