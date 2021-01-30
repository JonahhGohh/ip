package duke;

import duke.command.Command;
import duke.exceptions.EmptyTaskDukeException;
import duke.parser.Parser;
import duke.storage.Storage;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Handles the core logic of the Duke application
 */
public class Duke {
    private Storage storage;
    private TaskList taskList;
    private Ui ui;

    public Duke(String filePath) {
        this.storage = new Storage(filePath);
        this.ui = new Ui(new Scanner(System.in));
        // loads the internal storage tasks into task list of program
        this.taskList = storage.load();
    }

    /**
     * Runs the entire logic for Duke
     */
    public void run() {
        // show welcome message
        ui.printWelcomeMessage();

        boolean isDukeOnline = true;
        while (isDukeOnline) {
            // do something
            try {
                String fullInput = ui.readCommand();
                Command c = Parser.parseCommand(fullInput);
                Task task = Parser.parseTask(fullInput, taskList);
                c.execute(task, taskList, storage);
                // check online condition
                isDukeOnline = c.isDukeOnline();
            } catch (EmptyTaskDukeException e) {
                Ui.printError(e.getMessage());
            } catch (NumberFormatException e) {
                Ui.printError(e.getMessage()
                        + "\nPlease enter a valid task number.");
            } catch (IndexOutOfBoundsException e) {
                Ui.printError(e.getMessage()
                        + "\nPlease enter a valid task number.");
            } catch (DateTimeParseException e) {
                Ui.printError(e.getMessage()
                        + "\nPlease enter the date in the correct format.");
            }

        }
        ui.close();
    }
}