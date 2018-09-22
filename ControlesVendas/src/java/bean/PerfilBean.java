package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.bean.ViewScoped;
import dao.PerfilDao;
import model.PerfilModel;
import saidas.Mensagens;

@ManagedBean
@ViewScoped
public class PerfilBean implements Serializable{
    private PerfilModel modelo = new PerfilModel();
    private List<PerfilModel> lista = new ArrayList<PerfilModel>();
    private List<PerfilModel> listaFiltro = new ArrayList<PerfilModel>();
    private List<SelectItem> listaSelect;
     private PerfilModel selecionado = new PerfilModel();

    public PerfilBean() {
        lista = new PerfilDao().buscar();
        listaFiltro = lista;
    }
    
    public PerfilModel getModelo() {
        return modelo;
    }

    public void setModelo(PerfilModel modelo) {
        this.modelo = modelo;
    }

    public List<PerfilModel> getLista() {
        return lista;
    }

    public void setLista(List<PerfilModel> lista) {
        this.lista = lista;
    }

    public List<PerfilModel> getListaFiltro() {
        return listaFiltro;
    }

    public void setListaFiltro(List<PerfilModel> listaFiltro) {
        this.listaFiltro = listaFiltro;
    }

    public List<SelectItem> getListaSelect() {
        if(this.listaSelect == null){
            listaSelect = new ArrayList<SelectItem>();
            
            for(PerfilModel model : lista){
                SelectItem item = new SelectItem(model, model.getNome());
                
                this.listaSelect.add(item);
            }
            
        }
        return listaSelect;
    }

    public void setListaSelect(List<SelectItem> listaSelect) {
        this.listaSelect = listaSelect;
    }
    
    @PostConstruct
    public void verificaSelecionado(){
        selecionado = (PerfilModel) FacesContext.
                getCurrentInstance().
                getExternalContext().
                getFlash().get("pselecionado");
        System.err.println("");
    }
    
    public void cadastrar(){
        if (selecionado == null) {
            if (new PerfilDao().insere(modelo)) {
                modelo = new PerfilModel();
                Mensagens.mostrarMensagemAviso("Cadastro realizado com sucesso", "Detalhes da mensagem");
            } else {
                Mensagens.mostrarMensagemErro("Cadastro não realizado", "Não foi possível realizar o cadastro");
            }
        }
        else {
            new PerfilDao().alterar(selecionado);
            Mensagens.mostrarMensagemAviso("Registro atualizado com sucesso", "Detalhes da mensagem");
        }
    }
    
   

    public PerfilModel getSelecionado() {
        return selecionado;
    }

    public void setSelecionado(PerfilModel selecionado) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("pselecionado", selecionado);
        
        this.selecionado = selecionado;
    }
    
    public void apagar(PerfilModel selecionado) {
        if (selecionado != null) {
            try {
                if (new PerfilDao().apagar(selecionado)) {
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
