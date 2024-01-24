package ru.yandex.practicum.filmorate.service.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.DataStorageDao;
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

    @Autowired
    public ReviewServiceImpl(DataStorageDao<Review> dataStorageDao,
                             UserStorageDao userStorageDao,
                             FilmStorageDao filmStorageDao,
                             ReviewStorageDao reviewStorageDao) {
        super(dataStorageDao);
        this.userStorageDao = userStorageDao;
        this.filmStorageDao = filmStorageDao;
        this.reviewStorageDao = reviewStorageDao;
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

        return super.create(review);
    }

    @Override
    public Review update(Review review) {
        if (dataStorageDao.isExists(review.getReviewId())) {
            return dataStorageDao.update(review);
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

}