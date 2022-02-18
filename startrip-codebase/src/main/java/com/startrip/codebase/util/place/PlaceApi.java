package com.startrip.codebase.util.place;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.startrip.codebase.domain.place.Place;
import com.startrip.codebase.util.OpenApiUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class PlaceApi {
    @Autowired // 의존성 주입
    OpenApiUtil openApiUtil;

    public String getApiResult(String date) throws IOException, URISyntaxException {
        ArrayList<LinkedHashMap> items = get(date);
        List<String> dataInfo = parse(items);
        return dataInfo.toString();
    }

    public ArrayList<LinkedHashMap> get(String date) throws IOException, URISyntaxException {
        StringBuilder urlBuilder = new StringBuilder("http://api.visitkorea.or.kr/openapi/service/rest/DataLabService/tmapTotalTarItsBroDDList");
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + openApiUtil.getDATA_GO_KR_API_KEY());
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("baseYm", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8"));

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(URI.create(urlBuilder.toString()), HttpMethod.GET, entity, Map.class);

        ObjectMapper mapper = new ObjectMapper();
        // TODO : 최근 1일 조회 예외처리 잡기
        String jsonInString = mapper.writeValueAsString(response.getBody().get("response"));

        // 추출
        LinkedHashMap responseResult = (LinkedHashMap) ((LinkedHashMap) response.getBody().get("response")).get("body");
        LinkedHashMap bodyResult = (LinkedHashMap) responseResult.get("items");
        ArrayList<LinkedHashMap> items = (ArrayList<LinkedHashMap>) bodyResult.get("item");

        return items;
    }

    private List<String> parse(ArrayList<LinkedHashMap> items) {
        try {
            Place place = new Place();
            List<String> dataInfos = new ArrayList<>();

            for (LinkedHashMap item : items) {
                List<String> temp = new ArrayList<>();

                String dtlAddrNm = item.get("dtlAddrNm").toString();
                String itsBroNm = item.get("itsBroNm").toString();

                temp.add(dtlAddrNm);
                temp.add(itsBroNm);
                dataInfos.add(String.valueOf(temp));

                place.setAddress(dtlAddrNm);
                place.setPlaceName(itsBroNm);
            }
            return dataInfos;
        } catch (Exception e) {
            //log.info(e.getMessage());
            return null;
        }
    }
}
