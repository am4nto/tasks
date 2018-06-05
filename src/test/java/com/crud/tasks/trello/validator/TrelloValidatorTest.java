package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
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
public class TrelloValidatorTest {

    @Autowired
    private TrelloValidator validator;

    @Test
    public void validateTestCard() {
        TrelloCard card = new TrelloCard("testCard", "Test card", "1", "1");
        validator.validatecard(card);
    }

    @Test
    public void validateRealCard() {
        TrelloCard card = new TrelloCard("Card", "Real card", "1", "1");
        validator.validatecard(card);
    }

    @Test
    public void validateTrelloBoards() {
        //Given
        List<TrelloBoard> boards = new ArrayList<>();
        boards.add(new TrelloBoard("1", "test", new ArrayList<>()));
        boards.add(new TrelloBoard("2", "board", new ArrayList<>()));

        //When
        List<TrelloBoard> validatedBoards = validator.validateTrelloBoards(boards);

        //Then
        assertEquals(1, validatedBoards.size());
        validatedBoards.forEach(trelloBoard -> {
            assertEquals("2", trelloBoard.getId());
            assertEquals("board", trelloBoard.getName());
        });
    }
}