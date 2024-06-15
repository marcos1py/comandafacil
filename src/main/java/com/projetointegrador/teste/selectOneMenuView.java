package com.projetointegrador.teste;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import javax.faces.validator.ValidatorException;
import java.util.Date;

@ManagedBean
public class selectOneMenuView {
    private Date inicio;
    private Date termino;

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getTermino() {
        return termino;
    }

    public void setTermino(Date termino) {
        this.termino = termino;
    }

    public void validateStartDate(FacesContext context, UIComponent component, Object value) {
        Date inicio = (Date) value;
        if (termino != null && inicio != null) {
            if (inicio.after(termino)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de validação", "Início não pode ser maior que Término"));
            }
        }
    }

    public void validateEndDate(FacesContext context, UIComponent component, Object value) {
        Date termino = (Date) value;
        if (inicio != null && termino != null) {
            if (termino.before(inicio)) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de validação", "Término não pode ser menor que Início"));
            }
        }
    }
}
