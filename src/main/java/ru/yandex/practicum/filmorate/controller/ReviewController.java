package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.service.DataService;
import ru.yandex.practicum.filmorate.service.review.ReviewService;

import java.util.Collection;

@RestController
@RequestMapping("/reviews")
public class ReviewController extends DataController<Review> {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(@Qualifier("reviewServiceImpl") DataService<Review> dataService, ReviewService reviewService) {
        super(dataService);
        this.reviewService = reviewService;
    }

    @Override
    public Collection<Review> readAll(@RequestParam(required = false) Long filmId,
                                      @RequestParam(defaultValue = "10") Long count) {
        return reviewService.readReviewsByMovie(filmId, count);
    }

    @PutMapping("/{reviewId}/like/{userId}")
    public void likeReview(@PathVariable long reviewId, @PathVariable long userId) {
        boolean rating = true;

        reviewService.rateReview(reviewId, userId, rating);
    }

    @PutMapping("/{reviewId}/dislike/{userId}")
    public void dislikeReview(@PathVariable long reviewId, @PathVariable long userId) {
        boolean rating = false;

        reviewService.rateReview(reviewId, userId, rating);
    }

    @DeleteMapping("/{reviewId}/like/{userId}")
    public void deleteLikeFromReview(@PathVariable long reviewId, @PathVariable long userId) {
        boolean rating = true;

        reviewService.deleteReviewRating(reviewId, userId, rating);
    }

    @DeleteMapping("/{reviewId}/dislike/{userId}")
    public void deleteDislikeFromReview(@PathVariable long reviewId, @PathVariable long userId) {
        boolean rating = false;

        reviewService.deleteReviewRating(reviewId, userId, rating);
    }


}
