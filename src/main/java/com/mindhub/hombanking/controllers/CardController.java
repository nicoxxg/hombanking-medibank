package com.mindhub.hombanking.controllers;


import com.mindhub.hombanking.DTO.CardDTO;
import com.mindhub.hombanking.DTO.ClientDTO;
import com.mindhub.hombanking.models.Card;
import com.mindhub.hombanking.models.CardColor;
import com.mindhub.hombanking.models.CardType;
import com.mindhub.hombanking.models.Client;
import com.mindhub.hombanking.repository.CardRepository;
import com.mindhub.hombanking.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    CardRepository cardRepository;
    @Autowired
    ClientRepository clientRepository;

    int min = 100;
    int max = 999;
    int minCard = 1000;
    int maxCard = 9999;
    Random random = new Random();


    @GetMapping("/cards")
    public List<CardDTO> getCards(){
        return cardRepository.findAll().stream().map(card -> new CardDTO(card)).collect(Collectors.toList());
    }

    @GetMapping("/cards/{id}")
    public CardDTO getCardById(@PathVariable long id){
        return cardRepository.findById(id).map( card -> new CardDTO(card)).orElse(null);
    }
    @GetMapping("/clients/current/cards")
    public Set<CardDTO> cardDTOS(Authentication authentication){
        Client current = clientRepository.findByEmail(authentication.getName());
        List<Card> activedCard = current.getCards().stream().filter(card -> card.isExistCard()).collect(Collectors.toList());
        return activedCard.stream().map(card -> new CardDTO(card)).collect(Collectors.toSet());
    }
    @PostMapping(path = "/clients/current/cards")
    public ResponseEntity<Object> createCards(Authentication authentication, @RequestParam CardColor color, @RequestParam CardType type){

        Client current = clientRepository.findByEmail(authentication.getName());
        Set<Card> cards = current.getCards();
        List<Card> activedCard = cards.stream().filter(card -> card.isExistCard() == true).collect(Collectors.toList());
        List<Card> cardsType = activedCard.stream().filter(card ->  card.getType() == type).collect(Collectors.toList());
        if (color == null || type == null) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if(cardsType.size() >= 3){
            return new ResponseEntity<>("you already have 3 cards of that type created", HttpStatus.FORBIDDEN);
        }
        String number;

        do{
            number = random.ints(minCard, maxCard).findFirst().getAsInt()+"-"+random.ints(minCard, maxCard).findFirst().getAsInt()+"-"+random.ints(minCard, maxCard).findFirst().getAsInt()+"-"+random.ints(minCard, maxCard).findFirst().getAsInt();
        }while (cardRepository.findByNumber(number) != null);
        int cvv = random.ints(min, max).findFirst().getAsInt(); //(int) ((Math.random() * (max - min)) + min);

        String cardHolder = current.getName()+" "+current.getLastName();
        LocalDate thruDate = LocalDate.now();
        LocalDate fromDate = LocalDate.now().plusYears(5);
        Card card = new Card(cardHolder,type,color,number,cvv,thruDate,fromDate,true,current);
        cardRepository.save(card);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PatchMapping("/clients/current/cards/{id}")
    public ResponseEntity<Object> deleteCards(Authentication authentication,@PathVariable long id){
        Card card = cardRepository.findById(id).orElse(null);
        Client current = clientRepository.findByEmail(authentication.getName());
        if(!current.getCards().contains(card)){
          return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        card.setExistCard(false);
        cardRepository.save(card);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
