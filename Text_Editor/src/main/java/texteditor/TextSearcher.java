package texteditor;

import javax.swing.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class TextSearcher extends SwingWorker<List<SearchResult>, SearchResult> {
    private final JTextArea textArea;
    private final String searchTerm;
    private final List<SearchResult> searchResults;
    private final boolean useRegex;
    private final Consumer<SearchResult> searchResultMarker;

    TextSearcher (
        final JTextArea textarea,
        final String searchTerm,
        final boolean useRegex,
        final List<SearchResult> searchResults,
        final Consumer<SearchResult> searchResultMarker) {
        this.textArea = textarea;
        this.searchTerm = useRegex ? searchTerm : searchTerm.toLowerCase();
        this.useRegex = useRegex;
        this.searchResults = searchResults;
        this.searchResultMarker = searchResultMarker;
    }

    @Override
    protected List<SearchResult> doInBackground() {
        searchResults.clear();
        return useRegex ? searchWithRegex() : searchWithString();
    }

    protected List<SearchResult> searchWithString() {
        final String text = textArea.getText().toLowerCase();
        int index = 0;
        while (true) {
            index = text.indexOf(searchTerm, index);
            if (index != -1) {
                publish(new SearchResult(index, searchTerm));
                index++;
            } else {
                break;
            }
        }
        return searchResults;
    }

    protected List<SearchResult> searchWithRegex() {
        final Pattern pattern = Pattern.compile(searchTerm);
        final Matcher matcher = pattern.matcher(textArea.getText());
        while (matcher.find()) {
            publish(new SearchResult(matcher.start(), matcher.group()));
        }
        return searchResults;
    }

    @Override
    protected void process(final List<SearchResult> chunks) {
        for (final SearchResult result : chunks) {
            if (searchResults.size() == 0) {
                // Mark the first result.
                searchResultMarker.accept(result);
            }
            searchResults.add(result);
        }
    }
}
