package bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import dao.UsuarioDao;
import model.UsuarioModel;
import saidas.Mensagens;
import util.SessionUtil;

@ManagedBean
@RequestScoped
public class LoginBean {
    private UsuarioModel modelo = new UsuarioModel();
    
    public LoginBean() {
    }

    public UsuarioModel getModelo() {
        return modelo;
    }

    public void setModelo(UsuarioModel modelo) {
        this.modelo = modelo;
    }
    
    public String login(){
        modelo = new UsuarioDao().login(modelo);
        if(modelo.getId() != 0) {
            SessionUtil.setParametro("usuarioLogado", modelo);
            return "/index.xtml?faces-redirect=true";
        } else {
            Mensagens.mostrarMensagemErro("Erro ao realizar o login", null);
            return null;
        }
    }
    
    public String logout(){
        SessionUtil.invalidate();
        return "/login.xtml?faces-redirect=true";
    }
    
    public String cadastro(){
        return "/cadastro.xhtml?faces-redirect=true";
    }
}
