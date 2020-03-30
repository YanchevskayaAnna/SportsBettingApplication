package sportsbetting.controller;

import sportsbetting.exceptions.SportEventIdMismatchException;
import sportsbetting.exceptions.SportEventNotFoundException;
import sportsbetting.model.sportevent.SportEvent;
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
@RequestMapping("player/events")
@Slf4j
public class SportEventController {

    private ISportEventService sportEventService;

    @Autowired
    public SportEventController(ISportEventService sportEventService) {
        this.sportEventService = sportEventService;
    }

    @RequestMapping(value = "/")
    public ModelAndView getAllSportEvents() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("events", sportEventService.findAll());
        modelAndView.setViewName("player/events");
        return modelAndView;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public SportEvent getSportEventById(@PathVariable long id) {
        log.info("getSportEventById request called with id: {}", id);
        return sportEventService.getSportEventById(id)
                .orElseThrow(SportEventNotFoundException::new);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SportEvent addNewSportEvent(@RequestBody SportEvent sportEvent) {
        log.info("addNewSportEvent request called with sportEvent: {}", sportEvent);
        return sportEventService.save(sportEvent);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SportEvent updateSportEvent(@PathVariable long id, @RequestBody SportEvent sportEvent) {
        if (sportEvent.getId() != id) {
            throw new SportEventIdMismatchException();
        }
        sportEventService.getSportEventById(id)
                .orElseThrow(SportEventNotFoundException::new);
        log.info("updateSportEvent request called with id: {} and sportEvent: {}", id, sportEvent);
        return sportEventService.updateSportEvent(id, sportEvent);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public boolean deleteSportEvent(@PathVariable long id) {
        log.info("deleteSportEvent request called with id: {}", id);
        sportEventService.getSportEventById(id)
                .orElseThrow(SportEventNotFoundException::new);
        return sportEventService.deleteSportEvent(id);
    }
}
