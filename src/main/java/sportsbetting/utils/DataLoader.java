package sportsbetting.utils;

import sportsbetting.model.bet.Bet;
import sportsbetting.model.bet.BetType;
import sportsbetting.model.outcome.Outcome;
import sportsbetting.model.outcome.OutcomeOdd;
import sportsbetting.model.sportevent.FootballSportEvent;
import sportsbetting.model.sportevent.SportEvent;
import sportsbetting.model.sportevent.TennisSportEvent;
import sportsbetting.model.user.Admin;
import sportsbetting.model.user.Currency;
import sportsbetting.model.user.Player;
import sportsbetting.service.IBetService;
import sportsbetting.service.IOutcomeOddService;
import sportsbetting.service.IOutcomeService;
import sportsbetting.service.ISportEventService;
import sportsbetting.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

@Component
@Slf4j
public class DataLoader{

    private static ISportEventService sportEventService;
    private static IBetService betService;
    private static IOutcomeService outcomeService;
    private static IOutcomeOddService outcomeOddService;
    private static IUserService userService;

    @Autowired
    public DataLoader(ISportEventService sportEventService,
                      IBetService betService,
                      IOutcomeService outcomeService,
                      IOutcomeOddService outcomeOddService,
                      IUserService userService) {
        DataLoader.sportEventService = sportEventService;
        DataLoader.betService = betService;
        DataLoader.outcomeOddService = outcomeOddService;
        DataLoader.outcomeService = outcomeService;
        DataLoader.userService = userService;
    }

    public static void run(String... args) throws Exception {
        log.info("Data loading...");

        userService.saveUser(new Admin("admin", "hanna_yanchevska@epam.com", "Friends"));
        userService.saveUser(new Player(
                "player",
                "hanna_yanchevska@epam.com",
                "Friends",
                "123456789",
                100.00,
                Currency.EUR,
                LocalDate.ofYearDay(1983, 200)));

        SportEvent football = new FootballSportEvent("Southampton v Bournemoth");
        football.setStartDate(LocalDateTime.of(2020, Month.MAY, 20, 19, 00));
        football.setEndDate(LocalDateTime.of(2020, Month.MAY, 20, 21, 00));
        football = sportEventService.save(football);

        createFootballBet1(football);
        createFootballBet2(football);
        createFootballBet3(football);

        SportEvent tennis = new TennisSportEvent("Rafael Nadal vs. Alexander Zverev, Indian Wells 4th Round");
        tennis.setStartDate(LocalDateTime.of(2020, Month.MAY, 21, 19, 00));
        tennis.setEndDate(LocalDateTime.of(2020, Month.MAY, 21, 21, 00));
        tennis = sportEventService.save(tennis);

        createTennisBet1(tennis);

        log.info("events downloaded: {}", sportEventService.findAll());
        log.info("bets downloaded: {}", betService.findAll());
        log.info("outcomes downloaded: {}", outcomeService.findAll());
        log.info("outcomes odds downloaded: {}", outcomeOddService.findAll());
    }

    private static void createFootballBet1(SportEvent football) {
        Bet footballBet1 = new Bet();
        footballBet1.setBetType(BetType.WINNER_BET);
        footballBet1.setDescription("winner bet");
        footballBet1.setSportEvent(football);
        footballBet1 = betService.save(footballBet1);

        Outcome footballOutcome1 = new Outcome("Southampton", footballBet1);
        footballOutcome1 = outcomeService.save(footballOutcome1);

        OutcomeOdd footballOutcomeOdd11 = new OutcomeOdd(footballOutcome1);
        footballOutcomeOdd11.setOddValue(4);
        footballOutcomeOdd11.setValidFrom(LocalDateTime.of(2020, Month.FEBRUARY, 1, 19, 00));
        footballOutcomeOdd11.setValidTo(LocalDateTime.of(2020, Month.FEBRUARY, 10, 18, 59));
        outcomeOddService.save(footballOutcomeOdd11);

        OutcomeOdd footballOutcomeOdd12 = new OutcomeOdd(footballOutcome1);
        footballOutcomeOdd12.setOddValue(5);
        footballOutcomeOdd12.setValidFrom(LocalDateTime.of(2020, Month.FEBRUARY, 10, 19, 00));
        footballOutcomeOdd12.setValidTo(LocalDateTime.of(2020, Month.MAY, 20, 18, 59));
        outcomeOddService.save(footballOutcomeOdd12);

        Outcome footballOutcome2 = new Outcome("Bournemoth", footballBet1);
        footballOutcome2 = outcomeService.save(footballOutcome2);

        OutcomeOdd footballOutcomeOdd21 = new OutcomeOdd(footballOutcome2);
        footballOutcomeOdd21.setOddValue(1.7);
        footballOutcomeOdd21.setValidFrom(LocalDateTime.of(2020, Month.FEBRUARY, 1, 19, 00));
        footballOutcomeOdd21.setValidTo(LocalDateTime.of(2020, Month.FEBRUARY, 10, 18, 59));
        outcomeOddService.save(footballOutcomeOdd21);

        OutcomeOdd footballOutcomeOdd22 = new OutcomeOdd(footballOutcome2);
        footballOutcomeOdd22.setOddValue(1.5);
        footballOutcomeOdd22.setValidFrom(LocalDateTime.of(2020, Month.FEBRUARY, 10, 19, 00));
        footballOutcomeOdd22.setValidTo(LocalDateTime.of(2020, Month.MAY, 20, 18, 59));
        outcomeOddService.save(footballOutcomeOdd22);

        Outcome footballOutcome3 = new Outcome("Draw", footballBet1);
        footballOutcome3 = outcomeService.save(footballOutcome3);

        OutcomeOdd footballOutcomeOdd31 = new OutcomeOdd(footballOutcome3);
        footballOutcomeOdd31.setOddValue(1.7);
        footballOutcomeOdd31.setValidFrom(LocalDateTime.of(2020, Month.FEBRUARY, 1, 19, 00));
        footballOutcomeOdd31.setValidTo(LocalDateTime.of(2020, Month.FEBRUARY, 10, 18, 59));
        outcomeOddService.save(footballOutcomeOdd21);

        OutcomeOdd footballOutcomeOdd32 = new OutcomeOdd(footballOutcome3);
        footballOutcomeOdd32.setOddValue(1.5);
        footballOutcomeOdd32.setValidFrom(LocalDateTime.of(2020, Month.FEBRUARY, 10, 19, 00));
        footballOutcomeOdd32.setValidTo(LocalDateTime.of(2020, Month.MAY, 20, 18, 59));
        outcomeOddService.save(footballOutcomeOdd32);
    }

    private static void createFootballBet2(SportEvent football) {
        Bet footballBet2 = new Bet();
        footballBet2.setBetType(BetType.GOALS_BET);
        footballBet2.setDescription("goals bet");
        footballBet2.setSportEvent(football);
        footballBet2 = betService.save(footballBet2);

        Outcome footballOutcome1 = new Outcome("0", footballBet2);
        footballOutcome1 = outcomeService.save(footballOutcome1);

        OutcomeOdd footballOutcomeOdd11 = new OutcomeOdd(footballOutcome1);
        footballOutcomeOdd11.setOddValue(1.2);
        footballOutcomeOdd11.setValidFrom(LocalDateTime.of(2020, Month.FEBRUARY, 1, 19, 00));
        footballOutcomeOdd11.setValidTo(LocalDateTime.of(2020, Month.MAY, 20, 19, 00));
        outcomeOddService.save(footballOutcomeOdd11);

        Outcome footballOutcome2 = new Outcome("1", footballBet2);
        OutcomeOdd footballOutcomeOdd21 = new OutcomeOdd(footballOutcome1);
        footballOutcomeOdd21.setOddValue(1.3);
        footballOutcomeOdd21.setValidFrom(LocalDateTime.of(2020, Month.FEBRUARY, 1, 19, 00));
        footballOutcomeOdd21.setValidTo(LocalDateTime.of(2020, Month.MAY, 20, 19, 00));
        outcomeOddService.save(footballOutcomeOdd21);

        Outcome footballOutcome3 = new Outcome(">=2", footballBet2);
        OutcomeOdd footballOutcomeOdd32 = new OutcomeOdd(footballOutcome1);
        footballOutcomeOdd32.setOddValue(1.5);
        footballOutcomeOdd32.setValidFrom(LocalDateTime.of(2020, Month.FEBRUARY, 10, 19, 00));
        footballOutcomeOdd32.setValidTo(LocalDateTime.of(2020, Month.MAY, 20, 19, 00));
        outcomeOddService.save(footballOutcomeOdd32);
    }

    private static void createFootballBet3(SportEvent football) {
        Bet footballBet3 = new Bet();
        footballBet3.setBetType(BetType.PLAYER_SCORE_BET);
        footballBet3.setDescription("scores of Victor Wanyama");
        footballBet3.setSportEvent(football);
        footballBet3 = betService.save(footballBet3);

        Outcome footballOutcome1 = new Outcome("0", footballBet3);
        footballOutcome1 = outcomeService.save(footballOutcome1);

        OutcomeOdd footballOutcomeOdd11 = new OutcomeOdd(footballOutcome1);
        footballOutcomeOdd11.setOddValue(0.5);
        footballOutcomeOdd11.setValidFrom(LocalDateTime.of(2020, Month.FEBRUARY, 1, 19, 00));
        footballOutcomeOdd11.setValidTo(LocalDateTime.of(2020, Month.MAY, 20, 19, 00));
        outcomeOddService.save(footballOutcomeOdd11);

        Outcome footballOutcome2 = new Outcome("1", footballBet3);
        footballOutcome2 = outcomeService.save(footballOutcome2);

        OutcomeOdd footballOutcomeOdd21 = new OutcomeOdd(footballOutcome2);
        footballOutcomeOdd21.setOddValue(1.4);
        footballOutcomeOdd21.setValidFrom(LocalDateTime.of(2020, Month.FEBRUARY, 1, 19, 00));
        footballOutcomeOdd21.setValidTo(LocalDateTime.of(2020, Month.MAY, 20, 19, 00));
        outcomeOddService.save(footballOutcomeOdd21);

        Outcome footballOutcome3 = new Outcome(">=2", footballBet3);
        footballOutcome3 = outcomeService.save(footballOutcome3);

        OutcomeOdd footballOutcomeOdd32 = new OutcomeOdd(footballOutcome3);
        footballOutcomeOdd32.setOddValue(1.8);
        footballOutcomeOdd32.setValidFrom(LocalDateTime.of(2020, Month.FEBRUARY, 10, 19, 00));
        footballOutcomeOdd32.setValidTo(LocalDateTime.of(2020, Month.MAY, 20, 19, 00));
        outcomeOddService.save(footballOutcomeOdd32);
    }

    private static void createTennisBet1(SportEvent tennis) {
        Bet tennisBet = new Bet();
        tennisBet.setBetType(BetType.WINNER_BET);
        tennisBet.setDescription("Rafael Nadal  vs Alexander Zverev");
        tennisBet.setSportEvent(tennis);
        tennisBet = betService.save(tennisBet);

        Outcome tennisOutcome1 = new Outcome("Rafael Nadal", tennisBet);
        tennisOutcome1 = outcomeService.save(tennisOutcome1);

        OutcomeOdd tennisOutcomeOdd11 = new OutcomeOdd(tennisOutcome1);
        tennisOutcomeOdd11.setOddValue(0.5);
        tennisOutcomeOdd11.setValidFrom(LocalDateTime.of(2020, Month.FEBRUARY, 1, 19, 00));
        tennisOutcomeOdd11.setValidTo(LocalDateTime.of(2020, Month.MAY, 21, 19, 00));
        outcomeOddService.save(tennisOutcomeOdd11);

        Outcome tennisOutcome2 = new Outcome("Alexander Zverev", tennisBet);
        tennisOutcome2 = outcomeService.save(tennisOutcome2);

        OutcomeOdd tennisOutcomeOdd21 = new OutcomeOdd(tennisOutcome2);
        tennisOutcomeOdd21.setOddValue(1.4);
        tennisOutcomeOdd21.setValidFrom(LocalDateTime.of(2020, Month.FEBRUARY, 1, 19, 00));
        tennisOutcomeOdd21.setValidTo(LocalDateTime.of(2020, Month.MAY, 21, 19, 00));
        outcomeOddService.save(tennisOutcomeOdd21);
    }
}
