package tdd.examples.mathworks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class MathWorksTest {

	@Test
	public void givenListOfNumbers_FindPrimeNumbers() {
		// given
		List<Integer> numbers = new ArrayList<Integer>(Arrays.asList(new Integer[]{1, 2, 3, 4, 5}));

		NumberFilter filter = new NumberFilter();
		List<Integer> primes = filter.filter(numbers, new PrimeNumberFilter());
		
		// then
		Assert.assertTrue(primes.contains(2));
		Assert.assertTrue(primes.contains(3));
		Assert.assertTrue(primes.contains(5));
		Assert.assertFalse(primes.contains(1));
		Assert.assertFalse(primes.contains(4));
	}
	
	
	@Test
	public void givenListOfNumbers_FindOddNumbers() {
		// given
		List<Integer> numbers = new ArrayList<Integer>(Arrays.asList(new Integer[]{1, 2, 3, 4, 5}));
		
		NumberFilter filter = new NumberFilter();
		List<Integer> odds = filter.filter(numbers, new OddNumberFilter());
		
		// then
		Assert.assertTrue(odds.contains(1));
		Assert.assertFalse(odds.contains(2));
		Assert.assertTrue(odds.contains(3));
		Assert.assertFalse(odds.contains(4));
		Assert.assertTrue(odds.contains(5));
	}
	

	
	
	@Test
	public void givenListOfNumbers_FindOddPrimes() {
		// given
		List<Integer> numbers = new ArrayList<Integer>(Arrays.asList(new Integer[]{1, 2, 3, 4, 5}));
		
		NumberFilter filter = new NumberFilter();
		List<Integer> oddPrimes = filter.filter(numbers, new OddNumberFilter(), new PrimeNumberFilter());
		
		// then
		Assert.assertFalse(oddPrimes.contains(1));
		Assert.assertFalse(oddPrimes.contains(2));
		Assert.assertTrue(oddPrimes.contains(3));
		Assert.assertFalse(oddPrimes.contains(4));
		Assert.assertTrue(oddPrimes.contains(5));
	}
}
