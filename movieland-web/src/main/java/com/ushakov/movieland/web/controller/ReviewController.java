package com.ushakov.movieland.web.controller;

import com.ushakov.movieland.common.UserRole;
import com.ushakov.movieland.common.ReviewRequest;
import com.ushakov.movieland.entity.Review;
import com.ushakov.movieland.service.ReviewService;
import com.ushakov.movieland.service.SecurityService;
import com.ushakov.movieland.web.interceptor.ProtectedBy;
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

    @ProtectedBy({UserRole.USER, UserRole.ADMIN})
    @PostMapping(value = "/v1/review", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Review addReview(@RequestHeader(name = "uuid") String uuid, @RequestBody ReviewRequest reviewRequest) {
        reviewRequest.setUser(securityService.getUser(uuid));
        Review review = reviewService.addReview(reviewRequest);

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
