package com.magr.lolrest.rest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.magr.lolrest.configuration.LolProperties;
import com.magr.lolrest.domain.Summoner;
import com.magr.lolrest.domain.Summoners;
import com.magr.lolrest.rest.dto.MasteryPagesDto;
import com.magr.lolrest.rest.dto.SummonerDto;
import com.magr.lolrest.util.JsonMapper;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class SummonerController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private LolProperties lolProperties;

    @RequestMapping("/summoner/{name}")
    public Summoners summoner(@PathVariable("name") String name) {
        RestTemplate restTemplate = new RestTemplate();
        String responseEntity = restTemplate.getForObject(lolProperties.getBasePath() + "/summoner/by-name/" + name + "?api_key=" + lolProperties.getApiKey(), String.class);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(responseEntity);
            name = name.replaceAll(" ", "");
            String[] names = StringUtils.split(name, ",");
            List<Long> summonerIds = new ArrayList<>();
            List<SummonerDto> summoners = new ArrayList<>();

            for (int i = 0; i < names.length; i++) {
                JSONObject currentSummoner = jsonObject.getJSONObject(names[i].toLowerCase());
                JsonNode jsonNode = JsonMapper.convertJsonFormat(currentSummoner);
                ObjectMapper mapper = new ObjectMapper();
                SummonerDto summoner = mapper.readValue(new TreeTraversingParser(jsonNode), SummonerDto.class);
                summonerIds.add(summoner.getId());
                summoners.add(summoner);
            }

            List<MasteryPagesDto> masteries = getMasteriesById(summonerIds);
            List<Summoner> actualSummoners = new ArrayList<>();
            for (SummonerDto summoner: summoners) {
                for (MasteryPagesDto masteryPages: masteries) {
                    if (summoner.getId() == masteryPages.getSummonerId()) {
                        actualSummoners.add(new Summoner(summoner, masteryPages));
                    }
                }
            }

            return new Summoners(actualSummoners);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<MasteryPagesDto> getMasteriesById(List<Long> summonerIds) {
        String summonerIdsAsString = mapSummonerIdsToString(summonerIds);

        RestTemplate restTemplate = new RestTemplate();
        String responseEntity = restTemplate.getForObject(lolProperties.getBasePath() + "/summoner/" + summonerIdsAsString + "/masteries?api_key=" + lolProperties.getApiKey(), String.class);
        try {
            JSONObject jsonObject = new JSONObject(responseEntity);
            List<MasteryPagesDto> masterPages = new ArrayList<>();
            for (Long summonerId: summonerIds) {
                JSONObject masteryPagesJson = jsonObject.getJSONObject(summonerId.toString());
                JsonNode jsonNode = JsonMapper.convertJsonFormat(masteryPagesJson);
                ObjectMapper mapper = new ObjectMapper();
                System.out.println(jsonNode.toString());
                MasteryPagesDto masteryPagesDto = mapper.readValue(new TreeTraversingParser(jsonNode), MasteryPagesDto.class);
                masterPages.add(masteryPagesDto);
            }

            return masterPages;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String mapSummonerIdsToString(List<Long> summonerIds) {
        return StringUtils.join(summonerIds, ",");
    }
}