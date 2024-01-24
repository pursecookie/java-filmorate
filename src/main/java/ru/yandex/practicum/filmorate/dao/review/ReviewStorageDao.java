package ru.yandex.practicum.filmorate.dao.review;

import ru.yandex.practicum.filmorate.dao.DataStorageDao;
import ru.yandex.practicum.filmorate.model.Review;

import java.util.Collection;

public interface ReviewStorageDao extends DataStorageDao<Review> {
    Collection<Review> readReviewsByMovie(Long filmId, Long count);

    void rateReview(long reviewId, long userId, boolean rating);

    void deleteReviewRating(long reviewId, long userId, boolean rating);
}
