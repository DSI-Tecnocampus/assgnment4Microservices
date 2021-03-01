package cat.tecncampus.productService.application;



import cat.tecncampus.productService.domain.ProductService;

import java.util.List;

public interface ProductServicePersistence {
    public List<ProductService> getAllProducts();

    public ProductService createProduct(ProductService productService);

    public ProductService getProduct(String id);

    public void deleteProductService(String id);
}
