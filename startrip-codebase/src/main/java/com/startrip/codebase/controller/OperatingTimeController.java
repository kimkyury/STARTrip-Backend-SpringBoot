package com.startrip.codebase.controller;

import com.startrip.codebase.domain.Operating_time.OperatingTime;
import com.startrip.codebase.dto.operatingTime.RequestOptimeDto;
import com.startrip.codebase.dto.operatingTime.ResponseOptimeDto;
import com.startrip.codebase.service.OperatingTimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/api/place")
public class OperatingTimeController {

     private final OperatingTimeService operatingTimeService;

     public OperatingTimeController(OperatingTimeService operatingTimeService ) {
         this.operatingTimeService = operatingTimeService;
     }

    // CREATE
    @PostMapping("/optime")
    public ResponseEntity createOpTime(RequestOptimeDto dto){
        operatingTimeService.createOpTime(dto);
         return  new ResponseEntity<>("운영시간 생성",HttpStatus.OK);
    }

    // GET : api/place/optime?palceId={placeId}  // 특정 장소의 모든 op_time 보기
    @GetMapping (path = "/optime/list", params="placeid")
    public ResponseEntity getOpTimeAll_inSpecificPlace(@RequestParam(value="placeid") Long placeId ){
       List<OperatingTime> operatingTimes;
        try {
            operatingTimes = operatingTimeService.getOptimeAll(placeId);
        }catch(Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(operatingTimes, HttpStatus.OK);
    }


    // GET api/place/optime?placeId={placeId}&datetime={datetime} // 특정 시간의 특정장소 op_time 보기
    @GetMapping (path= "/optime", params = {"placeId", "date"})
    public ResponseEntity getOpTimeAll_inSpecificTime(@RequestParam(value = "placeid") Long placeId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        //log.info(String.valueOf(date));

        OperatingTime optime;
        try{
            optime = operatingTimeService.getOpTimeDatetime(placeId, date);

        }catch(Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity(optime, HttpStatus.OK);
    }

    // UPDATE opTime
    @PutMapping ("/optime/{optimeId}")
    public ResponseEntity updateOpTime( @PathVariable("optimeId") UUID optimeId, RequestOptimeDto dto){
        try {
            operatingTimeService.updateOptime(optimeId, dto);
        }catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("운영시간 수정완료", HttpStatus.OK);
    }


    // DELETE opTime
    @DeleteMapping("/optime/{optimeId}")
    public ResponseEntity deleteOpTime(@PathVariable("optimeId") UUID optimeId){
        try {
            operatingTimeService.deleteOptime(optimeId);
        }catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("운영시간 삭제완료", HttpStatus.OK);
    }
}
