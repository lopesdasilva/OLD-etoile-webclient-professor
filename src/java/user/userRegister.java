/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import etoile.javapi.professor.ServiceManager;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import services.UserService;
import sha1.sha1;

@ManagedBean(name = "userRegister")
@SessionScoped
public class userRegister implements Serializable {


    private String forgotUserName;
    private String forgotEmail;

    public String getForgotEmail() {
        return forgotEmail;
    }

    public String getForgotUserName() {
        return forgotUserName;
    }

    public void setForgotEmail(String forgotEmail) {
        this.forgotEmail = forgotEmail;
    }

    public void setForgotUserName(String forgotUserName) {
        this.forgotUserName = forgotUserName;
    }

    public String resetPassword() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            System.out.println("User: " + forgotUserName + " with email: " + forgotEmail + " has requested a new password");
            ServiceManager manager = new ServiceManager();

            manager.userService().resetPassword(forgotEmail);


            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Password reset successfully. Please check your Email"));

            context.addCallbackParam("forgotDialog", true);
            return "success";


        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(userRegister.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(userRegister.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(userRegister.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(userRegister.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(userRegister.class.getName()).log(Level.SEVERE, null, ex);
        }
        context.addCallbackParam("forgotDialog", false);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Password reset failed"));
        return "fail";
    }
}
