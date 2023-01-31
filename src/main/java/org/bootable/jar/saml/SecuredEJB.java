package org.bootable.jar.saml;


import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.SessionContext;

import org.jboss.ejb3.annotation.SecurityDomain;

import org.springframework.stereotype.Component;


public class SecuredEJB implements SecuredEJBRemote {

    @Resource
    private SessionContext context;

    @PermitAll
    public String getSecurityInformation() {
        StringBuilder sb = new StringBuilder("[");
        sb.append("Principal=[").append(context.getCallerPrincipal().getName()).append("], ");
        userInRole("guest", sb).append(", ");
        userInRole("user", sb).append(", ");
        userInRole("admin", sb).append("]");

        return sb.toString();
    }

    @RolesAllowed("guest")
    public String guestMethod() {
        return "Principal=[" + context.getCallerPrincipal() + "] has guest permissions";
    }

    @RolesAllowed("user")
    public String userMethod() {
        return "Principal=[" + context.getCallerPrincipal() + "] has user permissions";
    }

    @RolesAllowed("admin")
    public String adminMethod() {
        return "Principal=[" + context.getCallerPrincipal() + "] has admin permissions";
    }

    private StringBuilder userInRole(final String role, final StringBuilder sb) {
        sb.append("In role [").append(role).append("]=").append(context.isCallerInRole(role));
        return sb;
    }

}