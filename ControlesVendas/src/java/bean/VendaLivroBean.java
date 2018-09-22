package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.bean.ViewScoped;
import dao.VendaLivroDao;
import model.VendaLivroModel;
import saidas.Mensagens;

@ManagedBean
@ViewScoped
public class VendaLivroBean implements Serializable{
    private VendaLivroModel modelo = new VendaLivroModel();
    private List<VendaLivroModel> lista = new ArrayList<VendaLivroModel>();
    private List<VendaLivroModel> listaFiltro = new ArrayList<VendaLivroModel>();
    private List<SelectItem> listaSelect;
     private VendaLivroModel selecionado = new VendaLivroModel();

    public VendaLivroBean() {
        lista = new VendaLivroDao().buscar();
        listaFiltro = lista;
    }
    
    public VendaLivroModel getModelo() {
        return modelo;
    }

    public void setModelo(VendaLivroModel modelo) {
        this.modelo = modelo;
    }

    public List<VendaLivroModel> getLista() {
        return lista;
    }

    public void setLista(List<VendaLivroModel> lista) {
        this.lista = lista;
    }

    public List<VendaLivroModel> getListaFiltro() {
        return listaFiltro;
    }

    public void setListaFiltro(List<VendaLivroModel> listaFiltro) {
        this.listaFiltro = listaFiltro;
    }

    public List<SelectItem> getListaSelect() {
        if(this.listaSelect == null){
            listaSelect = new ArrayList<SelectItem>();
            
            for(VendaLivroModel model : lista){
                SelectItem item = new SelectItem(model, model.getLivros().toString());
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
        selecionado = (VendaLivroModel) FacesContext.
                getCurrentInstance().
                getExternalContext().
                getFlash().get("pselecionado");
        System.err.println("");
    }
    
    public void cadastrar(){
        if (selecionado == null) {
            if (new VendaLivroDao().insere(modelo)) {
                modelo = new VendaLivroModel();
                Mensagens.mostrarMensagemAviso("Cadastro realizado com sucesso", "Detalhes da mensagem");
            } else {
                Mensagens.mostrarMensagemErro("Cadastro não realizado", "Não foi possível realizar o cadastro");
            }
        }
        else {
            new VendaLivroDao().alterar(selecionado);
            Mensagens.mostrarMensagemAviso("Registro atualizado com sucesso", "Detalhes da mensagem");
        }
    }
    
   

    public VendaLivroModel getSelecionado() {
        return selecionado;
    }

    public void setSelecionado(VendaLivroModel selecionado) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("pselecionado", selecionado);
        
        this.selecionado = selecionado;
    }
    
    public void apagar(VendaLivroModel selecionado) {
        if (selecionado != null) {
            try {
                if (new VendaLivroDao().apagar(selecionado)) {
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
