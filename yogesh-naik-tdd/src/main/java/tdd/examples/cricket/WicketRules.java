package tdd.examples.cricket;

import java.util.Arrays;
import java.util.List;

public class WicketRules implements PointsRule {

    private final List<PointsBasedOnWicketsRule> wicketRules;

    public WicketRules(PointsBasedOnWicketsRule... rules) {
        this.wicketRules = Arrays.asList(rules);
    }

    @Override
    public int calculate(int wickets) {
        return wicketRules.stream().mapToInt(rule -> rule.calculate(wickets)).sum();
    }
}
