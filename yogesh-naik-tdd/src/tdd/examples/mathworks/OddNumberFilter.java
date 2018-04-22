package tdd.examples.mathworks;

public class OddNumberFilter implements FilterCondition {

	@Override
	public boolean isConditionSatisfied(Integer number) {
		return number % 2 != 0;
	}

}
