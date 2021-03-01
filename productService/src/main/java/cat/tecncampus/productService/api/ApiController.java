package cat.tecncampus.productService.api;


import cat.tecncampus.productService.application.ProductServicePersistence;
import cat.tecncampus.productService.domain.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ApiController {

    private ProductServicePersistence persistence;

    public ApiController(ProductServicePersistence persistence) {
        this.persistence = persistence;
    }

    @GetMapping("/products")
    public List<ProductService> getProducts() {
        return persistence.getAllProducts();
    }

    @GetMapping("/products/{id}")
    public ProductService getProduct(@PathVariable String id){
        return persistence.getProduct(id);
    }

    @PostMapping("/products")
    public ProductService createProduct(@RequestBody ProductService productService) {
        System.out.println("name: " + productService.getName() + " description: " + productService.getDescription() + " weight: "+ productService.getWeight());
        productService.setId(UUID.randomUUID().toString());
        return persistence.createProduct(productService);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable String id){
        persistence.deleteProductService(id);
    }


}
