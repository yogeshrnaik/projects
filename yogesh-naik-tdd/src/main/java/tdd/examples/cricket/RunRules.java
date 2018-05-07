package tdd.examples.cricket;

import java.util.Arrays;
import java.util.List;

public class RunRules implements PointsRule {

    private final List<PointsBasedOnRunsRule> runRules;

    public RunRules(PointsBasedOnRunsRule... rules) {
        this.runRules = Arrays.asList(rules);
    }

    @Override
    public int calculate(int runs) {
        return runRules.stream().mapToInt(rule -> rule.calculate(runs)).sum();
    }
}
