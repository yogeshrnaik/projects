package tdd.examples.mathworks;

import java.util.ArrayList;
import java.util.List;

public class NumberFilter {

	public List<Integer> filter(List<Integer> numbers, FilterCondition... filterConditions) {
		List<Integer> filteredNumbers = new ArrayList<Integer>();
		for (Integer number : numbers) {
			if (allConditionsSatisfied(number, filterConditions)) {
				filteredNumbers.add(number);
			}
		}
		return filteredNumbers;
	}

	private boolean allConditionsSatisfied(Integer number, FilterCondition... filter) {
		for (FilterCondition condition : filter) {
			if (!condition.isConditionSatisfied(number)) {
				return false;
			}
		}
		return true;
	}

}
