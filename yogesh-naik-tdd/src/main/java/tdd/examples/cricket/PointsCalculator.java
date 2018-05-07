package tdd.examples.cricket;

import java.util.Optional;

public class PointsCalculator {

    private final Optional<PointsRule> runRules;
    private final Optional<PointsRule> wicketRules;

    public PointsCalculator(RunRules runRules) {
        this(runRules, null);
    }

    public PointsCalculator(WicketRules wicketRules) {
        this(null, wicketRules);
    }

    public PointsCalculator(RunRules runRules, WicketRules wicketRules) {
        this.runRules = Optional.ofNullable(runRules);
        this.wicketRules = Optional.ofNullable(wicketRules);
    }

    public int calculate(Player player) {
        return calculate(runRules, player.getRuns()) +
            calculate(wicketRules, player.getWickets());
    }

    private int calculate(Optional<PointsRule> rule, int runsOrWicketsOrCatches) {
        if (rule.isPresent()) {
            return rule.get().calculate(runsOrWicketsOrCatches);
        }
        return 0;
    }
}
