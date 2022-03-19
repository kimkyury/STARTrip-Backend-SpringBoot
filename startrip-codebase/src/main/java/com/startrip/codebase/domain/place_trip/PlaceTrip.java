package com.startrip.codebase.domain.place_trip;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class PlaceTrip {
    @Id
    @Column(name = "trip_id")
    private UUID tripId = UUID.randomUUID();

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_partner")
    private String userPartner;

    @Column(name = "place_id")
    private UUID placeId = UUID.randomUUID();

    private Date start_time;

    private Date end_time;

    private String state;

    private String transportation;

    private String title;
}
