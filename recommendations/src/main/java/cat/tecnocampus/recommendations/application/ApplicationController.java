package cat.tecnocampus.recommendations.application;

import cat.tecnocampus.recommendations.domain.Recommendation;
import cat.tecnocampus.recommendations.application.dao.RecommendationDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationController {
    RecommendationDAO recommendationDAO;

    public ApplicationController(RecommendationDAO recommendationDAO) {
        this.recommendationDAO = recommendationDAO;
    }

    public List<Recommendation> getRecommendation(String productId) {
        return recommendationDAO.getRecommendation(productId);
    }

    public void createRecommendation(Recommendation recommendation) {
        recommendationDAO.createEecommendation(recommendation);
    }

    public void deleteRecommendation(String productId) {
        recommendationDAO.deleteRecommendation(productId);
    }
}
