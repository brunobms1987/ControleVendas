package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.LivroModel;

public class LivroDao {
    public boolean insere(LivroModel livro){
        try{
            Connection c = new Conexao().conectar();
            PreparedStatement statement = c.prepareStatement("insert into livro (titulo, subtitulo, valor, autor) values (?, ?, ?, ?)");
            statement.setString(1, livro.getTitulo());
            statement.setString(2, livro.getSubtitulo());
            statement.setDouble(3, livro.getValor());
            statement.setString(4, livro.getAutor());
            statement.execute();
            
            return true;
        } catch (Exception e){
            System.out.println("Erro ao inserir registro " + e.getMessage());
            return false;
        }
    }
    
    public boolean alterar(LivroModel livro){
        Connection c =  Conexao.conectar();
        try{
            String query = "UPDATE livro SET titulo=?, subtitulo=?, valor=?, autor=?,  WHERE id=?";
            PreparedStatement statement = c.prepareStatement(query);

            statement.setString(1, livro.getTitulo());
            statement.setString(2, livro.getSubtitulo());
            statement.setDouble(3, livro.getValor());
            statement.setString(4, livro.getAutor());
            statement.setInt(5, livro.getId());
            statement.executeUpdate();
            
            return true;
        }
        catch (Exception e){
            return false;
        } finally{
            Conexao.desconectar();
        }
    }
    
    public List<LivroModel> buscar() {
        List<LivroModel> lista = new ArrayList<LivroModel>();
        try{
            Connection conexao = Conexao.conectar();
            PreparedStatement preparedStatement = conexao.prepareStatement("select * from livro");
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                LivroModel um = new LivroModel();
                um.setId(rs.getInt("id"));
                um.setTitulo(rs.getString("titulo"));
                um.setSubtitulo(rs.getString("subtitulo"));
                um.setAutor(rs.getString("autor"));
                um.setValor(rs.getDouble("valor"));
                lista.add(um);
            }
            Conexao.desconectar();
        } catch (Exception e) {
            System.out.println("Erro");
        } finally {
            return lista;
        }
    }
    
    public LivroModel buscarPorId(int id){
        LivroModel model = new LivroModel();
        try{
            Connection conexao = Conexao.conectar();
            PreparedStatement preparedStatement = conexao.prepareStatement("select * from livro WHERE id=?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                model.setId(rs.getInt("id"));
                model.setTitulo(rs.getString("titulo"));
                model.setSubtitulo(rs.getString("subtitulo"));
                model.setAutor(rs.getString("autor"));
                model.setValor(rs.getDouble("valor"));
            }
            Conexao.desconectar();
        } catch (Exception e) {
            System.out.println("Erro");
        } finally {
            return model;
        }
    }
    
    public boolean apagar(LivroModel livro) {
        Connection c =  Conexao.conectar();
        try{
            PreparedStatement statement = c.prepareStatement("delete from livro WHERE id=" + livro.getId());
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
