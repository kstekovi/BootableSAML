package org.bootable.jar.saml;

public interface SecuredEJBRemote {

    /**
     * @return A String containing the name of the current principal and also confirmation of role membership.
     */
    String getSecurityInformation();

    /**
     * A method that is only invokable if the user has the 'guest' role.
     */
    String guestMethod();

    /**
     * A method that is only invokable if the user has the 'user' role.
     */
    String userMethod();

    /**
     * A method that is only invokable if the user has the 'admin' role.
     */
    String adminMethod();
}