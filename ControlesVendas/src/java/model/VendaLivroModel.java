package model;

import java.util.ArrayList;

public class VendaLivroModel {
    private int id;
    private int quant;
    private double valorUnit;
    private double valorTotal;
    private VendaModel venda;
    private ArrayList livros;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public double getValorUnit() {
        return valorUnit;
    }

    public void setValorUnit(double valorUnit) {
        this.valorUnit = valorUnit;
    }

    public double getValorTotal(double valorTotal, int quant) {
        this.valorTotal = this.valorUnit * this.quant;
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public VendaModel getVenda() {
        return venda;
    }

    public void setVenda(VendaModel venda) {
        this.venda = venda;
    }

    public ArrayList getLivros() {
        return livros;
    }

    public void setLivros(ArrayList livros) {
        this.livros = livros;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.quant;
        hash = 79 * hash + this.id;
        return hash;
    }

    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VendaLivroModel other = (VendaLivroModel) obj;
        return true;
    }
    
    
}
