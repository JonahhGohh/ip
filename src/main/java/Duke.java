import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        // Start up greeting message
        String greetingMessage = "Hello! I'm a Chat bot and my name " +
                "is Joe" + "\nHow may I help you?";
        System.out.println(formatMessage(greetingMessage));

        // Task list
        List<Task> taskList = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        boolean isChatBotOnline = true;
        while (isChatBotOnline) {
            // Listen to input
            String input = sc.nextLine();
            String command = formatCommand(input);
            String additionalText = formatCommandText(input);

            // Echoing the input
            if (command.equals("bye")) {
                String byeMessage = "Goodbye, hope you had a great time!";
                System.out.println(formatMessage(byeMessage));
                isChatBotOnline = false;
            } else if (command.equals("list")) {
                System.out.println(formatMessage(getTaskListString(taskList)));
            } else if (command.equals("done")) {
                try {
                    int taskNumber = Integer.parseInt(additionalText);
                    int arrayNumber = taskNumber - 1;
                    Task task = taskList.get(arrayNumber);
                    String doneMessage = task.setDone();
                    System.out.println(formatMessage(doneMessage));
                } catch (NumberFormatException e) {
                    System.out.println(formatMessage(e +
                            "\nError! Invalid task number." +
                            "\nPlease input a valid task number!"));
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(e +
                            "\nError! Task number does not exist." +
                            "\nPlease input a valid task number!");
                }
            } else if (command.equals("delete")) {
                try {
                    int taskNumber = Integer.parseInt(additionalText);
                    int arrayNumber = taskNumber - 1;
                    Task task = taskList.get(arrayNumber);
                    taskList.remove(arrayNumber);
                    System.out.println(formatMessage("The following task has been removed:\n" +
                            task + "\n" + Task.getNumOfTasksString()));
                } catch (NumberFormatException e) {
                    System.out.println(formatMessage(e +
                            "\nError! Invalid task number." +
                            "\nPlease input a valid task number!"));
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(e +
                            "\nError! Task number does not exist." +
                            "\nPlease input a valid task number!");
                }
            } else if (command.equals("todo")) {
                try {
                    Todos todos = new Todos(additionalText);
                    taskList.add(todos);
                    printTaskAddedMessage(todos);
                } catch (EmptyTaskDukeException e) {
                    System.out.println(formatMessage(e + ""));
                }
            } else if (command.equals("deadline")) {
                try {
                    Deadlines deadlines = new Deadlines(additionalText);
                    taskList.add(deadlines);
                    printTaskAddedMessage(deadlines);
                } catch (EmptyTaskDukeException e) {
                    System.out.println(formatMessage(e + ""));
                }
            } else if (command.equals("event")) {
                try {
                    Events events = new Events(additionalText);
                    taskList.add(events);
                    printTaskAddedMessage(events);
                } catch (EmptyTaskDukeException e) {
                    System.out.println(formatMessage(e + ""));
                }
            } else if (command.equals("help")) {
                String allCommands = "todo\n" +
                        "deadline *text* /by *date*\n" +
                        "event *text* /at *text*\n" +
                        "done *number*\n" +
                        "list\n" +
                        "delete *number*\n" +
                        "bye";
                System.out.println(formatMessage(allCommands));
            } else {
                // no valid command
                System.out.println(formatMessage("Please enter a valid command! \n" +
                        "Type help for a list of commands"));
            }
        }
        sc.close();
    }


    static String formatMessage(String str) {
        return "____________________________________________________________" +
                "\n" + str + "\n" +
                "____________________________________________________________\n";
    }

    // prints all of the tasks in the taskList
    static String getTaskListString(List<Task> taskList) {
        String taskListString = "Here are the tasks in your list:\n";
        for (int i = 0; i < taskList.size(); i++) {
            String taskString = (i + 1) + ". " + taskList.get(i);
            taskListString = taskListString + taskString +
                    (i == taskList.size() - 1 ? "" : "\n");
        }
        return taskListString;
    }

    // extracts the command from the input
    static String formatCommand(String input) {
        return input.split(" ")[0];
    }

    // extracts the additional text after the command from the input
    static String formatCommandText(String input) {
        if (input.split(" ").length == 1) {
            return "";
        }
        return input.substring(formatCommand(input).length() + 1);
    }

    static void printTaskAddedMessage(Task task) {
        System.out.println(formatMessage("Got it. I've added this task:\n"
                + task + "\n" + Task.getNumOfTasksString()));
    }
}
