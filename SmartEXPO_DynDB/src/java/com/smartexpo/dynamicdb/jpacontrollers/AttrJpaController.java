/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.dynamicdb.jpacontrollers;

import com.smartexpo.dynamicdb.jpacontrollers.exceptions.IllegalOrphanException;
import com.smartexpo.dynamicdb.jpacontrollers.exceptions.NonexistentEntityException;
import com.smartexpo.dynamicdb.jpacontrollers.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.smartexpo.dynamicdb.models.AttrString;
import com.smartexpo.dynamicdb.models.AttrDate;
import com.smartexpo.dynamicdb.models.AttrText;
import com.smartexpo.dynamicdb.models.AttrInt;
import com.smartexpo.dynamicdb.models.AgAttr;
import com.smartexpo.dynamicdb.models.Attr;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author tornado718
 */
public class AttrJpaController implements Serializable {

    public AttrJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Attr attr) throws RollbackFailureException, Exception {
        if (attr.getAttrStringCollection() == null) {
            attr.setAttrStringCollection(new ArrayList<AttrString>());
        }
        if (attr.getAttrDateCollection() == null) {
            attr.setAttrDateCollection(new ArrayList<AttrDate>());
        }
        if (attr.getAttrTextCollection() == null) {
            attr.setAttrTextCollection(new ArrayList<AttrText>());
        }
        if (attr.getAttrIntCollection() == null) {
            attr.setAttrIntCollection(new ArrayList<AttrInt>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AttrString attrString = attr.getAttrString();
            if (attrString != null) {
                attrString = em.getReference(attrString.getClass(), attrString.getAttrStringId());
                attr.setAttrString(attrString);
            }
            AttrDate attrDate = attr.getAttrDate();
            if (attrDate != null) {
                attrDate = em.getReference(attrDate.getClass(), attrDate.getAttrDateId());
                attr.setAttrDate(attrDate);
            }
            AttrText attrText = attr.getAttrText();
            if (attrText != null) {
                attrText = em.getReference(attrText.getClass(), attrText.getAttrTextId());
                attr.setAttrText(attrText);
            }
            AttrInt attrInt = attr.getAttrInt();
            if (attrInt != null) {
                attrInt = em.getReference(attrInt.getClass(), attrInt.getAttrIntId());
                attr.setAttrInt(attrInt);
            }
            AgAttr agAttr = attr.getAgAttr();
            if (agAttr != null) {
                agAttr = em.getReference(agAttr.getClass(), agAttr.getAgAttrId());
                attr.setAgAttr(agAttr);
            }
            Collection<AttrString> attachedAttrStringCollection = new ArrayList<AttrString>();
            for (AttrString attrStringCollectionAttrStringToAttach : attr.getAttrStringCollection()) {
                attrStringCollectionAttrStringToAttach = em.getReference(attrStringCollectionAttrStringToAttach.getClass(), attrStringCollectionAttrStringToAttach.getAttrStringId());
                attachedAttrStringCollection.add(attrStringCollectionAttrStringToAttach);
            }
            attr.setAttrStringCollection(attachedAttrStringCollection);
            Collection<AttrDate> attachedAttrDateCollection = new ArrayList<AttrDate>();
            for (AttrDate attrDateCollectionAttrDateToAttach : attr.getAttrDateCollection()) {
                attrDateCollectionAttrDateToAttach = em.getReference(attrDateCollectionAttrDateToAttach.getClass(), attrDateCollectionAttrDateToAttach.getAttrDateId());
                attachedAttrDateCollection.add(attrDateCollectionAttrDateToAttach);
            }
            attr.setAttrDateCollection(attachedAttrDateCollection);
            Collection<AttrText> attachedAttrTextCollection = new ArrayList<AttrText>();
            for (AttrText attrTextCollectionAttrTextToAttach : attr.getAttrTextCollection()) {
                attrTextCollectionAttrTextToAttach = em.getReference(attrTextCollectionAttrTextToAttach.getClass(), attrTextCollectionAttrTextToAttach.getAttrTextId());
                attachedAttrTextCollection.add(attrTextCollectionAttrTextToAttach);
            }
            attr.setAttrTextCollection(attachedAttrTextCollection);
            Collection<AttrInt> attachedAttrIntCollection = new ArrayList<AttrInt>();
            for (AttrInt attrIntCollectionAttrIntToAttach : attr.getAttrIntCollection()) {
                attrIntCollectionAttrIntToAttach = em.getReference(attrIntCollectionAttrIntToAttach.getClass(), attrIntCollectionAttrIntToAttach.getAttrIntId());
                attachedAttrIntCollection.add(attrIntCollectionAttrIntToAttach);
            }
            attr.setAttrIntCollection(attachedAttrIntCollection);
            em.persist(attr);
            if (attrString != null) {
                Attr oldAttrStringRefOfAttrString = attrString.getAttrStringRef();
                if (oldAttrStringRefOfAttrString != null) {
                    oldAttrStringRefOfAttrString.setAttrString(null);
                    oldAttrStringRefOfAttrString = em.merge(oldAttrStringRefOfAttrString);
                }
                attrString.setAttrStringRef(attr);
                attrString = em.merge(attrString);
            }
            if (attrDate != null) {
                Attr oldAttrDateRefOfAttrDate = attrDate.getAttrDateRef();
                if (oldAttrDateRefOfAttrDate != null) {
                    oldAttrDateRefOfAttrDate.setAttrDate(null);
                    oldAttrDateRefOfAttrDate = em.merge(oldAttrDateRefOfAttrDate);
                }
                attrDate.setAttrDateRef(attr);
                attrDate = em.merge(attrDate);
            }
            if (attrText != null) {
                Attr oldAttrTextRefOfAttrText = attrText.getAttrTextRef();
                if (oldAttrTextRefOfAttrText != null) {
                    oldAttrTextRefOfAttrText.setAttrText(null);
                    oldAttrTextRefOfAttrText = em.merge(oldAttrTextRefOfAttrText);
                }
                attrText.setAttrTextRef(attr);
                attrText = em.merge(attrText);
            }
            if (attrInt != null) {
                Attr oldAttrIntRefOfAttrInt = attrInt.getAttrIntRef();
                if (oldAttrIntRefOfAttrInt != null) {
                    oldAttrIntRefOfAttrInt.setAttrInt(null);
                    oldAttrIntRefOfAttrInt = em.merge(oldAttrIntRefOfAttrInt);
                }
                attrInt.setAttrIntRef(attr);
                attrInt = em.merge(attrInt);
            }
            if (agAttr != null) {
                Attr oldAttrIdOfAgAttr = agAttr.getAttrId();
                if (oldAttrIdOfAgAttr != null) {
                    oldAttrIdOfAgAttr.setAgAttr(null);
                    oldAttrIdOfAgAttr = em.merge(oldAttrIdOfAgAttr);
                }
                agAttr.setAttrId(attr);
                agAttr = em.merge(agAttr);
            }
            for (AttrString attrStringCollectionAttrString : attr.getAttrStringCollection()) {
                Attr oldAttrStringRefOfAttrStringCollectionAttrString = attrStringCollectionAttrString.getAttrStringRef();
                attrStringCollectionAttrString.setAttrStringRef(attr);
                attrStringCollectionAttrString = em.merge(attrStringCollectionAttrString);
                if (oldAttrStringRefOfAttrStringCollectionAttrString != null) {
                    oldAttrStringRefOfAttrStringCollectionAttrString.getAttrStringCollection().remove(attrStringCollectionAttrString);
                    oldAttrStringRefOfAttrStringCollectionAttrString = em.merge(oldAttrStringRefOfAttrStringCollectionAttrString);
                }
            }
            for (AttrDate attrDateCollectionAttrDate : attr.getAttrDateCollection()) {
                Attr oldAttrDateRefOfAttrDateCollectionAttrDate = attrDateCollectionAttrDate.getAttrDateRef();
                attrDateCollectionAttrDate.setAttrDateRef(attr);
                attrDateCollectionAttrDate = em.merge(attrDateCollectionAttrDate);
                if (oldAttrDateRefOfAttrDateCollectionAttrDate != null) {
                    oldAttrDateRefOfAttrDateCollectionAttrDate.getAttrDateCollection().remove(attrDateCollectionAttrDate);
                    oldAttrDateRefOfAttrDateCollectionAttrDate = em.merge(oldAttrDateRefOfAttrDateCollectionAttrDate);
                }
            }
            for (AttrText attrTextCollectionAttrText : attr.getAttrTextCollection()) {
                Attr oldAttrTextRefOfAttrTextCollectionAttrText = attrTextCollectionAttrText.getAttrTextRef();
                attrTextCollectionAttrText.setAttrTextRef(attr);
                attrTextCollectionAttrText = em.merge(attrTextCollectionAttrText);
                if (oldAttrTextRefOfAttrTextCollectionAttrText != null) {
                    oldAttrTextRefOfAttrTextCollectionAttrText.getAttrTextCollection().remove(attrTextCollectionAttrText);
                    oldAttrTextRefOfAttrTextCollectionAttrText = em.merge(oldAttrTextRefOfAttrTextCollectionAttrText);
                }
            }
            for (AttrInt attrIntCollectionAttrInt : attr.getAttrIntCollection()) {
                Attr oldAttrIntRefOfAttrIntCollectionAttrInt = attrIntCollectionAttrInt.getAttrIntRef();
                attrIntCollectionAttrInt.setAttrIntRef(attr);
                attrIntCollectionAttrInt = em.merge(attrIntCollectionAttrInt);
                if (oldAttrIntRefOfAttrIntCollectionAttrInt != null) {
                    oldAttrIntRefOfAttrIntCollectionAttrInt.getAttrIntCollection().remove(attrIntCollectionAttrInt);
                    oldAttrIntRefOfAttrIntCollectionAttrInt = em.merge(oldAttrIntRefOfAttrIntCollectionAttrInt);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Attr attr) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Attr persistentAttr = em.find(Attr.class, attr.getAttrId());
            AttrString attrStringOld = persistentAttr.getAttrString();
            AttrString attrStringNew = attr.getAttrString();
            AttrDate attrDateOld = persistentAttr.getAttrDate();
            AttrDate attrDateNew = attr.getAttrDate();
            AttrText attrTextOld = persistentAttr.getAttrText();
            AttrText attrTextNew = attr.getAttrText();
            AttrInt attrIntOld = persistentAttr.getAttrInt();
            AttrInt attrIntNew = attr.getAttrInt();
            AgAttr agAttrOld = persistentAttr.getAgAttr();
            AgAttr agAttrNew = attr.getAgAttr();
            Collection<AttrString> attrStringCollectionOld = persistentAttr.getAttrStringCollection();
            Collection<AttrString> attrStringCollectionNew = attr.getAttrStringCollection();
            Collection<AttrDate> attrDateCollectionOld = persistentAttr.getAttrDateCollection();
            Collection<AttrDate> attrDateCollectionNew = attr.getAttrDateCollection();
            Collection<AttrText> attrTextCollectionOld = persistentAttr.getAttrTextCollection();
            Collection<AttrText> attrTextCollectionNew = attr.getAttrTextCollection();
            Collection<AttrInt> attrIntCollectionOld = persistentAttr.getAttrIntCollection();
            Collection<AttrInt> attrIntCollectionNew = attr.getAttrIntCollection();
            List<String> illegalOrphanMessages = null;
            if (attrStringOld != null && !attrStringOld.equals(attrStringNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain AttrString " + attrStringOld + " since its attrStringRef field is not nullable.");
            }
            if (attrDateOld != null && !attrDateOld.equals(attrDateNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain AttrDate " + attrDateOld + " since its attrDateRef field is not nullable.");
            }
            if (attrTextOld != null && !attrTextOld.equals(attrTextNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain AttrText " + attrTextOld + " since its attrTextRef field is not nullable.");
            }
            if (attrIntOld != null && !attrIntOld.equals(attrIntNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain AttrInt " + attrIntOld + " since its attrIntRef field is not nullable.");
            }
            if (agAttrOld != null && !agAttrOld.equals(agAttrNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain AgAttr " + agAttrOld + " since its attrId field is not nullable.");
            }
            for (AttrString attrStringCollectionOldAttrString : attrStringCollectionOld) {
                if (!attrStringCollectionNew.contains(attrStringCollectionOldAttrString)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AttrString " + attrStringCollectionOldAttrString + " since its attrStringRef field is not nullable.");
                }
            }
            for (AttrDate attrDateCollectionOldAttrDate : attrDateCollectionOld) {
                if (!attrDateCollectionNew.contains(attrDateCollectionOldAttrDate)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AttrDate " + attrDateCollectionOldAttrDate + " since its attrDateRef field is not nullable.");
                }
            }
            for (AttrText attrTextCollectionOldAttrText : attrTextCollectionOld) {
                if (!attrTextCollectionNew.contains(attrTextCollectionOldAttrText)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AttrText " + attrTextCollectionOldAttrText + " since its attrTextRef field is not nullable.");
                }
            }
            for (AttrInt attrIntCollectionOldAttrInt : attrIntCollectionOld) {
                if (!attrIntCollectionNew.contains(attrIntCollectionOldAttrInt)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AttrInt " + attrIntCollectionOldAttrInt + " since its attrIntRef field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (attrStringNew != null) {
                attrStringNew = em.getReference(attrStringNew.getClass(), attrStringNew.getAttrStringId());
                attr.setAttrString(attrStringNew);
            }
            if (attrDateNew != null) {
                attrDateNew = em.getReference(attrDateNew.getClass(), attrDateNew.getAttrDateId());
                attr.setAttrDate(attrDateNew);
            }
            if (attrTextNew != null) {
                attrTextNew = em.getReference(attrTextNew.getClass(), attrTextNew.getAttrTextId());
                attr.setAttrText(attrTextNew);
            }
            if (attrIntNew != null) {
                attrIntNew = em.getReference(attrIntNew.getClass(), attrIntNew.getAttrIntId());
                attr.setAttrInt(attrIntNew);
            }
            if (agAttrNew != null) {
                agAttrNew = em.getReference(agAttrNew.getClass(), agAttrNew.getAgAttrId());
                attr.setAgAttr(agAttrNew);
            }
            Collection<AttrString> attachedAttrStringCollectionNew = new ArrayList<AttrString>();
            for (AttrString attrStringCollectionNewAttrStringToAttach : attrStringCollectionNew) {
                attrStringCollectionNewAttrStringToAttach = em.getReference(attrStringCollectionNewAttrStringToAttach.getClass(), attrStringCollectionNewAttrStringToAttach.getAttrStringId());
                attachedAttrStringCollectionNew.add(attrStringCollectionNewAttrStringToAttach);
            }
            attrStringCollectionNew = attachedAttrStringCollectionNew;
            attr.setAttrStringCollection(attrStringCollectionNew);
            Collection<AttrDate> attachedAttrDateCollectionNew = new ArrayList<AttrDate>();
            for (AttrDate attrDateCollectionNewAttrDateToAttach : attrDateCollectionNew) {
                attrDateCollectionNewAttrDateToAttach = em.getReference(attrDateCollectionNewAttrDateToAttach.getClass(), attrDateCollectionNewAttrDateToAttach.getAttrDateId());
                attachedAttrDateCollectionNew.add(attrDateCollectionNewAttrDateToAttach);
            }
            attrDateCollectionNew = attachedAttrDateCollectionNew;
            attr.setAttrDateCollection(attrDateCollectionNew);
            Collection<AttrText> attachedAttrTextCollectionNew = new ArrayList<AttrText>();
            for (AttrText attrTextCollectionNewAttrTextToAttach : attrTextCollectionNew) {
                attrTextCollectionNewAttrTextToAttach = em.getReference(attrTextCollectionNewAttrTextToAttach.getClass(), attrTextCollectionNewAttrTextToAttach.getAttrTextId());
                attachedAttrTextCollectionNew.add(attrTextCollectionNewAttrTextToAttach);
            }
            attrTextCollectionNew = attachedAttrTextCollectionNew;
            attr.setAttrTextCollection(attrTextCollectionNew);
            Collection<AttrInt> attachedAttrIntCollectionNew = new ArrayList<AttrInt>();
            for (AttrInt attrIntCollectionNewAttrIntToAttach : attrIntCollectionNew) {
                attrIntCollectionNewAttrIntToAttach = em.getReference(attrIntCollectionNewAttrIntToAttach.getClass(), attrIntCollectionNewAttrIntToAttach.getAttrIntId());
                attachedAttrIntCollectionNew.add(attrIntCollectionNewAttrIntToAttach);
            }
            attrIntCollectionNew = attachedAttrIntCollectionNew;
            attr.setAttrIntCollection(attrIntCollectionNew);
            attr = em.merge(attr);
            if (attrStringNew != null && !attrStringNew.equals(attrStringOld)) {
                Attr oldAttrStringRefOfAttrString = attrStringNew.getAttrStringRef();
                if (oldAttrStringRefOfAttrString != null) {
                    oldAttrStringRefOfAttrString.setAttrString(null);
                    oldAttrStringRefOfAttrString = em.merge(oldAttrStringRefOfAttrString);
                }
                attrStringNew.setAttrStringRef(attr);
                attrStringNew = em.merge(attrStringNew);
            }
            if (attrDateNew != null && !attrDateNew.equals(attrDateOld)) {
                Attr oldAttrDateRefOfAttrDate = attrDateNew.getAttrDateRef();
                if (oldAttrDateRefOfAttrDate != null) {
                    oldAttrDateRefOfAttrDate.setAttrDate(null);
                    oldAttrDateRefOfAttrDate = em.merge(oldAttrDateRefOfAttrDate);
                }
                attrDateNew.setAttrDateRef(attr);
                attrDateNew = em.merge(attrDateNew);
            }
            if (attrTextNew != null && !attrTextNew.equals(attrTextOld)) {
                Attr oldAttrTextRefOfAttrText = attrTextNew.getAttrTextRef();
                if (oldAttrTextRefOfAttrText != null) {
                    oldAttrTextRefOfAttrText.setAttrText(null);
                    oldAttrTextRefOfAttrText = em.merge(oldAttrTextRefOfAttrText);
                }
                attrTextNew.setAttrTextRef(attr);
                attrTextNew = em.merge(attrTextNew);
            }
            if (attrIntNew != null && !attrIntNew.equals(attrIntOld)) {
                Attr oldAttrIntRefOfAttrInt = attrIntNew.getAttrIntRef();
                if (oldAttrIntRefOfAttrInt != null) {
                    oldAttrIntRefOfAttrInt.setAttrInt(null);
                    oldAttrIntRefOfAttrInt = em.merge(oldAttrIntRefOfAttrInt);
                }
                attrIntNew.setAttrIntRef(attr);
                attrIntNew = em.merge(attrIntNew);
            }
            if (agAttrNew != null && !agAttrNew.equals(agAttrOld)) {
                Attr oldAttrIdOfAgAttr = agAttrNew.getAttrId();
                if (oldAttrIdOfAgAttr != null) {
                    oldAttrIdOfAgAttr.setAgAttr(null);
                    oldAttrIdOfAgAttr = em.merge(oldAttrIdOfAgAttr);
                }
                agAttrNew.setAttrId(attr);
                agAttrNew = em.merge(agAttrNew);
            }
            for (AttrString attrStringCollectionNewAttrString : attrStringCollectionNew) {
                if (!attrStringCollectionOld.contains(attrStringCollectionNewAttrString)) {
                    Attr oldAttrStringRefOfAttrStringCollectionNewAttrString = attrStringCollectionNewAttrString.getAttrStringRef();
                    attrStringCollectionNewAttrString.setAttrStringRef(attr);
                    attrStringCollectionNewAttrString = em.merge(attrStringCollectionNewAttrString);
                    if (oldAttrStringRefOfAttrStringCollectionNewAttrString != null && !oldAttrStringRefOfAttrStringCollectionNewAttrString.equals(attr)) {
                        oldAttrStringRefOfAttrStringCollectionNewAttrString.getAttrStringCollection().remove(attrStringCollectionNewAttrString);
                        oldAttrStringRefOfAttrStringCollectionNewAttrString = em.merge(oldAttrStringRefOfAttrStringCollectionNewAttrString);
                    }
                }
            }
            for (AttrDate attrDateCollectionNewAttrDate : attrDateCollectionNew) {
                if (!attrDateCollectionOld.contains(attrDateCollectionNewAttrDate)) {
                    Attr oldAttrDateRefOfAttrDateCollectionNewAttrDate = attrDateCollectionNewAttrDate.getAttrDateRef();
                    attrDateCollectionNewAttrDate.setAttrDateRef(attr);
                    attrDateCollectionNewAttrDate = em.merge(attrDateCollectionNewAttrDate);
                    if (oldAttrDateRefOfAttrDateCollectionNewAttrDate != null && !oldAttrDateRefOfAttrDateCollectionNewAttrDate.equals(attr)) {
                        oldAttrDateRefOfAttrDateCollectionNewAttrDate.getAttrDateCollection().remove(attrDateCollectionNewAttrDate);
                        oldAttrDateRefOfAttrDateCollectionNewAttrDate = em.merge(oldAttrDateRefOfAttrDateCollectionNewAttrDate);
                    }
                }
            }
            for (AttrText attrTextCollectionNewAttrText : attrTextCollectionNew) {
                if (!attrTextCollectionOld.contains(attrTextCollectionNewAttrText)) {
                    Attr oldAttrTextRefOfAttrTextCollectionNewAttrText = attrTextCollectionNewAttrText.getAttrTextRef();
                    attrTextCollectionNewAttrText.setAttrTextRef(attr);
                    attrTextCollectionNewAttrText = em.merge(attrTextCollectionNewAttrText);
                    if (oldAttrTextRefOfAttrTextCollectionNewAttrText != null && !oldAttrTextRefOfAttrTextCollectionNewAttrText.equals(attr)) {
                        oldAttrTextRefOfAttrTextCollectionNewAttrText.getAttrTextCollection().remove(attrTextCollectionNewAttrText);
                        oldAttrTextRefOfAttrTextCollectionNewAttrText = em.merge(oldAttrTextRefOfAttrTextCollectionNewAttrText);
                    }
                }
            }
            for (AttrInt attrIntCollectionNewAttrInt : attrIntCollectionNew) {
                if (!attrIntCollectionOld.contains(attrIntCollectionNewAttrInt)) {
                    Attr oldAttrIntRefOfAttrIntCollectionNewAttrInt = attrIntCollectionNewAttrInt.getAttrIntRef();
                    attrIntCollectionNewAttrInt.setAttrIntRef(attr);
                    attrIntCollectionNewAttrInt = em.merge(attrIntCollectionNewAttrInt);
                    if (oldAttrIntRefOfAttrIntCollectionNewAttrInt != null && !oldAttrIntRefOfAttrIntCollectionNewAttrInt.equals(attr)) {
                        oldAttrIntRefOfAttrIntCollectionNewAttrInt.getAttrIntCollection().remove(attrIntCollectionNewAttrInt);
                        oldAttrIntRefOfAttrIntCollectionNewAttrInt = em.merge(oldAttrIntRefOfAttrIntCollectionNewAttrInt);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = attr.getAttrId();
                if (findAttr(id) == null) {
                    throw new NonexistentEntityException("The attr with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Attr attr;
            try {
                attr = em.getReference(Attr.class, id);
                attr.getAttrId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The attr with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            AttrString attrStringOrphanCheck = attr.getAttrString();
            if (attrStringOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Attr (" + attr + ") cannot be destroyed since the AttrString " + attrStringOrphanCheck + " in its attrString field has a non-nullable attrStringRef field.");
            }
            AttrDate attrDateOrphanCheck = attr.getAttrDate();
            if (attrDateOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Attr (" + attr + ") cannot be destroyed since the AttrDate " + attrDateOrphanCheck + " in its attrDate field has a non-nullable attrDateRef field.");
            }
            AttrText attrTextOrphanCheck = attr.getAttrText();
            if (attrTextOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Attr (" + attr + ") cannot be destroyed since the AttrText " + attrTextOrphanCheck + " in its attrText field has a non-nullable attrTextRef field.");
            }
            AttrInt attrIntOrphanCheck = attr.getAttrInt();
            if (attrIntOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Attr (" + attr + ") cannot be destroyed since the AttrInt " + attrIntOrphanCheck + " in its attrInt field has a non-nullable attrIntRef field.");
            }
            AgAttr agAttrOrphanCheck = attr.getAgAttr();
            if (agAttrOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Attr (" + attr + ") cannot be destroyed since the AgAttr " + agAttrOrphanCheck + " in its agAttr field has a non-nullable attrId field.");
            }
            Collection<AttrString> attrStringCollectionOrphanCheck = attr.getAttrStringCollection();
            for (AttrString attrStringCollectionOrphanCheckAttrString : attrStringCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Attr (" + attr + ") cannot be destroyed since the AttrString " + attrStringCollectionOrphanCheckAttrString + " in its attrStringCollection field has a non-nullable attrStringRef field.");
            }
            Collection<AttrDate> attrDateCollectionOrphanCheck = attr.getAttrDateCollection();
            for (AttrDate attrDateCollectionOrphanCheckAttrDate : attrDateCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Attr (" + attr + ") cannot be destroyed since the AttrDate " + attrDateCollectionOrphanCheckAttrDate + " in its attrDateCollection field has a non-nullable attrDateRef field.");
            }
            Collection<AttrText> attrTextCollectionOrphanCheck = attr.getAttrTextCollection();
            for (AttrText attrTextCollectionOrphanCheckAttrText : attrTextCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Attr (" + attr + ") cannot be destroyed since the AttrText " + attrTextCollectionOrphanCheckAttrText + " in its attrTextCollection field has a non-nullable attrTextRef field.");
            }
            Collection<AttrInt> attrIntCollectionOrphanCheck = attr.getAttrIntCollection();
            for (AttrInt attrIntCollectionOrphanCheckAttrInt : attrIntCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Attr (" + attr + ") cannot be destroyed since the AttrInt " + attrIntCollectionOrphanCheckAttrInt + " in its attrIntCollection field has a non-nullable attrIntRef field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(attr);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Attr> findAttrEntities() {
        return findAttrEntities(true, -1, -1);
    }

    public List<Attr> findAttrEntities(int maxResults, int firstResult) {
        return findAttrEntities(false, maxResults, firstResult);
    }

    private List<Attr> findAttrEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Attr.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Attr findAttr(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Attr.class, id);
        } finally {
            em.close();
        }
    }

    public int getAttrCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Attr> rt = cq.from(Attr.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
