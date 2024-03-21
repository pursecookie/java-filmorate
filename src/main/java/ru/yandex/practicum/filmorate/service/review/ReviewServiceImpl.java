package ru.yandex.practicum.filmorate.service.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.component.DataFinder;
import ru.yandex.practicum.filmorate.dao.DataStorageDao;
import ru.yandex.practicum.filmorate.dao.feed.FeedStorageDao;
import ru.yandex.practicum.filmorate.dao.film.FilmStorageDao;
import ru.yandex.practicum.filmorate.dao.review.ReviewStorageDao;
import ru.yandex.practicum.filmorate.dao.user.UserStorageDao;
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
    public ReviewServiceImpl(DataStorageDao<Review> dataStorageDao, DataFinder dataFinder,
                             UserStorageDao userStorageDao,
                             FilmStorageDao filmStorageDao,
                             ReviewStorageDao reviewStorageDao,
                             FeedStorageDao feedStorageDao) {
        super(dataStorageDao, dataFinder);
        this.userStorageDao = userStorageDao;
        this.filmStorageDao = filmStorageDao;
        this.reviewStorageDao = reviewStorageDao;
        this.feedStorageDao = feedStorageDao;
    }

    @Override
    public Review create(Review review) {
        dataFinder.checkDataExists(userStorageDao.getIsExistsQuery(), review.getUserId());
        dataFinder.checkDataExists(filmStorageDao.getIsExistsQuery(), review.getFilmId());
        review.setUseful(0L);

        Review createdReview = super.create(review);

        feedStorageDao.create(createdReview.getUserId(), createdReview.getReviewId(),
                "REVIEW", "ADD");

        return createdReview;
    }

    @Override
    public Review update(Review review) {
        dataFinder.checkDataExists(dataStorageDao.getIsExistsQuery(), review.getReviewId());

        Review updatedReview = dataStorageDao.update(review);

        feedStorageDao.create(updatedReview.getUserId(), updatedReview.getReviewId(),
                "REVIEW", "UPDATE");

        return updatedReview;
    }

    @Override
    public Collection<Review> readReviewsByMovie(Long filmId, Long count) {
        if (filmId != null) {
            dataFinder.checkDataExists(filmStorageDao.getIsExistsQuery(), filmId);
        }

        return reviewStorageDao.readReviewsByMovie(filmId, count);
    }

    @Override
    public void rateReview(long reviewId, long userId, boolean rating) {
        dataFinder.checkDataExists(dataStorageDao.getIsExistsQuery(), reviewId);
        dataFinder.checkDataExists(userStorageDao.getIsExistsQuery(), userId);
        reviewStorageDao.rateReview(reviewId, userId, rating);
    }

    @Override
    public void deleteReviewRating(long reviewId, long userId, boolean rating) {
        dataFinder.checkDataExists(dataStorageDao.getIsExistsQuery(), reviewId);
        dataFinder.checkDataExists(userStorageDao.getIsExistsQuery(), userId);
        reviewStorageDao.deleteReviewRating(reviewId, userId, rating);
    }

    @Override
    public void delete(long reviewId) {
        Review reviewToDelete = read(reviewId);

        super.delete(reviewId);
        feedStorageDao.create(reviewToDelete.getUserId(), reviewId, "REVIEW", "REMOVE");
    }
}