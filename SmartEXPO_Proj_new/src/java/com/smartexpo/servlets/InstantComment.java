/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.servlets;

import com.smartexpo.managedbean.OverallInfo;
import com.smartexpo.models.CommentInfo;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.bean.ManagedProperty;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ben
 */
@WebServlet(name = "InstantComment", urlPatterns = {"/InstantComment"})
public class InstantComment extends HttpServlet {

//    @ManagedProperty(value = "#{overallInfo}")
    private OverallInfo overallInfo;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm z");

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
//        System.out.println("$$$$$$$$$$$$$$$$" + request.getSession().getServletContext().getAttribute("overallInfo"));
        
        overallInfo = (OverallInfo)request.getSession().getServletContext().getAttribute("overallInfo");
        
        response.setContentType("text/event-stream");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        String id = null;
        String data = null;
        CommentInfo[] infos = overallInfo.getComment();
//        System.out.println(infos.length + "$$");
        if (infos.length == 1) {
            id = Integer.toString(infos[0].getId());
            data = parseJSON(infos[0]);
//            System.out.println("@@@@@  " + data + "  " + id);
        } else if (infos.length > 1) {
            id = "#";
            data = getInfo(infos);
        } else {
            id = null;
            data = null;
        }
        out.println("id: " + id);
        out.println("data: " + data);
        out.println("retry: 500");
        out.println();
        out.flush();
        out.close();

    }

    private String parseJSON(CommentInfo info) {
        String json = "{\"username\":\""+info.getUsername()
                +"\", \"time\":\"" + sdf.format(info.getTime()) 
                + "\", \"content\":\""+info.getContent()
                +"\", \"num\":" + info.getNum()
                + "}";
        return json;
    }
    
    private String getInfo(CommentInfo[] info) {
        String json = "[";
        for (int i = 0; i < info.length; i++) {
            json += parseJSON(info[i]);
            if (i != info.length - 1)
                json += ",";
        }
        json += "]";
        return json;
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
}
