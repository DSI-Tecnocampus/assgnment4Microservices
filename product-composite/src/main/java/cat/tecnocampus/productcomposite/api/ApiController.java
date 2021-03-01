package cat.tecnocampus.productcomposite.api;

import cat.tecnocampus.productcomposite.application.ApplicationController;
import cat.tecnocampus.productcomposite.domain.ProductComposite;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiController {
    private ApplicationController applicationController;

    public ApiController(ApplicationController applicationController) {
        this.applicationController = applicationController;
    }

    @GetMapping("/products")
    public List<ProductComposite> getProducts() {
        return applicationController.getProductsComposite();
    }

    @GetMapping("/products/{id}")
    public ProductComposite getProduct(@PathVariable String id) {
        return applicationController.getProductsComposite(id);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable String id) {
        applicationController.deleteProductComposite(id);
    }

    @PostMapping("/product")
    public void createProduct(@RequestBody ProductComposite productComposite) {
        System.out.println("name: " + productComposite.getProduct().getName());
        applicationController.createProductComposite(productComposite);
    }
}
