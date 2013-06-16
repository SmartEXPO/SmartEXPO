/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.concretemodel;

import com.smartexpo.controls.GetInfoFeatured;
import com.smartexpo.dbproto.Attr;
import com.smartexpo.dbproto.AttrGroup;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tornado718
 */
public class AttrGroupModel {

    private static final Logger LOG = Logger.getLogger(AttrGroupModel.class.getName());
    protected AttrGroup self;

    public AttrGroup getSelf() {
        return self;
    }
    protected List<AttrGroupModel> attrGroupModels;
    protected List<AttrGroup> attrGroups;
    protected List<Attr> attrs;
    protected boolean created = false;
    
    
    public void setAttrGroups(List<AttrGroup> attrGroups){
        this.attrGroups=attrGroups;
    }
    public void setAttrs(List<Attr> attrs){
        this.attrs=attrs;
    }

    public AttrGroupModel() {
        this.attrGroupModels = new ArrayList<AttrGroupModel>();
        this.attrGroups = new ArrayList<AttrGroup>();
        this.attrs = new ArrayList<Attr>();

        this.self = new AttrGroup();
        this.self.setAttrGroupName(this.getClass().getName());

//        Field[] fields = this.getClass().getDeclaredFields();
//        for (int i = 0; i < fields.length; i++) {
//            Class<?> c = fields[i].getType();
//            if (c.equals(Integer.TYPE) || c.equals(Integer.class)) {
//                LOG.log(Level.WARNING, "integer field:" + fields[i].toString());
//            } else if (c.equals(String.class)) {
//                LOG.log(Level.WARNING, "String field:" + fields[i].toString());
//            } else if (c.equals(List.class)){
//                LOG.log(Level.WARNING, "List field:" + fields[i].getGenericType());
//            } 
//            
//        }


    }

    public List<AttrGroupModel> getAttrGroupModels() {
        return attrGroupModels;
    }

    public List<AttrGroup> getAttrGroups() {
        return attrGroups;
    }

    public List<Attr> getAttrs() {
        return attrs;
    }

    public int getAttrGroupId() {
        return self.getAttrGroupId();
    }

    private Attr getAttr(Field f) throws IllegalArgumentException, IllegalAccessException {
        Attr attr = null;
        Class<?> c = f.getType();
        Object value = f.get(this);
        if (value == null) {
            return null;
        }
        if (c.equals(Integer.TYPE) || c.equals(Integer.class)) {
            //LOG.log(Level.WARNING, "integer field:" + f.toString() + "name:" + f.getName() + "value:" + (Integer) (f.get(this)));
            attr = GetInfoFeatured.generateIntAttr(f.getName(), (Integer) (f.get(this)));
        } else if (c.equals(String.class)) {
            //LOG.log(Level.WARNING, "String field:" + f.toString() + "name:" + f.getName() + "value:" + (String) (f.get(this)));
            attr = GetInfoFeatured.generateTextAttr(f.getName(), (String) (f.get(this)));
        } else if (c.equals(Date.class)) {
            //LOG.log(Level.WARNING, "DATE field:" + f.toString());
            attr = GetInfoFeatured.generateDateAttr(f.getName(), (Date) (f.get(this)));
        }

        return attr;
    }

    private void updateAttr(Field f, GetInfoFeatured gif) throws IllegalArgumentException, IllegalAccessException {
        Attr attr = null;
        if(f.get(this)==null){
            return;
        }
        if(f.getType().getName().equals(ArrayList.class.getName())){
            List<Object> list = (ArrayList<Object>) (f.get(this));
            if(list == null || list.isEmpty()){
                return ;
            }
            
            AttrGroupModel agm=null;
            for(int i=0;i<list.size();i++){
                agm=(AttrGroupModel)list.get(i);
                if(agm==null){
                    continue;
                }
                if (agm instanceof AttrGroupModel) {
                    agm.edit(gif);
                }
            }
        }
        
        
        for (int i = 0; i < attrs.size(); i++) {
            if (attrs.get(i).getAttrName().equals(f.getName())) {
                
                attr = attrs.get(i);
                LOG.log(Level.WARNING,"attr.getName"+ attr.getAttrName());
                Class<?> c = f.getType();
                if (c.equals(Integer.TYPE) || c.equals(Integer.class)) {
                    LOG.log(Level.WARNING,"attr int:"+ attr.getAttrInt());
                    gif.editAttrInt(attr.getAttrInt(), f.getInt(this));
                } else if (c.equals(String.class)) {
                    LOG.log(Level.WARNING,"attr string:"+ attr.getAttrString());
                    gif.editAttrText(attr.getAttrText(), (String) (f.get(this)));
                } else if (c.equals(Date.class)) {
                    LOG.log(Level.WARNING,"attr date:"+ attr.getAttrDate());
                    gif.editAttrDate(attr.getAttrDate(), (Date) (f.get(this)));
                }
                return;
            }
        }

    }

    public void create(GetInfoFeatured gif) {
        if (created) {
            return;
        }


        if (self.getAttrGroupId() == null) {
            LOG.log(Level.WARNING, "gif.addAttrGroup");
            gif.addAttrGroup(self);
        }

        Field[] fields = this.getClass().getDeclaredFields();

        try {
            for (int i = 0; i < fields.length; i++) {
                Field f = fields[i];
                if (f.getType().equals(ArrayList.class)) {
                    LOG.log(Level.WARNING, "list!!!");
                    //a collaction of attrGroups;
                    List<Object> list = (ArrayList<Object>) (fields[i].get(this));

                    if (list == null || list.isEmpty()) {
                        continue;
                    }
                    LOG.log(Level.WARNING, list.get(0).getClass().getName());
                    if (list.get(0) instanceof AttrGroupModel) {
                        LOG.log(Level.WARNING, "AttrGroupModel!!!!");
                        for (int j = 0; j < list.size(); j++) {
                            AttrGroupModel agm = (AttrGroupModel) (list.get(j));
                            AttrGroup ag = agm.getSelf();
                            ag.setAttrGroupName(f.getName());
                            attrGroups.add(ag);
                            if (agm.created == false) {
                                agm.create(gif);
                            }
                            gif.addAgAgAttach(self, ag);
                            //gif.addAttrGroup(self, ag);
                            attrGroups.add(agm.self);

                        }
                    } else {
                        //.. to be implemented 
                    }

                    //LOG.log(Level.WARNING,f.getGenericType().toString());
                } else {
                    //an attribute;
                    //LOG.log(Level.WARNING,"attr");
                    Attr attr = getAttr(f);
                    if (attr == null) {
                        //LOG.log(Level.WARNING, "attr null!");
                    } else {
                        //LOG.log(Level.WARNING, "attr:" + attr.toString());
                        attrs.add(attr);
                        gif.addAttr(self, attr);
                    }
                }
            }


        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }

        created = true;
    }

    public void edit(GetInfoFeatured gif) {
        Field[] fields = this.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            try {
                updateAttr(fields[i], gif);
                
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(AttrGroupModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(AttrGroupModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    public void delete(GetInfoFeatured gif) {
        gif.destoryAttrGroup(self);
    }
    
    
    
    public static AttrGroupModel restoreAttrGroupModel(GetInfoFeatured gif,int attrGroupId){
        AttrGroupModel agm=new AttrGroupModel();
        agm.setAttrGroups(gif.getAllAttrGroupByAttrGroupId(attrGroupId));
        agm.setAttrs(gif.getAllAttrByAttrGroupId(attrGroupId));
        return agm;
    }
    
    
    public Attr getAttrByName(String name){
        for(int i=0;i<attrs.size();i++){
            if(attrs.get(i).getAttrName().equals(name)){
                return attrs.get(i);
            }
        }
        return null;
    }
    
    public List<Attr> getAttrsByName(String name){
        ArrayList<Attr> attrs=new ArrayList<Attr>();
        for(int i=0;i<attrs.size();i++){
            if(attrs.get(i).getAttrName().equals(name)){
                attrs.add(attrs.get(i));
            }
        }
        return attrs;
    }
    
    public AttrGroup getAttrGroupByName(String name){
        for(int i=0;i<attrGroups.size();i++){
            if(attrGroups.get(i).getAttrGroupName().equals(name)){
                return attrGroups.get(i);
            }
        }
        return null;
    }
    
    public List<AttrGroup> getAttrGroupsByName(String name){
        ArrayList<AttrGroup> attrGroups=new ArrayList<AttrGroup>();
        for(int i=0;i<attrGroups.size();i++){
            if(attrGroups.get(i).getAttrGroupName().equals(name)){
                attrGroups.add(attrGroups.get(i));
            }
        }
        
        return attrGroups;
    }
    
    
    public void setItem(GetInfoFeatured gif){
        gif.AttachItem(self);
    }
    
}
