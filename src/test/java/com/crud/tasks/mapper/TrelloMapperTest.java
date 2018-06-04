package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMapperTest {

    @Autowired private TrelloMapper mapper;

    @Test
    public void mapToBoards() {
        //Given
        TrelloBoardDto boardDto = new TrelloBoardDto("001", "boardDTO", new ArrayList<>());
        List<TrelloBoardDto>boardDtos = new ArrayList<>();
        boardDtos.add(boardDto);
        //When
        List<TrelloBoard> boards = mapper.mapToBoards(boardDtos);
        //Then
        assertEquals(1, boards.size());
        assertEquals("001", boards.get(0).getId());
        assertEquals("boardDTO", boards.get(0).getName());
    }

    @Test
    public void mapToBoardsDto() {
        //Given
        TrelloBoard board = new TrelloBoard("001", "board", new ArrayList<>());
        List<TrelloBoard> boards = new ArrayList<>();
        boards.add(board);
        //When
        List<TrelloBoardDto> boardDtos = mapper.mapToBoardsDto(boards);
        //Then
        assertEquals(1, boardDtos.size());
        assertEquals("001", boardDtos.get(0).getId());
        assertEquals("board", boardDtos.get(0).getName());
    }

    @Test
    public void mapToList() {
        //Given
        TrelloListDto listDto = new TrelloListDto("001", "listDTO", true);
        List<TrelloListDto> listDtos = new ArrayList<>();
        listDtos.add(listDto);
        //When
        List<TrelloList> lists = mapper.mapToList(listDtos);
        //Then
        assertEquals(1, lists.size());
        assertEquals("001", lists.get(0).getId());
        assertEquals("listDTO", lists.get(0).getName());
        assertTrue(lists.get(0).isClosed());
    }

    @Test
    public void mapToListDto() {
        //Given
        TrelloList list = new TrelloList("001", "list", true);
        List<TrelloList> lists = new ArrayList<>();
        lists.add(list);
        //When
        List<TrelloListDto> listDtos = mapper.mapToListDto(lists);
        //Then
        assertEquals(1, listDtos.size());
        assertEquals("001", listDtos.get(0).getId());
        assertEquals("list", listDtos.get(0).getName());
        assertTrue(listDtos.get(0).isClosed());
    }

    @Test
    public void mapToCardDto() {
        //Given
        TrelloCard card = new TrelloCard("card", "Test card", "1", "001");
        //When
        TrelloCardDto cardDto = mapper.mapToCardDto(card);
        //Then
        assertEquals("card", cardDto.getName());
    }

    @Test
    public void mapToCard() {
        //Given
        TrelloCardDto cardDto = new TrelloCardDto("cardDto", "test Card DTO", "1", "001");
        //When
        TrelloCard card = mapper.mapToCard(cardDto);
        //Then
        assertEquals("cardDto", card.getName());
    }
}