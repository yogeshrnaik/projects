package tdd.examples.keywordhighlighter;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import tdd.examples.keywordhighlighter.ColorKeywordStyler;
import tdd.examples.keywordhighlighter.Highlighter;
import tdd.examples.keywordhighlighter.KeywordStyler;

public class HighlighterTest {
	@Test
	public void highlightKeywordsInInputWithBlue() {
		// given
		String input = "If we write a program and compile it, then we can run the program to get output";
		List<KeywordStyler> keywords = keywordsToBeMarkedBlue();
		Highlighter highlighter = new Highlighter(input, keywords);

		// when
		String result = highlighter.applyKeywordStyles();

		// then
		String expectedResult = "[blue]If[blue] we write a program [blue]and[blue] compile it, [blue]then[blue] we can run the program to get output";
		assertEquals(expectedResult, result);
	}

//	@Test
//	public void highlightKeywordsInInputWithRespectiveColor() {
//		// given
//		String input = "If we write a program and compile it, then as we run the program, we will get output";
//		Map<String, String> keywords = keywordsWithRespectiveColors();
//		Highlighter highlighter = new Highlighter(input, keywords);
//
//		// when
//		String result = highlighter.highlight();
//
//		// then
//		String expectedResult = "[red]If[red] we write a program [red]and[red] compile it, [green]then[green] [blue]as[blue] we run the program, we will get output";
//		assertEquals(expectedResult, result);
//	}

	private List<KeywordStyler> keywordsToBeMarkedBlue() {
		List<KeywordStyler> keywordStylers = new ArrayList<KeywordStyler>();
		keywordStylers.add(new ColorKeywordStyler("as", "blue"));
		keywordStylers.add(new ColorKeywordStyler("if", "blue"));
		keywordStylers.add(new ColorKeywordStyler("and", "blue"));
		keywordStylers.add(new ColorKeywordStyler("then", "blue"));
		keywordStylers.add(new ColorKeywordStyler("when", "blue"));
		return keywordStylers;
	}

	private Map<String, String> keywordsWithRespectiveColors() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("as", "blue");
		map.put("if", "red");
		map.put("and", "red");
		map.put("then", "green");
		map.put("when", "blue");
		return map;
	}
}
