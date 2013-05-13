/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.controls;

import com.smartexpo.models.Description;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author tornado718
 */
@Stateless
public class DescriptionFacade extends AbstractFacade<Description> {
    @PersistenceContext(unitName = "SmartEXPO_ProjectPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DescriptionFacade() {
        super(Description.class);
    }
    
}
