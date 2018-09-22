package util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class SessionUtil {
    
    public static HttpSession getSessao() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession sessao = (HttpSession) facesContext.getExternalContext().getSession(false);
            return sessao;
        } catch (Exception e) {
            return null;
        }
    }
    
    public static void setParametro(String chave, Object valor) {
        getSessao().setAttribute(chave, valor);
    }
    
    public static Object getParametro(String chave) {
        return getSessao().getAttribute(chave);
    }
    
    public static void invalidate() {
        getSessao().invalidate();
    }
}
