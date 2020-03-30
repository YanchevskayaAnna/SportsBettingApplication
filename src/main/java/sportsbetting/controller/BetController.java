package sportsbetting.controller;

import sportsbetting.exceptions.BetIdMismatchException;
import sportsbetting.exceptions.BetNotFoundException;
import sportsbetting.model.bet.Bet;
import sportsbetting.model.sportevent.SportEvent;
import sportsbetting.service.IBetService;
import sportsbetting.service.ISportEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("player/bets")
@Slf4j
public class BetController {

    private IBetService betService;
    private ISportEventService sportEventService;

    @Autowired
    public BetController(IBetService betService, ISportEventService sportEventService) {
        this.betService = betService;
        this.sportEventService = sportEventService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getAllBets() {
        log.info("getAllBets request called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("bets", betService.findAll());
        modelAndView.setViewName("player/bets");
        return modelAndView;
    }

    @RequestMapping(value = "/event/{id}", method = RequestMethod.GET)
    public ModelAndView getAllBetsBySportEventID(@PathVariable long id) {
        log.info("getAllBets request called");
        ModelAndView modelAndView = new ModelAndView();
        SportEvent sportEvent = sportEventService.getSportEventById(id).get();
        modelAndView.addObject("sportevent", sportEvent.getTitle());
        modelAndView.addObject("sporteventtype", sportEvent.getType());
        modelAndView.addObject("sporteventstart", sportEvent.getStartDate());
        modelAndView.addObject("sporteventend", sportEvent.getEndDate());
        modelAndView.addObject("bets", betService.findBySportEvent(sportEvent));
        modelAndView.setViewName("/player/bets");
        return modelAndView;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Bet getBetById(@PathVariable long id) {
        log.info("getBetById request called with id: {}", id);
        return betService.getBetById(id)
                .orElseThrow(BetNotFoundException::new);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Bet addNewBet(@RequestBody Bet bet) {
        log.info("addNewBet request called with bet: {}", bet);
        return betService.save(bet);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Bet updateBet(@PathVariable long id, @RequestBody Bet bet) {
        if (bet.getId() != id) {
            throw new BetIdMismatchException();
        }
        betService.getBetById(id)
                .orElseThrow(BetNotFoundException::new);
        log.info("updateBet request called with id: {} and bet: {}", id, bet);
        return betService.updateBet(id, bet);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public boolean deleteBet(@PathVariable long id) {
        log.info("deleteBet request called with id: {}", id);
        betService.getBetById(id)
                .orElseThrow(BetNotFoundException::new);
        return betService.deleteBet(id);
    }
}
