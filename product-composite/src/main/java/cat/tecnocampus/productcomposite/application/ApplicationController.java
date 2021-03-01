package cat.tecnocampus.productcomposite.application;

import cat.tecnocampus.productcomposite.domain.Product;
import cat.tecnocampus.productcomposite.domain.ProductComposite;
import cat.tecnocampus.productcomposite.domain.Recommendation;
import cat.tecnocampus.productcomposite.domain.Review;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;

@Service
public class ApplicationController {
    private RestTemplate restTemplate;
    private String productServiceUrl;
    private String recommendationServiceUrl;
    private String reviewServiceUrl;


    public ApplicationController(RestTemplate restTemplate,
                                 @Value("${app.product-service.host}") String productServiceHost,
                                 @Value("${app.product-service.port}") int productServicePort,
                                 @Value("${app.recommendation-service.host}") String recommendationServiceHost,
                                 @Value("${app.recommendation-service.port}") int recommendationServicePort,
                                 @Value("${app.review-service.host}") String reviewServiceHost,
                                 @Value("${app.review-service.port}") int reviewServicePort) {
        this.restTemplate = restTemplate;
        productServiceUrl = "http://" + productServiceHost + ":" + productServicePort + "/products";
        recommendationServiceUrl = "http://" + recommendationServiceHost + ":" + recommendationServicePort + "/recommendations";
        reviewServiceUrl = "http://" + reviewServiceHost + ":" + reviewServicePort + "/review";
    }

    public void createProductComposite(ProductComposite productComposite){
        Product product = productComposite.getProduct();
        List<Recommendation> recommendations = productComposite.getRecommendations();
        List<Review> reviews = productComposite.getReviews();

        product = restTemplate.postForObject(productServiceUrl, product, Product.class);
        for(Recommendation r: recommendations){
            r.setProductId(product.getId());
            restTemplate.postForObject(recommendationServiceUrl, r, String.class);
        }
        for(Review r: reviews){
            r.setProductId(product.getId());
            restTemplate.postForObject(reviewServiceUrl, r, String.class);
        }

    }

    public List<ProductComposite> getProductsComposite(){
        var product = restTemplate.exchange(productServiceUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {});

        List<ProductComposite> productComposites = new LinkedList<ProductComposite>();

        for(Product p: product.getBody()){
            var recommendation = restTemplate.exchange(recommendationServiceUrl + "/" + p.getId(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Recommendation>>() {});
            var review = restTemplate.exchange(reviewServiceUrl + "/" + p.getId(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Review>>() {});
            productComposites.add(new ProductComposite(p, review.getBody(), recommendation.getBody()));
        }

        return  productComposites;
    }

    public ProductComposite getProductsComposite(String id){
        var product = restTemplate.exchange(productServiceUrl + "/"+ id, HttpMethod.GET, null, new ParameterizedTypeReference<Product>() {});
        var recommendation = restTemplate.exchange(recommendationServiceUrl + "/"+ id, HttpMethod.GET, null, new ParameterizedTypeReference<List<Recommendation>>() {});
        var review = restTemplate.exchange(reviewServiceUrl + "/"+ id, HttpMethod.GET, null, new ParameterizedTypeReference<List<Review>>() {});

        return new ProductComposite(product.getBody(), review.getBody(), recommendation.getBody());
    }

    public void deleteProductComposite(String id) {
        var product = restTemplate.exchange(productServiceUrl + "/"+ id, HttpMethod.DELETE, null, new ParameterizedTypeReference<List<Product>>() {});
        var recommendation = restTemplate.exchange(recommendationServiceUrl + "/"+ id, HttpMethod.DELETE, null, new ParameterizedTypeReference<List<Recommendation>>() {});
        var review = restTemplate.exchange(reviewServiceUrl + "/"+ id, HttpMethod.DELETE, null, new ParameterizedTypeReference<List<Review>>() {});
    }
}