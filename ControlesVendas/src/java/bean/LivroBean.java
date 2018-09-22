package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.bean.ViewScoped;
import dao.LivroDao;
import model.LivroModel;
import saidas.Mensagens;

@ManagedBean
@ViewScoped
public class LivroBean implements Serializable{
    private LivroModel modelo = new LivroModel();
    private List<LivroModel> lista = new ArrayList<LivroModel>();
    private List<LivroModel> listaFiltro = new ArrayList<LivroModel>();
    private List<SelectItem> listaSelect;
     private LivroModel selecionado = new LivroModel();

    public LivroBean() {
        lista = new LivroDao().buscar();
        listaFiltro = lista;
    }
    
    public LivroModel getModelo() {
        return modelo;
    }

    public void setModelo(LivroModel modelo) {
        this.modelo = modelo;
    }

    public List<LivroModel> getLista() {
        return lista;
    }

    public void setLista(List<LivroModel> lista) {
        this.lista = lista;
    }

    public List<LivroModel> getListaFiltro() {
        return listaFiltro;
    }

    public void setListaFiltro(List<LivroModel> listaFiltro) {
        this.listaFiltro = listaFiltro;
    }

    public List<SelectItem> getListaSelect() {
        if(this.listaSelect == null){
            listaSelect = new ArrayList<SelectItem>();
            
            for(LivroModel model : lista){
                SelectItem item = new SelectItem(model, model.getTitulo());
                
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
        selecionado = (LivroModel) FacesContext.
                getCurrentInstance().
                getExternalContext().
                getFlash().get("pselecionado");
        System.err.println("");
    }
    
    public void cadastrar(){
        if (selecionado == null) {
            if (new LivroDao().insere(modelo)) {
                modelo = new LivroModel();
                Mensagens.mostrarMensagemAviso("Cadastro realizado com sucesso", "Detalhes da mensagem");
            } else {
                Mensagens.mostrarMensagemErro("Cadastro não realizado", "Não foi possível realizar o cadastro");
            }
        }
        else {
            new LivroDao().alterar(selecionado);
            Mensagens.mostrarMensagemAviso("Registro atualizado com sucesso", "Detalhes da mensagem");
        }
    }
    
   

    public LivroModel getSelecionado() {
        return selecionado;
    }

    public void setSelecionado(LivroModel selecionado) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("pselecionado", selecionado);
        
        this.selecionado = selecionado;
    }
    
    public void apagar(LivroModel selecionado) {
        if (selecionado != null) {
            try {
                if (new LivroDao().apagar(selecionado)) {
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
