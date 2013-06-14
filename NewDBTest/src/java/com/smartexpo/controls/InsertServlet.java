/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.controls;

import com.smartexpo.interfaces.Persister;
import com.smartexpo.dbproto.Attr;
import com.smartexpo.dbproto.AttrGroup;
import com.smartexpo.dbproto.AttrString;
import com.smartexpo.models.type.AttrType;
import com.smartexpo.dbproto.Item;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

/**
 *
 * @author tornado718
 */
@WebServlet(name = "InsertServlet", urlPatterns = {"/InsertServlet"})
public class InsertServlet extends HttpServlet implements Persister{
    @PersistenceContext(unitName = "NewDBTestPU")
    private EntityManager em;
    @PersistenceUnit(unitName = "NewDBTestPU")
    EntityManagerFactory emf;
    @Resource
    private javax.transaction.UserTransaction utx;
    private static final Logger LOG = Logger.getLogger(InsertServlet.class.getName());
    private GetInfoFeatured gif;
    
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        gif=new GetInfoFeatured(emf, utx, this);
        
        Item item =new Item();
        item.setItemName(request.getParameter("item_name"));
        gif.addItem(item);
        
        Item item2=new Item();
        item2.setItemName("new Item");
        gif.addItem(item2);
        
        
        Attr imageurl=gif.generateStringAttr("image_url11", "http::///////");
        gif.addAttr(item, imageurl);
        
        String getstring=gif.getStringAttr(item, "image_url11");
        LOG.log(Level.WARNING,"value:"+getstring);
        
        
        AttrGroup author=new AttrGroup();
        author.setAttrGroupName("author");
        Attr authorName=gif.generateStringAttr("authorName", request.getParameter("author_name"));
        Attr authorBirth=gif.generateDateAttr("authorBirthDay", new Date());
        
        gif.addAttrGroup(item, author);
        LOG.log(Level.WARNING,"authorid before:"+ author.getAttrGroupId());
        gif.addAttrGroup(item2, author);
        LOG.log(Level.WARNING,"authorid after:"+ author.getAttrGroupId());
        gif.addAttr(author, authorName);
        gif.addAttr(author, authorBirth);
        List<AttrGroup> getAuthor=gif.getAGByName(item2, "author");
        gif.editAttrString(authorName.getAttrString(),"hahaa");
        //for(int i=0;i<getAuthor.size();i++){
        //    gif.detachAttrGroup(item2.getItemAttrGroupId(), author);
        //}
        
        //LOG.log(Level.WARNING, "getAuthor.name:"+ gif.getStringAttr(getAuthor.get(0), "authorName"));
        
        
        
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    
    @Override
    public void persist(Object object) {
        try {
            utx.begin();
            em.persist(object);
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }
}
