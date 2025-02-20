# KIPP User Guide

![KIPP Detonates](https://github.com/reuben-thomas/ip/blob/master/docs/images/Ui.png)

KIPP is an AI Assistant, named after one of the robots from the movie Interstellar. In the movie, KIPP detonates itself when interacted with.

## Features

| Command    | Description                                             | Example                                                        |
| ---------- | ------------------------------------------------------- | -------------------------------------------------------------- |
| `help`     | Displays the available commands and their descriptions. | `help`                                                         |
| `hello`    | Greeting from KIPP.                                     | `hello`                                                        |
| `bye`      | Save task list and exit.                                | `bye`                                                          |
| `list`     | List all tasks on your list.                            | `list`                                                         |
| `mark`     | Set task as completed.                                  | `mark <task number>`                                           |
| `unmark`   | Set task as incomplete.                                 | `unmark <task number>`                                         |
| `todo`     | Add a to-do task to your list.                          | `todo <task description>`                                      |
| `deadline` | Add a task with a deadline to your list.                | `deadline <task description> /by <yyyy-mm-dd>`                 |
| `event`    | Add a task with a start and end date.                   | `event <task description> /from <yyyy-mm-dd> /to <yyyy-mm-dd>` |
| `delete`   | Delete a task by task number.                           | `delete <task number>`                                         |
| `saveto`   | Save the current task list to disk as a text file.      | `saveto <relative_file_path.txt>`                              |
| `loadfrom` | Load a previously saved task list from a text file.     | `loadfrom <relative_file_path.txt>`                            |
| `find`     | Find tasks containing a keyword.                        | `find <keyword>`                                               |

