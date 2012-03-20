/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import etoile.javapi.professor.Discipline;
import etoile.javapi.professor.Module;
import etoile.javapi.professor.Professor;
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
import javax.faces.event.ActionEvent;
import menu.MenuBean;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import sha1.sha1;
import test.testManager;

@ManagedBean(name = "userManager")
@SessionScoped
public class userManager implements Serializable {

    @ManagedProperty(value = "#{sha1}")
    private sha1 sha1;
    private String username;
    private String password;
    private ServiceManager manager;
    private MenuBean menu;
    private Professor current_user;
    private Discipline selectedDiscipline;
    private String moduleName;
    private testManager testManager;

    public testManager getTestManager() {
        return testManager;
    }

    public void setTestManager(testManager testManager) {
        this.testManager = testManager;
    }

    
    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
    
    
    public Discipline getSelectedDiscipline() {
        return selectedDiscipline;
    }

    public void setSelectedDiscipline(Discipline selectedDiscipline) {
        this.selectedDiscipline = selectedDiscipline;
    }

    
    
    public MenuBean getMenu() {
        return menu;
    }

    public void setMenu(MenuBean menu) {
        this.menu = menu;
    }

    public void setSha1(sha1 sha1) {
        this.sha1 = sha1;
    }

    public sha1 getSha1() {
        return sha1;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String checkValidUser() {
        System.out.println("DEBUG: USER: " + username + " TRYING TO LOGIN");
        try {
            manager = new ServiceManager();

            if (manager.setAuthentication(username, sha1.parseSHA1Password(password))) {

                current_user = manager.getCurrentProfessor();
                manager.userService().updateDisciplines(current_user.getId());

                this.menu = new MenuBean(current_user.getDisciplines());

                System.out.println("DEBUG: USER: " + username + " SUCCESS");
                return "success";
            }

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(userManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(userManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(userManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(userManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(userManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Wrong User or Password"));
        System.out.println("DEBUG: USER: " + username + " FAIL");
        return "fail";

    }

    public String logOff() {
        //TODO: Propper logout
        try {
            System.out.println("DEBUG: Redirecting to Logoff");
            manager.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(userManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "logoff";

    }

    public void redirectAddTest(ActionEvent event) {
        Object obj = event.getSource();
        MenuItem aux_info = (MenuItem) obj;
        Submenu aux_discipline = (Submenu) aux_info.getParent();
        selectedDiscipline = manager.userService().getDiscipline(aux_discipline.getLabel());
        System.out.println("DEBUG: SELECTED DISCIPLINE: " + selectedDiscipline.name + " ID: " + selectedDiscipline.getId());
        for (Module m: selectedDiscipline.modules){
            System.out.println("Module"+m.name);
        }
        testManager=new testManager(manager);

    }
    
    public String redirectAddTest() {
       System.out.println("DEBUG: Redirecting to addTest");
        return "addTest";
    }
    
      public void redirectAddModule(ActionEvent event) {
        Object obj = event.getSource();
        MenuItem aux_info = (MenuItem) obj;
        Submenu aux_discipline = (Submenu) aux_info.getParent();
        selectedDiscipline = manager.userService().getDiscipline(aux_discipline.getLabel());
        System.out.println("DEBUG: SELECTED DISCIPLINE: " + selectedDiscipline.name + " ID: " + selectedDiscipline.getId());

    }
    
    public String redirectAddModule() {
       System.out.println("DEBUG: Redirecting to addModule");
        return "addModule";
    }
    
    public void redirectEditContents(ActionEvent event) {
        Object obj = event.getSource();
        MenuItem aux_info = (MenuItem) obj;
        Submenu aux_discipline = (Submenu) aux_info.getParent();
        selectedDiscipline = manager.userService().getDiscipline(aux_discipline.getLabel());
        System.out.println("DEBUG: SELECTED DISCIPLINE: " + selectedDiscipline.name + " ID: " + selectedDiscipline.getId());

    }
    
    public String redirectEditContents() {
       System.out.println("DEBUG: Redirecting to Edit Contents");
        return "editContents";
    }
    

    
    
    
       
}
