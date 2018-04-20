package br.mr.robot.products.dao;

import br.mr.robot.products.entity.Products;
import br.mr.robot.products.util.FabricaConexao;
import br.mr.robot.products.util.exception.ErroSistema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mrrobot
 */
public class ProductsDAO {
    
    public void salvar(Products products) throws ErroSistema{
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps;
            if(products.getId() == null){
                ps = conexao.prepareStatement("INSERT INTO `products`(`modelo`, `fabricante`, `memoria`, `quantidade`) VALUES (?,?,?,?)");
            } else {
                ps = conexao.prepareStatement("update products set modelo=?, fabricante=?, memoria=?, quantidade=? where id=?");
                ps.setInt(5, products.getId());
            }
     
            ps.setString(1, products.getModelo());
            ps.setString(2, products.getFabricante());
            ps.setString(3, products.getMemoria());
            ps.setInt(4, products.getQuantidade());
            
            ps.execute();
            FabricaConexao.fecharConexao();
            
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao Tentar salvar!", ex);
        }
    }
    
    public void deletar(Integer idProducts) throws ErroSistema{
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("delete from products where id = ?");
            ps.setInt(1, idProducts);
            ps.execute();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao deletar o Produto!", ex);
        }
    }
    
    public List<Products> buscar() throws ErroSistema{
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("select * from products");
            ResultSet resultSet = ps.executeQuery();
            List<Products> products = new ArrayList<>();
            while(resultSet.next()){
                Products product = new Products();
                product.setId(resultSet.getInt("id"));
                product.setModelo(resultSet.getString("modelo"));
                product.setFabricante(resultSet.getString("fabricante"));
                product.setMemoria(resultSet.getString("memoria"));
                product.setQuantidade(resultSet.getInt("quantidade"));
                
                products.add(product);
                
            }
            FabricaConexao.fecharConexao();
            return products;
            
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao Buscar Itens!", ex);
        }
    }

 }
    
