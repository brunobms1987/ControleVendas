package bean;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import dao.UsuarioDao;
import model.PerfilModel;
import model.UsuarioModel;
import saidas.Mensagens;
import util.EmailUtil;

@ManagedBean
@ViewScoped
public class UsuarioBean {
// usuarioBeans faz o controle da aplicação..

    private UsuarioModel modelo = new UsuarioModel();
    private List<UsuarioModel> lista = new ArrayList<UsuarioModel>();
    private List<UsuarioModel> listaFiltro = new ArrayList<UsuarioModel>();

    public UsuarioBean() {
        lista = new UsuarioDao().buscar();
        listaFiltro = lista;
    }

    public UsuarioModel getModelo() {
        return modelo;
    }

    public void setModelo(UsuarioModel modelo) {
        this.modelo = modelo;
    }

    public List<UsuarioModel> getLista() {

        return lista;
    }

    public void setLista(List<UsuarioModel> lista) {
        this.lista = lista;
    }

    public List<UsuarioModel> getListaFiltro() {
        return listaFiltro;
    }

    public void setListaFiltro(List<UsuarioModel> listaFiltro) {
        this.listaFiltro = listaFiltro;
    }

    public void setSelecionado(UsuarioModel selecionado) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("selecionado", selecionado);

        this.selecionado = selecionado;
    }

    @PostConstruct
    public void verificaSelecionado() {
        selecionado = (UsuarioModel) FacesContext.
                getCurrentInstance().
                getExternalContext().
                getFlash().get("selecionado");
    }

    public void cadastrar() {
        if (selecionado == null) {
            if (new UsuarioDao().inserir(modelo)) {
                modelo = new UsuarioModel();
                Mensagens.mostrarMensagemAviso("Cadastro realizado com sucesso", "Detalhes da mensagem");
            } else {
                Mensagens.mostrarMensagemErro("Cadastro não realizado", "Não foi possível realizar o cadastro");
            }
        } else {
            new UsuarioDao().alterar(selecionado);
            Mensagens.mostrarMensagemAviso("Registro atualizado com sucesso", "Detalhes da mensagem");
        }
    }

    public void novoUsuario() {
        //gerar chave
        String chave = geraChave().substring(0,9);
        modelo.setChave(chave);
        
        //alterar id do perfil para cadastrar como user padrão
        PerfilModel perfil = new PerfilModel();
        perfil.setId(3);
        
        
        modelo.setPerfil(perfil);
        if (new UsuarioDao().inserir(modelo)) {
            //mandar email com o link que tem a chave
            String destinatario = modelo.getEmail();
            String user = modelo.getNome();
            String conteudo = "<b> NÃO RESPONDA ESTE E-MAIL</b><br><br>Olá, " + user + "!<br>Confirme seu cadastro neste link:"
                    + "<a href='http://localhost:8080/PrimeFacesI/novo.xhtml?chave=" + chave + "'>"
                    + "http://localhost:8080/PrimeFacesI/novo.xhtml?chave=" + chave
                    + "</a>";
            String assunto = "Confirmação de Cadastro";
            
            // enviando o email
            EmailUtil email = new EmailUtil(destinatario, conteudo, assunto);
            email.enviarSSL();
            
            modelo = new UsuarioModel();
            Mensagens.mostrarMensagemAviso("Cadastro realizado com sucesso", "Aguardando confirmação do seu e-mail");
        } else {
            Mensagens.mostrarMensagemErro("Cadastro não realizado", "Não foi possível realizar o cadastro");
        }

    }
    
    private String geraChave() {
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            Calendar c = Calendar.getInstance();
            String data = dateFormat.format(new Date());
            
            md.update(data.getBytes());
            byte[] hashMd5 = md.digest();
            return hashMd5.toString();
        } catch (Exception e) {
            System.out.println("Erro " +e);
            return null;
        }
    }
    
    public void confirmar(){
        String chaveConfirmar = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("chave");
        System.out.println(chaveConfirmar);
        if(chaveConfirmar != null){
            if(new UsuarioDao().buscaChaveConfirmar(chaveConfirmar)){
                System.out.println("TUDO CERTO");
            } else {
                System.out.println("ERRO SQL");
            }
        } else {
            // redireciona pro login.xhtml
            System.out.println("chegou pelo lugar errado amigo");
        }
    }

    private UsuarioModel selecionado;

    public UsuarioModel getSelecionado() {
        return selecionado;
    }

    /*public void setSelecionado(UsuarioModel selecionado) {
        this.selecionado = selecionado;
    }*/
    public void onRowSelect(SelectEvent event) {
        try {
            setSelecionado(((UsuarioModel) event.getObject()));
        } catch (Exception e) {
            Mensagens.mostrarMensagemErro("Erro", e.getMessage());
        }
    }

    public void apagar(UsuarioModel selecionado) {
        if (selecionado != null) {
            try {
                if (new UsuarioDao().apagar(selecionado)) {
                    lista.remove(selecionado);
                    System.out.println(lista.size());
                    Mensagens.mostrarMensagemAviso("Registro apagado", "Registro foi apagado com sucesso!" + selecionado);
                }
            } catch (Exception e) {
                Mensagens.mostrarMensagemErro("Erro", e.getMessage());
            }
        }
    }

}
