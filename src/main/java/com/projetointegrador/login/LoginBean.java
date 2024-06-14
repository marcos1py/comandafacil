package com.projetointegrador.login;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.springframework.stereotype.Component;

@Component(value = "loginBean")
@ViewScoped
public class LoginBean implements Serializable {


    private String username;
    private String password;


    public String login() {
        if ("usuario".equals(username) && "senha".equals(password)) {
            return "home.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de login", "Usuário ou senha inválidos"));
            return null;
        }
    }    
    
    // Limpar a sessão, realizar logout
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login.xhtml?faces-redirect=true";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
