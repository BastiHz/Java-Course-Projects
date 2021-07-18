package engine;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Response {
    // https://www.baeldung.com/jackson-serialize-enums

    CORRECT_ANSWER(true, "Congratulations, you're right!"),
    WRONG_ANSWER(false, "Wrong answer! Please, try again.");

    private final boolean success;
    private final String feedback;

    Response(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getFeedback() {
        return feedback;
    }
}
