package br.mr.robot.products.util;

import br.mr.robot.products.util.exception.ErroSistema;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author mrrobot
 */
public class FabricaConexao {
    
    private static Connection conexao;
    private static final String URL_CONEXAO = "jdbc:mysql://localhost/products";
    private static final String USUARIO = "root";
    private static final String SENHA = null;
    

    public static Connection getConexao() throws ErroSistema{
        if(conexao == null){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conexao = DriverManager.getConnection(URL_CONEXAO, USUARIO, SENHA);
            } catch (ClassNotFoundException ex) {
                throw new ErroSistema("Driver do BD não encontrado!", ex);
            } catch (SQLException ex) {
                throw new ErroSistema("Não foi possível completar esta ação!", ex);
            }
        }
        return conexao;
    }
    
    public static void fecharConexao() throws ErroSistema {
        if(conexao != null){
            try {
                conexao.close();
                conexao = null;
            } catch (SQLException ex) {
                throw new ErroSistema("Erro ao finalizar a conexão com o BD!", ex);
            }
        }
    }
 
    
}
