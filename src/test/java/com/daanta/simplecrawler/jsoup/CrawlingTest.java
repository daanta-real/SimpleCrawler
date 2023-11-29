package com.daanta.simplecrawler.jsoup;

import com.daanta.simplecrawler.SimpleCrawlerApplication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest(classes = SimpleCrawlerApplication.class)
class CrawlingTest {

    @Deprecated
    // Map<String, String> 인스턴스가 업캐스팅되어 Object로 포장되어 있을 경우,
    // 이것을 다시 Map<String, String>형으로 원복시켜 돌려준다.
    private static Map<String, String> convertObjToStringMap(Object orgMap) {
        return orgMap instanceof Map<?, ?> map
            ? map.entrySet().stream().collect(Collectors.toMap(
                    entry -> entry.getKey().toString(),
                    entry -> entry.getValue() != null
                            ? entry.getValue().toString()
                            : ""
                ))
            : null;
    }

    @Autowired
    ObjectMapper objectMapper;

    public Map<String, Object> prepareMap() {
        String jsonString = """
                {
                    "requestURL": "https://cafe.naver.com/ArticleList.nhn?search.clubid=10050146&search.menuid=433&search.boardtype=L&search.totalCount=151&search.cafeId=10050146&search.page=1",
                    "header": {
                        "똥": "뿌직"
                    },
                    "cssSelectionQuery": "body"
                }
                """;
        Map<String, Object> map;
        try {
            map = objectMapper.readValue(jsonString, new TypeReference<>() {});
        } catch(JsonProcessingException e) {
            map = null;
        }
        return map;
    }

    public Elements getHTMLResponseElement(Map<String, Object> m) throws Exception {

        // URL
        String requestURL = Optional.ofNullable(m.get("requestURL"))
                .map(Object::toString)
                .orElse("");

        // HEADER
        Map<String, String> header = objectMapper.convertValue(m.get("header"), new TypeReference<>() {});

        // GO!
        Connection.Response response = Jsoup.connect(requestURL).execute();
        Document doc = Jsoup.parse(response.body());
        String cssSelectionQuery = objectMapper.convertValue(m.get("cssSelectionQuery"), new TypeReference<>() {});
        return doc.select(cssSelectionQuery);

    }

    @Test
    void getElementTest() throws Exception {
        Map<String, Object> map = prepareMap();
        assert map != null;
        Elements result = getHTMLResponseElement(map);
        log.debug("종료!");
    }

}
