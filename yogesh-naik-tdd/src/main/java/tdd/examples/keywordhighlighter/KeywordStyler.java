package tdd.examples.keywordhighlighter;

public interface KeywordStyler {

	public String getKeyword();
	public String style(String word);
	
	
	public static final KeywordStyler NO_STYLER = new KeywordStyler() {

		@Override
		public String style(String word) {
			return word;
		}

		@Override
		public String getKeyword() {
			return "";
		}
	};
}
