package saidas;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Mensagens {

    public static void mostrarMensagemErro(String titulo, String conteudo) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, conteudo);
        FacesContext contexto = FacesContext.getCurrentInstance();
        contexto.addMessage(null, msg);
    }
    
    public static void mostrarMensagemAviso(String titulo, String conteudo) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, conteudo);
        FacesContext contexto = FacesContext.getCurrentInstance();
        contexto.addMessage(null, msg);
    }
    
    public static void mostrarMensagemAlerta(String titulo, String conteudo) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, titulo, conteudo);
        FacesContext contexto = FacesContext.getCurrentInstance();
        contexto.addMessage(null, msg);
    }
    
    public static void mostrarMensagemFatal(String titulo, String conteudo) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, titulo, conteudo);
        FacesContext contexto = FacesContext.getCurrentInstance();
        contexto.addMessage(null, msg);
    }
}
