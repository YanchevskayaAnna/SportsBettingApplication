package sportsbetting.controller;

import sportsbetting.exceptions.WagerIdMismatchException;
import sportsbetting.exceptions.WagerNotFoundException;
import sportsbetting.model.bet.Bet;
import sportsbetting.model.sportevent.SportEvent;
import sportsbetting.model.user.Player;
import sportsbetting.model.wager.Wager;
import sportsbetting.service.IBetService;
import sportsbetting.service.IOutcomeService;
import sportsbetting.service.IUserService;
import sportsbetting.service.IWagerService;
import sportsbetting.utils.NumberFormatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("player/wager")
@Slf4j
public class WagerController {

    private IWagerService wagerService;
    private IBetService betService;
    private IOutcomeService outcomeService;
    private IUserService userService;

    @Autowired
    public WagerController(IBetService betService, IWagerService wagerService, IOutcomeService outcomeService, IUserService userService) {
        this.betService = betService;
        this.wagerService = wagerService;
        this.outcomeService = outcomeService;
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        NumberFormatUtil.registerDoubleFormat(binder);
    }

    @RequestMapping(value = "/bet/{id}", method = RequestMethod.GET)
    public ModelAndView createWager(@PathVariable long id, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        Player player = (Player) userService.findUserByUserName(principal.getName());
        ModelAndView modelAndView = fillWagerFields(id);
        Wager wager = new Wager();
        wager.setPlayer(player);
        wager.setCurrency(player.getCurrency());
        modelAndView.addObject("wager", wager);
        modelAndView.setViewName("player/wager");
        return modelAndView;
    }

    @RequestMapping(value = "/bet/{id}", method = RequestMethod.POST)
    public ModelAndView addNewWager(@PathVariable long id, @Valid Wager wager, BindingResult bindingResult, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        Player player = (Player) userService.findUserByUserName(principal.getName());
        ModelAndView modelAndView = fillWagerFields(id);
        modelAndView.addObject("wager", wager);
        modelAndView.setViewName("player/wager");
        if (wager.getAmount() <= 0) {
            bindingResult.rejectValue("amount", "amount", "*Please provide an amount");
        }
        if (bindingResult.hasErrors()) {
            return modelAndView;
        } else {
            wager.setPlayer(player);
            wagerService.save(wager);
            modelAndView.addObject("successMessage", "Wager has been created successfully");
        }
        return modelAndView;
    }

    private ModelAndView fillWagerFields(Long betId) {
        ModelAndView modelAndView = new ModelAndView();
        Bet bet = betService.getBetById(betId).get();
        SportEvent sportEvent = bet.getSportEvent();
        modelAndView.addObject("sportevent", sportEvent.getTitle());
        modelAndView.addObject("sporteventtype", sportEvent.getType());
        modelAndView.addObject("sporteventstart", sportEvent.getStartDate());
        modelAndView.addObject("sporteventend", sportEvent.getEndDate());
        modelAndView.addObject("bettype", bet.getBetType());
        modelAndView.addObject("betdescription", bet.getDescription());
        modelAndView.addObject("betId", betId);
        modelAndView.addObject("allOutcomes", outcomeService.getCurrentOutcomeOdd(bet.getOutcomes(), LocalDateTime.now()));
        return modelAndView;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Wager> getAllWagers() {
        log.info("getAllWagers request called");
        return wagerService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Wager getWagerById(@PathVariable long id) {
        log.info("getWagerById request called with id: {}", id);
        return wagerService.getWagerById(id)
                .orElseThrow(WagerNotFoundException::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Wager updateWager(@PathVariable long id, @RequestBody Wager wager) {
        if (wager.getId() != id) {
            throw new WagerIdMismatchException();
        }
        wagerService.getWagerById(id)
                .orElseThrow(WagerNotFoundException::new);
        log.info("updateWager request called with id: {} and wager: {}", id, wager);
        return wagerService.updateWager(id, wager);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public boolean deleteWager(@PathVariable long id) {
        log.info("deleteWager request called with id: {}", id);
        wagerService.getWagerById(id)
                .orElseThrow(WagerNotFoundException::new);
        return wagerService.deleteWager(id);
    }
}
