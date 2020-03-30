package sportsbetting.controller;

import sportsbetting.exceptions.OutcomeIdMismatchException;
import sportsbetting.exceptions.OutcomeNotFoundException;
import sportsbetting.model.outcome.Outcome;
import sportsbetting.service.IOutcomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/outcome")
@Slf4j
public class OutcomeController {

    private IOutcomeService outcomeService;

    @Autowired
    public OutcomeController(IOutcomeService outcomeService) {
        this.outcomeService = outcomeService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Outcome> getAllOutcomes() {
        log.info("getAllOutcomes request called");
        return outcomeService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Outcome getOutcomeById(@PathVariable long id) {
        log.info("getOutcomeById request called with id: {}", id);
        return outcomeService.getOutcomeById(id)
                .orElseThrow(OutcomeNotFoundException::new);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Outcome addNewOutcome(@RequestBody Outcome outcome) {
        log.info("addNewOutcome request called with outcome: {}", outcome);
        return outcomeService.save(outcome);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Outcome updateOutcome(@PathVariable long id, @RequestBody Outcome outcome) {
        if (outcome.getId() != id) {
            throw new OutcomeIdMismatchException();
        }
        outcomeService.getOutcomeById(id)
                .orElseThrow(OutcomeNotFoundException::new);
        log.info("updateOutcome request called with id: {} and outcome: {}", id, outcome);
        return outcomeService.updateOutcome(id, outcome);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public boolean deleteOutcome(@PathVariable long id) {
        log.info("deleteOutcome request called with id: {}", id);
        outcomeService.getOutcomeById(id)
                .orElseThrow(OutcomeNotFoundException::new);
        return outcomeService.deleteOutcome(id);
    }
}
