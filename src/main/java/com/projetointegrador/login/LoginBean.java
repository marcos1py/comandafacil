package com.projetointegrador.login;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.springframework.stereotype.Component;

@Component(value = "loginBean")
@ViewScoped
public class LoginBean implements Serializable {


    private String username;
    private String password;


    public String login() {
        if ("usuario".equals(username) && "senha".equals(password)) {
            return "C:\\Users\\Aluno\\PI3\\comandafacil\\src\\main\\resources\\META-INF\\resources\\home.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de login", "Usuário ou senha inválidos"));
            return null;
        }
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
