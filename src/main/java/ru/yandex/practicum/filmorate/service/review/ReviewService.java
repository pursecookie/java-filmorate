package ru.yandex.practicum.filmorate.service.review;

import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.service.DataService;

import java.util.Collection;

public interface ReviewService extends DataService<Review> {
    Collection<Review> readReviewsByMovie(Long filmId, Long count);

    void rateReview(long reviewId, long userId, boolean rating);

    void deleteReviewRating(long reviewId, long userId, boolean rating);
}
