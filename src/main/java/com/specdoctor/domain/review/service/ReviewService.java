package com.specdoctor.domain.review.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.specdoctor.domain.activity.entity.Activity;
import com.specdoctor.domain.activity.repository.ActivityRepository;
import com.specdoctor.domain.review.dto.ReviewCreateRequest;
import com.specdoctor.domain.review.dto.ReviewResponse;
import com.specdoctor.domain.review.entity.Review;

import com.specdoctor.domain.review.repository.ReviewRepository;
import com.specdoctor.domain.user.entity.User;
import com.specdoctor.domain.user.exception.UserNotFoundException;
import com.specdoctor.domain.user.repository.UserRepository;
import com.specdoctor.global.error.ErrorCode;
import com.specdoctor.global.error.exception.ServiceException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final UserRepository userRepository;
	private final ActivityRepository activityRepository;

	public void create(Long userId, ReviewCreateRequest request) {
		User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
		Activity activity = activityRepository.findByName(request.name())
			.orElseThrow(() -> new ServiceException(ErrorCode.ACTIVITY_NOT_FOUND));

		Review review = Review.builder()
			.writer(user)
			.activity(activity)
			.review(request.review())
			.star(request.star())
			.role(request.role())
			.build();

		reviewRepository.save(review);
	}

	public List<ReviewResponse> findAll(String activityName) {
		return reviewRepository.findAllByActivityName(activityName).stream()
			.map(ReviewResponse::from)
			.toList();
	}
}
