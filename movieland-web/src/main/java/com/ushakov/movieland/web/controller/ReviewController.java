package com.ushakov.movieland.web.controller;

import com.ushakov.movieland.entity.AnonimusReview;
import com.ushakov.movieland.entity.Review;
import com.ushakov.movieland.service.ReviewService;
import com.ushakov.movieland.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewController {
    private ReviewService reviewService;
    private SecurityService securityService;

    @Autowired
    public ReviewController(ReviewService reviewService, SecurityService securityService) {
        this.reviewService = reviewService;
        this.securityService = securityService;
    }

    @PostMapping(value = "/v1/review", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Review addReview(@RequestHeader(name = "uuid") String uuid, @RequestBody AnonimusReview anonimusReview) {
        Review review = reviewService.addReview(anonimusReview.getMovieId(), securityService.getUser(uuid), anonimusReview.getText());

        if (review == null) {
            throw new InternalServerErrorException("Error happened when creating new review!");
        }

        return review;
    }

    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
