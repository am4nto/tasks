package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class TrelloClient {

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloToken;

    @Value("${trello.app.user}")
    private String trelloUser;

    @Autowired
    private RestTemplate restTemplate;

    private URI url() {
        URI u = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/" + trelloUser + "/boards")
            .queryParam("key", trelloAppKey)
            .queryParam("token", trelloToken)
            .queryParam("fields", "name,id").build().encode().toUri();
        return u;
    }

    public List<Optional<TrelloBoardDto>> getTrelloBoards() {

        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(url(), TrelloBoardDto[].class);
        
        List<Optional<TrelloBoardDto>> trelloBoards = new ArrayList<>();

        for (TrelloBoardDto dto : boardsResponse) {
            trelloBoards.add(Optional.of(dto));
        }

        return trelloBoards;

        //return Arrays.asList(boardsResponse);
/*
        if (boardsResponse != null) {
            return Arrays.asList(boardsResponse);
        }
        return new ArrayList<>();

*/
    }
}
