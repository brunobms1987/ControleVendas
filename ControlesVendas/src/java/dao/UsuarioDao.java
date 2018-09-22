package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.UsuarioModel;
import saidas.Mensagens;

public class UsuarioDao{
    
    public List<UsuarioModel> buscar() {
        
        List<UsuarioModel> lista = new ArrayList<UsuarioModel>();
        
        try{
            Connection conexao = Conexao.conectar();
            PreparedStatement preparedStatement = conexao.prepareStatement("select * from usuario");
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                UsuarioModel um = new UsuarioModel();
                um.setId(rs.getInt("id"));
                um.setNome(rs.getString("nome"));
                um.setSenha(rs.getString("senha"));
                um.setUsuario(rs.getString("usuario"));
                um.setEmail(rs.getString("email"));
                um.setAtivo(rs.getBoolean("ativo"));
                um.setDataNascimento(rs.getDate("dataNascimento"));
                lista.add(um);
            }
            Conexao.desconectar();
        } catch (Exception e) {
            System.out.println("Erro");
        } finally {
            return lista;
        }
    }
    
    public boolean buscaChaveConfirmar(String chave){
        
        int idNovo;
        
        try {
            Connection conexao = Conexao.conectar();
            String sql = "SELECT * FROM usuario WHERE chave='" + chave + "'";
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            
            if (rs.getFetchSize() == 1) {
                idNovo = rs.getInt("id");
                
                String query = "UPDATE usuario SET ativo=?, chave=? WHERE id=?";
                preparedStatement = conexao.prepareStatement(query);

                preparedStatement.setInt(1, 1);
                preparedStatement.setInt(2, 0);
                preparedStatement.setInt(3, idNovo);
                preparedStatement.executeUpdate();
                
                return true;
            } else{
                return false;
            }            
            
        } catch (Exception e) {
            System.out.println("Erro " + e);
            return false;
        }
    }
    
    public boolean inserir(UsuarioModel objeto) {
        try{
            Connection c = new Conexao().conectar();
            PreparedStatement statement = c.prepareStatement("INSERT INTO usuario (nome, email, usuario, senha, ativo, chave, dataNascimento, id_perfil, foto) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, objeto.getNome());
            statement.setString(2, objeto.getEmail());
            statement.setString(3, objeto.getUsuario());
            statement.setString(4, objeto.getSenha());
            statement.setBoolean(5, objeto.isAtivo());
            statement.setString(6, objeto.getChave());
            Date sqlDate = new Date(objeto.getDataNascimento().getTime());
            statement.setDate(7, sqlDate);
            statement.setInt(8, objeto.getPerfil().getId());
            statement.setString(9, objeto.getFoto());
            statement.execute();
            
            return true;
        } catch (Exception e){
            System.out.println("Erro ao inserir registro " + e.getMessage());
            return false;
        }
    }
    
    public boolean apagar(UsuarioModel selecionado) {
        Connection c =  Conexao.conectar();
        try{
            PreparedStatement statement = c.prepareStatement("delete from usuario WHERE id=" + selecionado.getId());
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
    
    public boolean alterar(UsuarioModel selecionado) {
        Connection c =  Conexao.conectar();
        try{
            String query = "UPDATE usuario SET nome=?, email=?, usuario=?, senha=?, ativo=?, dataNascimento=?, id_perfil=?, foto=? WHERE id=?";
            PreparedStatement statement = c.prepareStatement(query);

            statement.setString(1, selecionado.getNome());
            statement.setString(2, selecionado.getEmail());
            statement.setString(3, selecionado.getUsuario());
            statement.setString(4, selecionado.getSenha());
            statement.setBoolean(5, selecionado.isAtivo());
            statement.setDate(6, new Date(selecionado.getDataNascimento().getTime()));
            statement.setInt(7, selecionado.getPerfil().getId());
            statement.setString(8, selecionado.getFoto());
            statement.setInt(9, selecionado.getId());
            statement.executeUpdate();
            
            return true;
        }
        catch (Exception e){
            return false;
        } finally{
            Conexao.desconectar();
        }
    }
    
    public UsuarioModel login(UsuarioModel model) {
        Connection c = Conexao.conectar();
        String sql = "SELECT * FROM usuario WHERE usuario=? AND senha=? AND ativo=1 limit 1";
        try {
            PreparedStatement statement = c.prepareStatement(sql);
            statement.setString(1, model.getUsuario());
            statement.setString(2, model.getSenha());
            
            ResultSet rs = statement.executeQuery();
            
            while(rs.next()) {
                model.setId(rs.getInt("id"));
                model.setNome(rs.getString("nome"));
                model.setEmail(rs.getString("email"));
                model.setSenha(rs.getString("senha"));
                model.setDataNascimento(rs.getDate("dataNascimento"));
                model.setAtivo(rs.getBoolean("ativo"));
                model.setUsuario(rs.getString("usuario"));
                model.setPerfil(new PerfilDao().buscarPorId(rs.getInt("id_perfil")));
            }
            
        } catch (Exception e){
            Mensagens.mostrarMensagemErro("erro login", "comportamento inesperado");
        } finally {
            Conexao.desconectar();
            return model;
        }
    }
    
    
}
