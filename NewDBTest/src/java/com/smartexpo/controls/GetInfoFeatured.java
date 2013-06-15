/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.controls;

import com.smartexpo.interfaces.Persister;
import com.smartexpo.dbproto.AgAg;
import com.smartexpo.dbproto.AgAttr;
import com.smartexpo.dbproto.Attr;
import com.smartexpo.dbproto.AttrDate;
import com.smartexpo.dbproto.AttrGroup;
import com.smartexpo.dbproto.AttrInt;
import com.smartexpo.dbproto.AttrString;
import com.smartexpo.dbproto.AttrText;
import com.smartexpo.dbproto.DBItem;
import com.smartexpo.jpacontrollers.AgAgJpaController;
import com.smartexpo.jpacontrollers.AgAttrJpaController;
import com.smartexpo.jpacontrollers.AttrDateJpaController;
import com.smartexpo.jpacontrollers.AttrGroupJpaController;
import com.smartexpo.jpacontrollers.AttrIntJpaController;
import com.smartexpo.jpacontrollers.AttrJpaController;
import com.smartexpo.jpacontrollers.AttrStringJpaController;
import com.smartexpo.jpacontrollers.AttrTextJpaController;
import com.smartexpo.jpacontrollers.exceptions.IllegalOrphanException;
import com.smartexpo.jpacontrollers.exceptions.NonexistentEntityException;
import com.smartexpo.jpacontrollers.exceptions.RollbackFailureException;
import com.smartexpo.models.type.AttrType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author tornado718
 */
public class GetInfoFeatured implements Serializable {

    private static EntityManager em = null;
    private static UserTransaction utx = null;
    private static EntityManagerFactory emf = null;
    private static final Logger LOG = Logger.getLogger(GetInfoFeatured.class.getName());
    private static Persister persister;

    public GetInfoFeatured(EntityManagerFactory _emf, UserTransaction _utx, Persister p) {
        this.emf = _emf;
        this.utx = _utx;
        this.em = emf.createEntityManager();
        this.persister = p;
    }

    public void addItem(DBItem item) {
        AttrGroup ag = new AttrGroup();
        ag.setAttrGroupName(item.getItemName());
        item.setItemAttrGroupId(ag);
        ag.setItem(item);
        persister.persist(ag);
    }

    public Attr generateStringAttr(String AttrName, String value) {

        Attr attr = new Attr();
        attr.setAttrName(AttrName);
        attr.setAttrType(AttrType.STRING);

        AttrString attrString = new AttrString();
        attrString.setAttrStringValue(value);
        attrString.setAttrStringRef(attr);
        attr.setAttrString(attrString);

        return attr;
    }

    public Attr generateIntAttr(String AttrName, int value) {
        Attr attr = new Attr();
        attr.setAttrName(AttrName);
        attr.setAttrType(AttrType.INT);

        AttrInt attrInt = new AttrInt();
        attrInt.setAttrIntValue(value);
        attrInt.setAttrIntRef(attr);
        attr.setAttrInt(attrInt);

        return attr;
    }

    public Attr generateTextAttr(String AttrName, String value) {
        Attr attr = new Attr();
        attr.setAttrName(AttrName);
        attr.setAttrType(AttrType.TEXT);

        AttrText attrText = new AttrText();
        attrText.setAttrTextValue(value);
        attrText.setAttrTextRef(attr);
        attr.setAttrText(attrText);
        return attr;
    }

    public Attr generateDateAttr(String AttrName, Date date) {
        Attr attr = new Attr();
        attr.setAttrName(AttrName);
        attr.setAttrType(AttrType.DATE);

        AttrDate attrDate = new AttrDate();
        attrDate.setAttrDateValue(date);
        attrDate.setAttrDateRef(attr);
        attr.setAttrDate(attrDate);

        return attr;
    }

    public void addAttr(AttrGroup ag, Attr attr) {
        AgAttr agAttr = new AgAttr();
        attr.setAgAttr(agAttr);
        persister.persist(attr);
        agAttr.setAgId(ag.getAttrGroupId());
        agAttr.setAttrId(attr);
        persister.persist(agAttr);
    }

    public void addAttr(DBItem item, Attr attr) {
        addAttr(item.getItemAttrGroupId(), attr);
    }

    public void addAttrGroup(DBItem item, AttrGroup ag) {
        AttrGroup ag_parent = item.getItemAttrGroupId();
        addAttrGroup(ag_parent, ag);
    }

    public void addAttrGroup(AttrGroup ag_parent, AttrGroup ag_child) {

        AgAg agag = new AgAg();
        agag.setAgParentId(ag_parent.getAttrGroupId());
        agag.setAgChildId(ag_child);
        ag_child.setAgAg_toParent(agag);
        persister.persist(ag_child);

    }

    public String getStringAttr(DBItem item, String attrName) {
        return getStringAttr(item.getItemAttrGroupId(), attrName);
    }

    public List<AttrGroup> getAGByName(DBItem item, String ag_name) {
        AttrGroup item_ag = item.getItemAttrGroupId();

        return getAGByName(item_ag, ag_name);
    }

    public List<AttrGroup> getAGByName(AttrGroup ag, String ag_name) {
        List<AgAg> agags = em.createNamedQuery("AgAg.findByAgParentId").setParameter("agParentId", ag.getAttrGroupId()).getResultList();
        List<AttrGroup> results = new ArrayList<AttrGroup>();
        for (int i = 0; i < agags.size(); i++) {
            if (agags.get(i).getAgChildId().getAttrGroupName().equals(ag_name)) {
                results.add(agags.get(i).getAgChildId());
            }
        }
        return results;
    }

    public String getStringAttr(AttrGroup ag, String attrName) {
        List<AgAttr> agAttrs = em.createNamedQuery("AgAttr.findByAgId").setParameter("agId", ag.getAttrGroupId()).getResultList();
        for (int i = 0; i < agAttrs.size(); i++) {
            Attr attr = agAttrs.get(i).getAttrId();
            if (attr.getAttrName().equals(attrName)) {
                return attr.getAttrString().getAttrStringValue();
            }
        }
        return null;
    }

    public void destoryAttrGroupByName(DBItem item, String ag_name) {
        destoryAttrGroupByName(item.getItemAttrGroupId(), ag_name);
    }

    public void destoryAttrGroupByName(AttrGroup ag, String ag_name) {
        List<AttrGroup> agByName = getAGByName(ag, ag_name);
        for (int i = 0; i < agByName.size(); i++) {
            destoryAttrGroup(agByName.get(0));
        }
    }
    @Deprecated
    public void detachAttrGroup(AttrGroup ag_parent, AttrGroup ag_child) {
        List<AgAg> agags = em.createNamedQuery("AgAg.findByAgParentId").setParameter("agParentId", ag_parent.getAttrGroupId()).getResultList();
        if(agags.size()==0){
            LOG.log(Level.WARNING,"agags null");
        }
        AgAgJpaController aajc = new AgAgJpaController(utx, emf);
        
        for (int i = 0; i < agags.size(); i++) {
            AgAg agag = agags.get(i);
            LOG.log(Level.WARNING,"agag id"+agag.getAgAgId());
            LOG.log(Level.WARNING,"ag_child id"+ag_child.getAttrGroupId());
            LOG.log(Level.WARNING,"agag.getAgChild id"+agag.getAgChildId().getAttrGroupId());
            if (agag.getAgChildId().getAttrGroupId() == ag_child.getAttrGroupId()) {
                try {
                    aajc.destroy(agag.getAgAgId());
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(GetInfoFeatured.class.getName()).log(Level.SEVERE, null, ex);
                } catch (RollbackFailureException ex) {
                    Logger.getLogger(GetInfoFeatured.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(GetInfoFeatured.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    @Deprecated
    public void detachAttr(AttrGroup ag, Attr attr) {
        List<AgAttr> agAttrs = em.createNamedQuery("AgAttr.findByAgId").setParameter("agId", ag.getAttrGroupId()).getResultList();
        try {
            utx.begin();
            for (int i = 0; i < agAttrs.size(); i++) {
                AgAttr agAttr = agAttrs.get(i);
                if (agAttr.getAttrId().getAttrId() == attr.getAttrId()) {
                    em.detach(agAttr);
                    em.remove(agAttr);
                }
            }
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }

    public void destoryAttrGroup(AttrGroup ag) {
        try {
            AgAg agag = ag.getAgAg_toParent();
            AgAgJpaController aajc = new AgAgJpaController(utx, emf);
            aajc.destroy(agag.getAgAgId());
            AttrGroupJpaController agjc = new AttrGroupJpaController(utx, emf);
            agjc.destroy(ag.getAttrGroupId());
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(GetInfoFeatured.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(GetInfoFeatured.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(GetInfoFeatured.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GetInfoFeatured.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void destoryAttr(Attr attr) {
        try {
            switch (attr.getAttrType()) {
                case AttrType.DATE:
                    AttrDateJpaController adjc = new AttrDateJpaController(utx, emf);
                    adjc.destroy(attr.getAttrDate().getAttrDateId());
                    break;
                case AttrType.INT:
                    AttrIntJpaController aijc = new AttrIntJpaController(utx, emf);
                    aijc.destroy(attr.getAttrInt().getAttrIntId());
                    break;
                case AttrType.STRING:
                    AttrStringJpaController asjc = new AttrStringJpaController(utx, emf);
                    asjc.destroy(attr.getAttrString().getAttrStringId());
                    break;
                case AttrType.TEXT:
                    AttrTextJpaController atcj = new AttrTextJpaController(utx, emf);
                    atcj.destroy(attr.getAttrText().getAttrTextId());
                    break;
                default:
                    break;
            }
            AgAttrJpaController aajc = new AgAttrJpaController(utx, emf);
            aajc.destroy(attr.getAgAttr().getAgAttrId());
            AttrJpaController ajc = new AttrJpaController(utx, emf);
            ajc.destroy(attr.getAttrId());
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(GetInfoFeatured.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(GetInfoFeatured.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(GetInfoFeatured.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GetInfoFeatured.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    public void editAttrString(AttrString attrStr,String value){
        try {
            attrStr.setAttrStringValue(value);
            AttrStringJpaController asjc=new AttrStringJpaController(utx, emf);
            asjc.edit(attrStr);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(GetInfoFeatured.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(GetInfoFeatured.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(GetInfoFeatured.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GetInfoFeatured.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void editAttrText(AttrText attrText, String value){
        try {
            attrText.setAttrTextValue(value);
            AttrTextJpaController atjc=new AttrTextJpaController(utx, emf);
            atjc.edit(attrText);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(GetInfoFeatured.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(GetInfoFeatured.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(GetInfoFeatured.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GetInfoFeatured.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void editAttrInt(AttrInt attrInt, int value){
        try {
            attrInt.setAttrIntValue(value);
            AttrIntJpaController aijc=new AttrIntJpaController(utx, emf);
            aijc.edit(attrInt);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(GetInfoFeatured.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(GetInfoFeatured.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(GetInfoFeatured.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GetInfoFeatured.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
