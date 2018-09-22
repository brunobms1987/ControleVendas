package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.bean.ViewScoped;
import dao.VendaDao;
import model.VendaModel;
import saidas.Mensagens;

@ManagedBean
@ViewScoped
public class VendaBean implements Serializable{
    private VendaModel modelo = new VendaModel();
    private List<VendaModel> lista = new ArrayList<VendaModel>();
    private List<VendaModel> listaFiltro = new ArrayList<VendaModel>();
    private List<SelectItem> listaSelect;
     private VendaModel selecionado = new VendaModel();

    public VendaBean() {
        lista = new VendaDao().buscar();
        listaFiltro = lista;
    }
    
    public VendaModel getModelo() {
        return modelo;
    }

    public void setModelo(VendaModel modelo) {
        this.modelo = modelo;
    }

    public List<VendaModel> getLista() {
        return lista;
    }

    public void setLista(List<VendaModel> lista) {
        this.lista = lista;
    }

    public List<VendaModel> getListaFiltro() {
        return listaFiltro;
    }

    public void setListaFiltro(List<VendaModel> listaFiltro) {
        this.listaFiltro = listaFiltro;
    }

    public List<SelectItem> getListaSelect() {
        if(this.listaSelect == null){
            listaSelect = new ArrayList<SelectItem>();
            
            for(VendaModel model : lista){
                SelectItem item = new SelectItem(model, model.getUsuario().getNome());
                
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
        selecionado = (VendaModel) FacesContext.
                getCurrentInstance().
                getExternalContext().
                getFlash().get("pselecionado");
        System.err.println("");
    }
    
    public void cadastrar(){
        if (selecionado == null) {
            if (new VendaDao().insere(modelo)) {
                modelo = new VendaModel();
                Mensagens.mostrarMensagemAviso("Cadastro realizado com sucesso", "Detalhes da mensagem");
            } else {
                Mensagens.mostrarMensagemErro("Cadastro não realizado", "Não foi possível realizar o cadastro");
            }
        }
        else {
            new VendaDao().alterar(selecionado);
            Mensagens.mostrarMensagemAviso("Registro atualizado com sucesso", "Detalhes da mensagem");
        }
    }
    
   

    public VendaModel getSelecionado() {
        return selecionado;
    }

    public void setSelecionado(VendaModel selecionado) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("pselecionado", selecionado);
        
        this.selecionado = selecionado;
    }
    
    public void apagar(VendaModel selecionado) {
        if (selecionado != null) {
            try {
                if (new VendaDao().apagar(selecionado)) {
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
