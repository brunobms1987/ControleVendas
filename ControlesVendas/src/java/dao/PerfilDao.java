package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.PerfilModel;

public class PerfilDao {
    public boolean insere(PerfilModel perfil){
        try{
            Connection c = new Conexao().conectar();
            PreparedStatement statement = c.prepareStatement("insert into perfil (nome) values (?)");
            statement.setString(1, perfil.getNome());
            statement.execute();
            
            return true;
        } catch (Exception e){
            System.out.println("Erro ao inserir registro " + e.getMessage());
            return false;
        }
    }
    
    public boolean alterar(PerfilModel perfil){
        Connection c =  Conexao.conectar();
        try{
            String query = "UPDATE perfil SET nome=? WHERE id=?";
            PreparedStatement statement = c.prepareStatement(query);

            statement.setString(1, perfil.getNome());
            statement.setInt(2, perfil.getId());
            statement.executeUpdate();
            
            return true;
        }
        catch (Exception e){
            return false;
        } finally{
            Conexao.desconectar();
        }
    }
    
    public List<PerfilModel> buscar() {
        List<PerfilModel> lista = new ArrayList<PerfilModel>();
        try{
            Connection conexao = Conexao.conectar();
            PreparedStatement preparedStatement = conexao.prepareStatement("select * from perfil");
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                PerfilModel um = new PerfilModel();
                um.setId(rs.getInt("id"));
                um.setNome(rs.getString("nome"));
                lista.add(um);
            }
            Conexao.desconectar();
        } catch (Exception e) {
            System.out.println("Erro");
        } finally {
            return lista;
        }
    }
    
    public PerfilModel buscarPorId(int id){
        PerfilModel model = new PerfilModel();
        try{
            Connection conexao = Conexao.conectar();
            PreparedStatement preparedStatement = conexao.prepareStatement("select * from perfil WHERE id=?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                model.setId(rs.getInt("id"));
                model.setNome(rs.getString("nome"));
            }
            Conexao.desconectar();
        } catch (Exception e) {
            System.out.println("Erro");
        } finally {
            return model;
        }
    }
    
    public boolean apagar(PerfilModel perfil) {
        Connection c =  Conexao.conectar();
        try{
            PreparedStatement statement = c.prepareStatement("delete from perfil WHERE id=" + perfil.getId());
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
