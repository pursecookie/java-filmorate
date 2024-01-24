package ru.yandex.practicum.filmorate.dao.review;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.DataStorageDaoImpl;
import ru.yandex.practicum.filmorate.mapper.ReviewMapper;
import ru.yandex.practicum.filmorate.model.Review;

import java.util.Collection;
import java.util.stream.Collectors;

@Repository
public class ReviewStorageDaoImpl extends DataStorageDaoImpl<Review> implements ReviewStorageDao {
    public ReviewStorageDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    protected String getInsertQuery() {
        return "INSERT INTO reviews (content, is_positive, user_id, film_id, useful) VALUES (?,?,?,?,?)";
    }

    protected String getSelectQuery() {
        return "SELECT r.review_id, r.content, r.is_positive, r.user_id, r.film_id, r.useful " +
                "FROM reviews AS r " +
                "WHERE r.review_id = ?";
    }

    protected String getSelectAllQuery() {
        return "SELECT r.review_id, r.content, r.is_positive, r.user_id, r.film_id, r.useful " +
                "FROM reviews AS r";
    }

    protected String getUpdateQuery() {
        return "UPDATE reviews SET content = ?, is_positive = ? " +
                "WHERE review_id = ?";
    }

    protected String getDeleteQuery() {
        return "DELETE FROM reviews WHERE review_id = ?";
    }

    protected RowMapper<Review> getMapper() {
        return new ReviewMapper();
    }

    @Override
    public Review create(Review review) {
        jdbcTemplate.update(getInsertQuery(),
                review.getContent(),
                review.getIsPositive(),
                review.getUserId(),
                review.getFilmId(),
                review.getUseful());

        return jdbcTemplate.queryForObject("SELECT r.review_id, r.content, r.is_positive, r.user_id, r.film_id, r.useful " +
                "FROM reviews AS r " +
                "ORDER BY r.review_id DESC " +
                "LIMIT 1", getMapper());
    }

    @Override
    public Review update(Review review) {
        jdbcTemplate.update(getUpdateQuery(), review.getContent(),
                review.getIsPositive(),
                review.getReviewId());

        return read(review.getReviewId());
    }

    @Override
    public Collection<Review> readReviewsByMovie(Long filmId, Long count) {
        String queryForOneFilm = "SELECT r.review_id, r.content, r.is_positive, r.user_id, r.film_id, r.useful " +
                "FROM reviews AS r " +
                "WHERE r.film_id = ? " +
                "ORDER BY r.useful DESC " +
                "LIMIT ?";

        String queryForAllFilms = "SELECT r.review_id, r.content, r.is_positive, r.user_id, r.film_id, r.useful " +
                "FROM reviews AS r " +
                "ORDER BY r.useful DESC " +
                "LIMIT ?";

        if (filmId == null) {
            return jdbcTemplate.queryForStream(queryForAllFilms, getMapper(), count)
                    .collect(Collectors.toList());
        }

        return jdbcTemplate.queryForStream(queryForOneFilm, getMapper(), filmId, count)
                .collect(Collectors.toList());
    }

    @Override
    public void rateReview(long reviewId, long userId, boolean rating) {
        String query = "INSERT INTO reviews_likes (review_id, user_id, rating) VALUES (?,?,?)";

        if (rating) {
            jdbcTemplate.update(query, reviewId, userId, true);
            updateUseful(reviewId, 1);
        } else {
            jdbcTemplate.update(query, reviewId, userId, false);
            updateUseful(reviewId, -1);
        }
    }

    @Override
    public void deleteReviewRating(long reviewId, long userId, boolean rating) {
        String query = "DELETE FROM reviews_likes WHERE review_id = ? AND user_id = ? AND rating = ?";

        if (rating) {
            jdbcTemplate.update(query, reviewId, userId, true);
            updateUseful(reviewId, -1);
        } else {
            jdbcTemplate.update(query, reviewId, userId, false);
            updateUseful(reviewId, 1);
        }
    }

    private void updateUseful(long reviewId, long useful) {
        Long currentUseful = jdbcTemplate.queryForObject("SELECT r.useful " +
                "FROM reviews AS r " +
                "WHERE r.review_id = "
                + reviewId, Long.class);

        String query = "UPDATE reviews SET useful = ? " +
                "WHERE review_id = ?";

        jdbcTemplate.update(query, currentUseful + useful, reviewId);
    }
}