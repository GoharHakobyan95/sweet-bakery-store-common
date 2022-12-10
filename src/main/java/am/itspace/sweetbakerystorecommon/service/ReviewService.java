package am.itspace.sweetbakerystorecommon.service;

import am.itspace.sweetbakerystorecommon.entity.Review;
import am.itspace.sweetbakerystorecommon.entity.User;
import am.itspace.sweetbakerystorecommon.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public Page<Review> findPaginated(Pageable pageable) {
        return reviewRepository.findAll(pageable);
    }

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public Review save(Review review, User user) {
        review.setDate(new Date());
        review.setUser(user);
        return  reviewRepository.save(review);
    }
}
