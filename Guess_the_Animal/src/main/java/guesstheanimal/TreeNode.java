package guesstheanimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class TreeNode {

    // No parent Node to avoid circular references.
    public String data;  // Can be either the full animal name or the fact statement.
    public TreeNode yes;
    public TreeNode no;

    // The fields below will not be serialized.
    static final Pattern dotBangEnd = Pattern.compile("[.!]$");
    static final Pattern factSplit = Pattern.compile(
        TextHelper.getString("pattern.splitFact"),
        Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS
    );
    @SuppressWarnings("unchecked")
    static final UnaryOperator<String> getArticle =
        (UnaryOperator<String>) TextHelper.getObject("fun.getArticle");
    @SuppressWarnings("unchecked")
    static final Function<String, String[]> splitName =
        (Function<String, String[]>) TextHelper.getObject("fun.splitName");
    @SuppressWarnings("unchecked")
    static final Function<String, Boolean> nameHasArticle =
        (Function<String, Boolean>) TextHelper.getObject("fun.nameHasArticle");
    String name;
    String article;
    String question;
    String negatedStatement;

    boolean isAnimal() {
        return yes == null && no == null;
    }

    static TreeNode newAnimal() {
        final TreeNode animal = new TreeNode();
        final String input = TextHelper.nextLine();
        if (nameHasArticle.apply(input)) {
            animal.data = input;
            animal.initOtherAnimalFields();
        } else {
            animal.article = getArticle.apply(input);
            animal.name = input;
            if (animal.article.isBlank()) {
                animal.data = animal.name;
            } else {
                animal.data = animal.article + " " + animal.name;
            }
        }
        return animal;
    }

    static TreeNode newFact(String statement) {
        final TreeNode fact = new TreeNode();
        statement = dotBangEnd.matcher(statement).replaceAll("");
        statement = TextHelper.capitalizeFirst(statement);
        fact.data = statement + ".";
        fact.initOtherFactFields();
        return fact;
    }

    void initOtherFields() {
        if (isAnimal()) {
            initOtherAnimalFields();
        } else {
            initOtherFactFields();
            yes.initOtherFields();
            no.initOtherFields();
        }
    }

    private void initOtherAnimalFields() {
        final String[] parts = splitName.apply(data);
        this.article = parts[0];
        this.name = parts[1];
    }

    private void initOtherFactFields() {
        final String[] parts = factSplit.split(data);
        question = String.format(
            "%s %s?",
            TextHelper.FACT_QUESTION.get(parts[0]),
            parts[1].substring(0, parts[1].length() - 1)
        );
        negatedStatement = String.format(
            "%s %s %s",
            TextHelper.getString("statement.it"),
            TextHelper.FACT_NEGATION.get(parts[0]),
            parts[1]
        );
    }
}
