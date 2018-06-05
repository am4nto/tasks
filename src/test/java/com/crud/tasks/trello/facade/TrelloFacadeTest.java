package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.client.TrelloClient;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloFacadeTest {

    @InjectMocks
    private TrelloFacade trelloFacade;

    @Mock
    private TrelloService trelloService;

    @Mock
    private TrelloValidator trelloValidator;

    @Mock
    private TrelloMapper trelloMapper;

    @Mock
    private TrelloClient trelloClient;

    @Test
    public void shoultFetchEmptyList() {
        //Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1", "test_list", false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1", "test", trelloLists));

        List<TrelloList> mappedTrelloLists = new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("1", "test_list", false));

        List<TrelloBoard> mappedtrelloBoards = new ArrayList<>();
        mappedtrelloBoards.add(new TrelloBoard("1", "test", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedtrelloBoards);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(new ArrayList());
        when(trelloValidator.validateTrelloBoards(mappedtrelloBoards)).thenReturn(new ArrayList<>());

        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();

        //Then
        assertNotNull(trelloBoardDtos);
        assertEquals(0, trelloBoardDtos.size());
    }

    @Test
    public void shouldFetchTrelloBoards() {

        //Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1", "my_list", false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1", "my_task", trelloLists));

        List<TrelloList> mappedTrelloLists = new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("1", "my_list", false));

        List<TrelloBoard> mappedtrelloBoards = new ArrayList<>();
        mappedtrelloBoards.add(new TrelloBoard("1", "my_task", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedtrelloBoards);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(trelloBoards);
        when(trelloValidator.validateTrelloBoards(mappedtrelloBoards)).thenReturn(mappedtrelloBoards);

        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();

        //Then
        assertNotNull(trelloBoardDtos);
        assertEquals(1, trelloBoardDtos.size());

        trelloBoardDtos.forEach(trelloBoardDto -> {
            assertEquals("1", trelloBoardDto.getId());
            assertEquals("my_task", trelloBoardDto.getName());

            trelloBoardDto.getLists().forEach(trelloListDto -> {
                assertEquals("1", trelloListDto.getId());
                assertEquals("my_list", trelloListDto.getName());
                assertEquals(false, trelloListDto.isClosed());
            });
        });
    }
    @Test
    public void ShouldCreateCard() {
        //Given
        TrelloCardDto cardDto = new TrelloCardDto("testCardDto", "Trello Card DTO", "1", "01");
        TrelloCard card = new TrelloCard("testCardDto", "Trello Card DTO", "1", "01");
        AttachmentByType attachment = new AttachmentByType(new Trello(1, 2));
        Badges badges = new Badges(1, attachment);
        when(trelloMapper.mapToCard(cardDto)).thenReturn(card);
        when(trelloMapper.mapToCardDto(card)).thenReturn(cardDto);
        when(trelloService.createTrelloCard(cardDto)).thenReturn(new CreatedTrelloCardDto("1", "created card", "some url", badges));

        //When
        CreatedTrelloCardDto createdCardDto = trelloFacade.createCard(cardDto);

        assertEquals("1", createdCardDto.getId());
        assertEquals("created card", createdCardDto.getName());
        assertEquals("some url", createdCardDto.getShortUrl());
        assertEquals(1, createdCardDto.getBadges().getVotes());
        assertEquals(1, createdCardDto.getBadges().getAttachments().getTrello().getBoard());
        assertEquals(2, createdCardDto.getBadges().getAttachments().getTrello().getCard());
    }

    @Test
    public void ShouldCreateEmptyCard() {
        TrelloCardDto cardDto = new TrelloCardDto("testCardDto", "Trello Card DTO", "1", "01");
        TrelloCard card = new TrelloCard("testCardDto", "Trello Card DTO", "1", "01");

        when(trelloMapper.mapToCard(cardDto)).thenReturn(card);
        when(trelloMapper.mapToCardDto(card)).thenReturn(cardDto);
        when(trelloClient.createNewCard(cardDto)).thenReturn(null);

        //When
        CreatedTrelloCardDto createdCardDto = trelloFacade.createCard(cardDto);

        //Then
        assertNull(createdCardDto);
    }



}