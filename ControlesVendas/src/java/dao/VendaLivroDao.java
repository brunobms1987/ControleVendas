package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.VendaLivroModel;

public class VendaLivroDao {
    public boolean insere(VendaLivroModel vendaLivro){
        try{
            Connection c = new Conexao().conectar();
            PreparedStatement statement = c.prepareStatement("insert into vendaLivro (Livro_id, Venda_id, quant, valorUnit, valorTotal) values (?, ?, ?, ?, ?)");
            statement.setInt(1, (int) vendaLivro.getLivros().get(0));
            statement.setInt(2, vendaLivro.getVenda().getId());
            statement.setInt(3, vendaLivro.getQuant());
            statement.setDouble(4, vendaLivro.getValorUnit());
            statement.setDouble(5, vendaLivro.getValorTotal(vendaLivro.getValorUnit(), vendaLivro.getQuant()));
            statement.execute();
            
            return true;
        } catch (Exception e){
            System.out.println("Erro ao inserir registro " + e.getMessage());
            return false;
        }
    }
    
    public boolean alterar(VendaLivroModel vendaLivro){
        Connection c =  Conexao.conectar();
        try{
            String query = "UPDATE vendaLivro SET Livro_id=?, quant=?, valorUnit=?, valorTotal=? WHERE id=?";
            PreparedStatement statement = c.prepareStatement(query);

            statement.setInt(1, (int) vendaLivro.getLivros().get(0));
            statement.setInt(2, vendaLivro.getQuant());
            statement.setDouble(3, vendaLivro.getValorUnit());
            statement.setDouble(4, vendaLivro.getValorTotal(vendaLivro.getValorUnit(), vendaLivro.getQuant()));
            statement.executeUpdate();
            
            return true;
        }
        catch (Exception e){
            return false;
        } finally{
            Conexao.desconectar();
        }
    }
    
    public List<VendaLivroModel> buscar() {
        List<VendaLivroModel> lista = new ArrayList<VendaLivroModel>();
        try{
            Connection conexao = Conexao.conectar();
            PreparedStatement preparedStatement = conexao.prepareStatement("select * from vendaLivro");
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                int i=1;
                VendaLivroModel um = new VendaLivroModel();
                um.setId(rs.getInt("id"));
                um.setLivros((ArrayList) rs.getArray(i));
                um.setQuant(rs.getInt("quant"));
                um.setValorUnit(rs.getDouble("valorUnit"));
                um.setValorTotal(rs.getDouble("valorTotal"));
                lista.add(um);
                i++;
            }
            Conexao.desconectar();
        } catch (Exception e) {
            System.out.println("Erro");
        } finally {
            return lista;
        }
    }
    
    public VendaLivroModel buscarPorId(int id){
        VendaLivroModel model = new VendaLivroModel();
        try{
            Connection conexao = Conexao.conectar();
            PreparedStatement preparedStatement = conexao.prepareStatement("select * from vendaLivro WHERE id=?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                int i=1;
                VendaLivroModel um = new VendaLivroModel();
                model.setId(rs.getInt("id"));
                model.setLivros((ArrayList) rs.getArray(i));
                model.setQuant(rs.getInt("quant"));
                model.setValorUnit(rs.getDouble("valorUnit"));
                model.setValorTotal(rs.getDouble("valorTotal"));
                i++;
            }
            Conexao.desconectar();
        } catch (Exception e) {
            System.out.println("Erro");
        } finally {
            return model;
        }
    }
    
    public boolean apagar(VendaLivroModel vendaLivro) {
        Connection c =  Conexao.conectar();
        try{
            PreparedStatement statement = c.prepareStatement("delete from vendaLivro WHERE id=" + vendaLivro.getId());
            statement.execute();
            statement.close();
            
            return true;
        }
        catch (Exception e){
            return false;
        } finally{
            Conexao.desconectar();
        }
    }
}
