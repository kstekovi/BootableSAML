package org.bootable.jar.saml;

import java.io.IOException;
import java.security.Principal;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBAccessException;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(value="/secured")
public class MyServlet extends HttpServlet {

    @EJB
    private SecureEJBLocal secureEJBLocal;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        try (PrintWriter writer = resp.getWriter()) {
//            writer.println("<html>");
//            writer.println("  <head><title>Secured Servlet</title></head>");
//            writer.println("  <body>");
//            writer.println("    <h1>Secured Servlet</h1>");
//            if(service == null){
//                writer.println("service je null ");
//            }else {
//                writer.println("Hurray");
////                writer.println(service.getSecurityInformation());
//            }
//            writer.println("    <p>");
//            writer.print(" Current Principal '");
//            Principal user = req.getUserPrincipal();
//            writer.print(user != null ? user.getName() : "NO AUTHENTICATED USER");
//            writer.print("'");
//            writer.println("    </p>");
//            writer.println("  </body>");
//            writer.println("</html>");
//        }
        response.setContentType("text/plain");
        ServletOutputStream out = response.getOutputStream();

        out.println("Servlet");
        Principal principal = request.getUserPrincipal();
        if (principal != null) {
            out.println("Servlet.getUserPrincipal()=" + principal + " [" + principal.getName() + "]");
        } else {
            out.println("Servlet.getUserPrincipal()=<null>");
        }
        out.println("Servlet.isCallerInRole(\"user\")=" + request.isUserInRole("user"));
        out.println("Servlet.isCallerInRole(\"manager\")=" + request.isUserInRole("manager"));
        out.println("Servlet.isCallerInRole(\"fake\")=" + request.isUserInRole("fake"));
        out.println();

        out.println("@EJB=" + secureEJBLocal);
        if (secureEJBLocal != null) {
//            principal = secureEJBLocal.getCallerPrincipal();
//            if (principal != null) {
//                out.println("@EJB.getCallerPrincipal()=" + principal + " [" + principal.getName() + "]");
//            } else {
//                out.println("@EJB.getCallerPrincipal()=<null>");
//            }
//            out.println("@EJB.isCallerInRole(\"user\")=" + secureEJBLocal.isCallerInRole("user"));
//            out.println("@EJB.isCallerInRole(\"manager\")=" + secureEJBLocal.isCallerInRole("manager"));
//            out.println("@EJB.isCallerInRole(\"fake\")=" + secureEJBLocal.isCallerInRole("fake"));

            try {
                secureEJBLocal.allowUserMethod();
                out.println("@EJB.allowUserMethod() ALLOWED");
            } catch (EJBAccessException e) {
                out.println("@EJB.allowUserMethod() DENIED");
            }

            try {
                secureEJBLocal.allowManagerMethod();
                out.println("@EJB.allowManagerMethod() ALLOWED");
            } catch (EJBAccessException e) {
                out.println("@EJB.allowManagerMethod() DENIED");
            }

            try {
                secureEJBLocal.allowFakeMethod();
                out.println("@EJB.allowFakeMethod() ALLOWED");
            } catch (EJBAccessException e) {
                out.println("@EJB.allowFakeMethod() DENIED");
            }

            try {
                secureEJBLocal.denyAllMethod();
                out.println("@EJB.denyAllMethod() ALLOWED");
            } catch (EJBAccessException e) {
                out.println("@EJB.denyAllMethod() DENIED");
            }
        }
        out.println();
    }
}
