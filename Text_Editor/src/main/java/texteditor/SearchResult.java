package texteditor;

class SearchResult {
    int startIndex;
    int endIndex;

    SearchResult(final int index, final String foundText) {
        this.startIndex = index;
        this.endIndex = index + foundText.length();
    }
}
