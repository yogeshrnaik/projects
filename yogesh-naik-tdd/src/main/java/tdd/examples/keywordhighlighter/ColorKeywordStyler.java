package tdd.examples.keywordhighlighter;

public class ColorKeywordStyler implements KeywordStyler {

	private String color;
	private String keyword;

	public ColorKeywordStyler(String keyword, String color) {
		this.keyword = keyword;
		this.color = color;
	}

	@Override
	public String style(String word) {
		return "[" + color + "]" + word + "[" + color + "]";
	}

	@Override
	public String getKeyword() {
		return keyword;
	}

}
