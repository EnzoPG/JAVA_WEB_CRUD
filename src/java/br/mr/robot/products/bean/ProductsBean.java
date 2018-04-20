package br.mr.robot.products.bean;

import br.mr.robot.products.dao.ProductsDAO;
import br.mr.robot.products.entity.Products;
import br.mr.robot.products.util.exception.ErroSistema;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class ProductsBean {

    private Products products = new Products();
    private List<Products> productses = new ArrayList<>();
    private ProductsDAO productsDAO = new ProductsDAO();
    
    public void adicionar(){
        try {
            productses.add(products);
            productsDAO.salvar(products);
            products = new Products();
            adicionarMensagem("Salvo!", "Produto salvo com sucesso.", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    public void listar(){
        try {
            productses = productsDAO.buscar();
            if((productses == null) || productses.size() == 0){
            adicionarMensagem("Nenhum dado encontrado!", "Sua buscar não encontrou nenhum produto.", FacesMessage.SEVERITY_WARN);
        }
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void deletar(Products p){
        try {
            productsDAO.deletar(p.getId());
            adicionarMensagem("Deletado!", "Produto excluído com sucesso.", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema ex) {
            adicionarMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void editar(Products p){
        products = p;
    }
    
    public void adicionarMensagem(String sumario, String detalhe, FacesMessage.Severity tipoErro){
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(tipoErro, sumario, detalhe);
        context.addMessage(null, message);
    }

    public Products getProduct() {
        return products;
    }

    public void setProduct(Products product) {
        this.products = product;
    }

    public List<Products> getProducts() {
        return productses;
    }

    public void setProducts(List<Products> products) {
        this.productses = products;
    }
    
    
}
