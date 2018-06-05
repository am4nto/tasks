package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTest {
    @InjectMocks
    private TrelloService service;

    @Mock
    private AdminConfig adminConfig;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService emailService;

    @Test
    public void ShoultFetchTrelloBoards() {
        //Given
        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloListDtos.add(new TrelloListDto("1", "test_list", false));

        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(new TrelloBoardDto("1", "test", trelloListDtos));

        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardDtos);

        //When
        List<TrelloBoardDto>fetchedTrelloBoardDtos = service.fetchTrelloBoards();

        //Then
        assertNotNull(fetchedTrelloBoardDtos);
        assertEquals(1, fetchedTrelloBoardDtos.size());
        trelloBoardDtos.forEach(trelloBoardDto -> {
            assertEquals("1", trelloBoardDto.getId());
            assertEquals("test", trelloBoardDto.getName());

            trelloBoardDto.getLists().forEach(trelloListDto -> {
                assertEquals("1", trelloListDto.getId());
                assertEquals("test_list", trelloListDto.getName());
                assertEquals(false, trelloListDto.isClosed());
            });
        });

    }

    @Test
    public void ShoultFetchEmptyList() {
        //Given
        when(trelloClient.getTrelloBoards()).thenReturn(new ArrayList<>());

        //When
        List<TrelloBoardDto>fetchedTrelloBoardDtos = service.fetchTrelloBoards();

        //Then
        assertNotNull(fetchedTrelloBoardDtos);
        assertEquals(0, fetchedTrelloBoardDtos.size());

    }

    @Test
    public void ShouldcreateCard() {
        //Given
        Trello trello = new Trello();
        AttachmentByType attachment = new AttachmentByType();
        Badges badges = new Badges();
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto();
        TrelloCardDto cardDto = new TrelloCardDto("test", "test card", "1", "01");

        trello.setBoard(1);
        trello.setCard(2);
        attachment.setTrello(trello);
        badges.setAttachments(attachment);
        badges.setVotes(1);
        createdTrelloCardDto.setId("1");
        createdTrelloCardDto.setName("test");
        createdTrelloCardDto.setShortUrl("url");
        createdTrelloCardDto.setBadges(badges);

        when(trelloClient.createNewCard(cardDto)).thenReturn(createdTrelloCardDto);

        //When
        CreatedTrelloCardDto createdCardDto = service.createTrelloCard(cardDto);

        //Then
        assertEquals("1", createdCardDto.getId());
        assertEquals("test", createdCardDto.getName());
        assertEquals("url", createdCardDto.getShortUrl());
        assertEquals(1, createdCardDto.getBadges().getVotes());
        assertEquals(1, createdCardDto.getBadges().getAttachments().getTrello().getBoard());
        assertEquals(2, createdCardDto.getBadges().getAttachments().getTrello().getCard());

    }
}