package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.UsuarioModel;
import model.VendaModel;

public class VendaDao {
    public boolean insere(VendaModel venda){
        try{
            Connection c = new Conexao().conectar();
            PreparedStatement statement = c.prepareStatement("insert into venda (pessoa_id) values (?)");
            statement.setObject(1, venda.getUsuario());
            statement.execute();
            
            return true;
        } catch (Exception e){
            System.out.println("Erro ao inserir registro " + e.getMessage());
            return false;
        }
    }
    
    public boolean alterar(VendaModel venda){
        Connection c =  Conexao.conectar();
        try{
            String query = "UPDATE venda SET pessoa_id=? WHERE id=?";
            PreparedStatement statement = c.prepareStatement(query);

            statement.setInt(1, venda.getUsuario().getId());
            statement.setInt(2, venda.getId());
            statement.executeUpdate();
            
            return true;
        }
        catch (Exception e){
            return false;
        } finally{
            Conexao.desconectar();
        }
    }
    
    public List<VendaModel> buscar() {
        List<VendaModel> lista = new ArrayList<VendaModel>();
        try{
            Connection conexao = Conexao.conectar();
            PreparedStatement preparedStatement = conexao.prepareStatement("select * from venda");
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                VendaModel um = new VendaModel();
                um.setId(rs.getInt("id"));
                um.setInt(rs.getInt("Usuario_id"));
                lista.add(um);
            }
            Conexao.desconectar();
        } catch (Exception e) {
            System.out.println("Erro");
        } finally {
            return lista;
        }
    }
    
    public VendaModel buscarPorId(int id){
        VendaModel model = new VendaModel();
        try{
            Connection conexao = Conexao.conectar();
            PreparedStatement preparedStatement = conexao.prepareStatement("select * from venda WHERE id=?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                model.setId(rs.getInt("id"));
                model.setInt(rs.getInt("Usuario_id"));
            }
            Conexao.desconectar();
        } catch (Exception e) {
            System.out.println("Erro");
        } finally {
            return model;
        }
    }
    
    public boolean apagar(VendaModel venda) {
        Connection c =  Conexao.conectar();
        try{
            PreparedStatement statement = c.prepareStatement("delete from venda WHERE id=" + venda.getId());
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
