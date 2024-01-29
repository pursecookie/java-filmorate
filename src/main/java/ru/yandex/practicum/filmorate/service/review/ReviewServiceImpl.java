package ru.yandex.practicum.filmorate.service.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.DataStorageDao;
import ru.yandex.practicum.filmorate.dao.feed.FeedStorageDao;
import ru.yandex.practicum.filmorate.dao.film.FilmStorageDao;
import ru.yandex.practicum.filmorate.dao.review.ReviewStorageDao;
import ru.yandex.practicum.filmorate.dao.user.UserStorageDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.service.DataServiceImpl;

import java.util.Collection;

@Service
public class ReviewServiceImpl extends DataServiceImpl<Review> implements ReviewService {
    private final UserStorageDao userStorageDao;
    private final FilmStorageDao filmStorageDao;
    private final ReviewStorageDao reviewStorageDao;
    private final FeedStorageDao feedStorageDao;

    @Autowired
    public ReviewServiceImpl(DataStorageDao<Review> dataStorageDao,
                             UserStorageDao userStorageDao,
                             FilmStorageDao filmStorageDao,
                             ReviewStorageDao reviewStorageDao,
                             FeedStorageDao feedStorageDao) {
        super(dataStorageDao);
        this.userStorageDao = userStorageDao;
        this.filmStorageDao = filmStorageDao;
        this.reviewStorageDao = reviewStorageDao;
        this.feedStorageDao = feedStorageDao;
    }

    @Override
    public Review create(Review review) {
        if (!userStorageDao.isExists(review.getUserId())) {
            throw new NotFoundException("Пользователь с id " + review.getUserId() + " не найден");
        }

        if (!filmStorageDao.isExists(review.getFilmId())) {
            throw new NotFoundException("Фильм с id " + review.getFilmId() + " не найден");
        }

        review.setUseful(0L);

        Review createdReview = super.create(review);

        feedStorageDao.create(createdReview.getUserId(), createdReview.getReviewId(),
                "REVIEW", "ADD");

        return createdReview;
    }

    @Override
    public Review update(Review review) {
        if (dataStorageDao.isExists(review.getReviewId())) {
            Review updatedReview = dataStorageDao.update(review);

            feedStorageDao.create(updatedReview.getUserId(), updatedReview.getReviewId(),
                    "REVIEW", "UPDATE");

            return updatedReview;
        } else {
            throw new NotFoundException("Данные с id " + review.getReviewId() + " не найдены");
        }
    }

    @Override
    public Collection<Review> readReviewsByMovie(Long filmId, Long count) {
        if (filmId != null) {
            if (!filmStorageDao.isExists(filmId)) {
                throw new NotFoundException("Данные с id " + filmId + " не найдены");
            }
        }

        return reviewStorageDao.readReviewsByMovie(filmId, count);
    }

    @Override
    public void rateReview(long reviewId, long userId, boolean rating) {
        if (!dataStorageDao.isExists(reviewId)) {
            throw new NotFoundException("Отзыв с id " + reviewId + " не найден");
        }

        if (!userStorageDao.isExists(userId)) {
            throw new NotFoundException("Пользователь с id " + userId + " не найден");
        }

        reviewStorageDao.rateReview(reviewId, userId, rating);
    }

    @Override
    public void deleteReviewRating(long reviewId, long userId, boolean rating) {
        if (!dataStorageDao.isExists(reviewId)) {
            throw new NotFoundException("Отзыв с id " + reviewId + " не найден");
        }

        if (!userStorageDao.isExists(userId)) {
            throw new NotFoundException("Пользователь с id " + userId + " не найден");
        }

        reviewStorageDao.deleteReviewRating(reviewId, userId, rating);
    }

    @Override
    public void delete(long reviewId) {
        Review reviewToDelete = read(reviewId);

        super.delete(reviewId);
        feedStorageDao.create(reviewToDelete.getUserId(), reviewId, "REVIEW", "REMOVE");
    }
}