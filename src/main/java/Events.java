public class Events extends Task {
    private String dueAt;
    private static final String DUE_COMMAND = "/at";
    public Events(String input) {
        super(getTaskNameFromInput(input));
        dueAt = getDueDateFromInput(input);
    }

    private static String getTaskNameFromInput(String input) {
        if (input.contains(DUE_COMMAND)) {
            return input.split(DUE_COMMAND)[0];
        } else {
            return input;
        }
    }

    private static String getDueDateFromInput(String input) {
        if (input.contains(DUE_COMMAND)) {
            return removeStartingWhiteSpace(input.split(DUE_COMMAND)[1]);
        } else {
            return "not specified";
        }
    }

    private static String removeStartingWhiteSpace(String input) {
        if (input.substring(0, 1).equals(" ")) {
            return input.substring(1);
        }
        return input;
    }

    @Override
    public String setDone() {
        super.setTaskCompleted();
        return "Nice! I've marked this event as done:\n" +
                toString();
    }

    @Override
    public String toString() {
        String taskStringCheck = super.getIsTaskCompleted() ? "X" : " ";
        return "[E]" + "[" + taskStringCheck + "] " + super.getTaskName() +
                "(at: " + (dueAt.equals(null) ? "not specified" : dueAt) +  ")";
    }
}