package cat.tecncampus.productService.persistence;

import cat.tecncampus.productService.application.ProductServicePersistence;
import cat.tecncampus.productService.domain.ProductDoesNotExistException;
import cat.tecncampus.productService.domain.ProductService;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.simpleflatmapper.jdbc.spring.ResultSetExtractorImpl;
import org.simpleflatmapper.jdbc.spring.RowMapperImpl;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Persistence implements ProductServicePersistence {
    private JdbcTemplate jdbcTemplate;

    ResultSetExtractorImpl<ProductService> productsResultSetExtractor =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id")
                    .newResultSetExtractor(ProductService.class);

    RowMapperImpl<ProductService> productRowMapper =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id")
                    .newRowMapper(ProductService.class);



    public Persistence(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ProductService> getAllProducts() {
        final var query = "select * from productService";
        return jdbcTemplate.query(query, productsResultSetExtractor);
    }

    @Override
    public ProductService createProduct(ProductService productService) {
        final var insertProduct = "INSERT INTO productService (id, name, description, weight) VALUES (?, ?,?, ?)";
        jdbcTemplate.update(insertProduct, productService.getId(), productService.getName(), productService.getDescription(), productService.getWeight());
        return getProduct(productService.getId());
    }

    @Override
    public ProductService getProduct(String id) {
        final var query = "select * from productService where id=?";
        try {
            return jdbcTemplate.queryForObject(query, productRowMapper, id);
        } catch (
                EmptyResultDataAccessException e) {
            throw new ProductDoesNotExistException(id);
        }
    }

    @Override
    public void deleteProductService(String id) {
        final String deleteProduct = "DELETE FROM productService where id=?";
        jdbcTemplate.update(deleteProduct, id);

    }
}
