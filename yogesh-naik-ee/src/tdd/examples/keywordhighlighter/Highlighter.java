package tdd.examples.keywordhighlighter;

import java.util.List;
import java.util.Map;

public class Highlighter {

	private String input;
	private List<KeywordStyler> keywordStylers;

	public Highlighter(String input, List<KeywordStyler> keywords) {
		this.input = input;
		this.keywordStylers = keywords;
	}

	public String applyKeywordStyles() {
		return processInputWordByWord(input.split(" "));
	}

	private String processInputWordByWord(String[] inputWords) {
		String result = "";
		for (String word : inputWords) {
			result += getKeywordStyler(word).style(word) + " ";
		}
		return result.trim();
	}

	private KeywordStyler getKeywordStyler(String inputWord) {
		for (KeywordStyler keywordStyler : keywordStylers) {
			if (keywordStyler.getKeyword().equalsIgnoreCase(inputWord))
				return keywordStyler;
		}
		return KeywordStyler.NO_STYLER;
	}
}
