package com.startrip.codebase.service;

import com.startrip.codebase.domain.place.Place;
import com.startrip.codebase.domain.place.PlaceRepository;

import com.startrip.codebase.dto.PlaceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlaceService {

    private PlaceRepository placeRepository;

    @Autowired
    public PlaceService(PlaceRepository placeRepository){
        this.placeRepository = placeRepository;
    }

    public List<Place> allPlace() {
        return placeRepository.findAll();
    }

    public Place getPlace(UUID id) {
        Optional<Place> place = placeRepository.findById(id);
        if (place.isEmpty()) {
            throw new RuntimeException("해당 장소가 없습니다");
        }
        return place.get();
    }

    public void createPlace(PlaceDto dto) {
        Place place = Place.of(dto);
        placeRepository.save(place);
    }

    public void updatePlace(UUID id, PlaceDto dto){
        Place place = getPlace(id);
        place.update(dto);
        placeRepository.save(place);
    }

    public void deletePlace(UUID id) {
        placeRepository.deleteById(id);
    }

}

