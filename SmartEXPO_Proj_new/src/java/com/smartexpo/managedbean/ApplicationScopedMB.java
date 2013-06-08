/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.managedbean;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author tornado718
 */
@ManagedBean
@ApplicationScoped
public class ApplicationScopedMB {

    private static final Logger LOG = Logger.getLogger(ApplicationScopedMB.class.getName());

    /**
     * Creates a new instance of ApplicationScopedMB
     */
    public ApplicationScopedMB() {
        LOG.log(Level.WARNING, "hello!! Im alive!!");
    }
}
