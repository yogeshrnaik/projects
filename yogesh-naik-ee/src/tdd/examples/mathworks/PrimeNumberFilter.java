package tdd.examples.mathworks;

public class PrimeNumberFilter implements FilterCondition {

	@Override
	public boolean isConditionSatisfied(Integer number) {
		if (number == 1)
			return false;

		for (int divisor = 2; divisor < ((int)Math.sqrt(number) + 1); divisor++) {
			if (number % divisor == 0)
				return false;
		}
		return true;
	}
}
