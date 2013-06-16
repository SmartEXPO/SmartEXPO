/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.util.filter;

import com.smartexpo.controls.GetInfo;
import com.smartexpo.managedbean.LoginManagedBean;
import com.smartexpo.managedbean.OverallInfo;
import com.smartexpo.models.Manager;
import com.smartexpo.models.Sessioninfo;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

/**
 *
 * @author Boy
 */
public class AutoLoginFilter implements Filter {

    @PersistenceContext(unitName = "SmartEXPO_ProjPU")
    EntityManager em;
    @PersistenceUnit(unitName = "SmartEXPO_ProjPU")
    EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;
    private GetInfo gi;
    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    private List<String> notCheckURLList = new ArrayList<String>();
    private String sessionKey = null;

    public AutoLoginFilter() {
    }

    @PostConstruct
    public void postConstruct() {
        gi = new GetInfo(emf, utx);
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {

        String notCheckURLListStr = filterConfig.getInitParameter("notCheckURLList");
        sessionKey = filterConfig.getInitParameter("checkSessionKey");

        if (notCheckURLList != null) {
            StringTokenizer st = new StringTokenizer(notCheckURLListStr, ";");
            notCheckURLList.clear();
            while (st.hasMoreTokens()) {
                notCheckURLList.add("/faces/admin/" + st.nextToken() + ".xhtml");
            }
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.

        // For example, a logging filter might log items on the request object,
        // such as the parameters.
	/*
         for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
         String name = (String)en.nextElement();
         String values[] = request.getParameterValues(name);
         int n = values.length;
         StringBuffer buf = new StringBuffer();
         buf.append(name);
         buf.append("=");
         for(int i=0; i < n; i++) {
         buf.append(values[i]);
         if (i < n-1)
         buf.append(",");
         }
         log(buf.toString());
         }
         */
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
	/*
         for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
         String name = (String)en.nextElement();
         Object value = request.getAttribute(name);
         log("attribute: " + name + "=" + value.toString());

         }
         */
        // For example, a filter might append something to the response.
	/*
         PrintWriter respOut = new PrintWriter(response.getWriter());
         respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        doBeforeProcessing(request, response);


        // Filter begin
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse res = (HttpServletResponse) response;
//        HttpSession session = req.getSession(true);
//
//        if ((!isURLNotInFilterList(req)) && session.getAttribute(sessionKey) == null) {
//            return;
//        }
//
//        String username = null;
//        String sessionid; // last sessionid, not current
//        Cookie[] cookies;
//        boolean isAutoLogin = false;
//
//        String user = (String) session.getAttribute(sessionKey);
//        if (user == null || user.equals("")) {
//            cookies = req.getCookies();
//            for (int i = 0; i < cookies.length; ++i) {
//                Cookie cookie = cookies[i];
//                if (cookie.getName().equals(OverallInfo.COOKIE_NAME_USERNAME)) {
//                    username = cookie.getValue();
//                }
//                if (cookie.getName().equals(OverallInfo.COOKIE_NAME_SESSION_ID)) {
//                    sessionid = cookie.getValue();
//                }
//            }
//            Sessioninfo sessionInfo = gi.getSessioninfosByName(username).get(0);
//            if (sessionInfo == null) {
//                isAutoLogin = false;
//            } else {
//                isAutoLogin = true;
//            }
//        }
//        if (isAutoLogin) {
//            FacesContext context = FacesContext.getCurrentInstance();
//            ELContext eLContext = context.getELContext();
//            LoginManagedBean loginManagedBean = (LoginManagedBean) context.getApplication()
//                    .getELResolver().getValue(eLContext, null, "loginManagedBean");
//            if (loginManagedBean != null) {
//                List<Manager> managers = gi.getManagerByName(username);
//                boolean[] permissions;
//                if (managers == null || managers.isEmpty()) {
//                    return;
//                } else {
//                    Manager manager = managers.get(0);
//                    permissions = new boolean[6];
//
//                    permissions[1] = manager.isPermission1();
//                    permissions[2] = manager.isPermission2();
//                    permissions[3] = manager.isPermission3();
//                    permissions[4] = manager.isPermission4();
//                    permissions[5] = manager.isPermission5();
//                }
//                loginManagedBean.setStatus(true);
//                loginManagedBean.setUsername(username);
//                loginManagedBean.setPermissions(permissions);
//            }
//        }
        // Filter end


        Throwable problem = null;


        try {
            chain.doFilter(request, response);
        } catch (Throwable t) {
            // If an exception is thrown somewhere down the filter chain,
            // we still want to execute our after processing, and then
            // rethrow the problem after that.
            problem = t;
        }

        doAfterProcessing(request, response);
        // If there was a problem, we want to rethrow it if it is
        // a known type, otherwise log it.
        if (problem
                != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
            sendProcessingError(problem, response);
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    @Override
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("AutoLoginFilter()");
        }
        StringBuilder sb = new StringBuilder("AutoLoginFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    private boolean isURLNotInFilterList(HttpServletRequest request) {
        String url = request.getServletPath()
                + (request.getPathInfo() == null ? "" : request.getPathInfo());
        return notCheckURLList.contains(url);
    }
}