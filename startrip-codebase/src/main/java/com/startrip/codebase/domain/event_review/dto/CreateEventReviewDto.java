package com.startrip.codebase.domain.event_review.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CreateEventReviewDto {
    private String eventReviewTitle;

    private String text;

    private Double reviewRate;

}
