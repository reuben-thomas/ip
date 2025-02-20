# KIPP User Guide

![KIPP Detonates](https://github.com/reuben-thomas/ip/blob/master/docs/images/Ui.png)

> The star background from the James Webb Space Telescope was taken from their [Flickr page.](https://www.flickrcom/photos/nasawebbtelescope/52404135772/in/album-72177720301006030/), and is permitted for non-commercial use as per the guidelines [here](https://www.nasa.gov/nasa-brand-center/images-and-media/)
> References to KIPP and image icons were taken from the comic [Absolute Zero](https://thefilmstage.com/read-christopher-nolan-penned-interstellar-prequel-comic-telling-dr-manns-story/)


KIPP is a task manager assistant, accessible through a CLI or a GUI. KIPP is named after one of the robots from
interstellar, set to detonate by Dr Mann in order to sabotage the Endurance mission. This assistant on the other
hand, won't detonate anytime soon, trust me.

In fact, rather than sabotaging your tasks, KIPP is here to help you manage them. If you're a competent typist,
you'll be able to take the greatest advantage of KIPP's features over a conventional GUI based task manager.

## Quick Start

1. Ensure you have Java `17` installed on your Computer.
   > **Mac users**: Ensure you have a specific verion of JDK as
   described [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).
2. Download the latest `kipp.jar` from [here](https://github.com/reuben-thomas/ip/releases)
3. Copy the file into the directory where you'd like these tasks to be stored.
4. Open a terminal and navigate into the directory where `kipp.jar` is located. Use the following command to run KIPP:
    ```shell
    java -jar kippChat.jar
    ```
5. You should see KIPP say hello. If you're unfamiliar with any of the commands, type `help` to see all available
   commandds and what they can do. Some example commands you can try include:
    - `hello` - Get KIPP to say hello right back.
    - `todo <task description>` - Add a to-do task to your list.
    - `bye` - Exit KIPP.

## Command Summary

| Command    | Description                                             | Example                                                        |
|------------|---------------------------------------------------------|----------------------------------------------------------------|
| `help`     | Displays the available commands and their descriptions. | `help`                                                         |
| `hello`    | Greeting from KIPP.                                     | `hello`                                                        |
| `bye`      | Exit                                                    | `bye`                                                          |
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

## FAQ

**Q**: How do I transfer my tasks to another Computer with KIPP installed?

**A**: From the KIPP interface, the `saveto` command to save your tasks to a text file. Copy this file to the other 
Computer. Then, from the KIPP interface on the other Computer, use the `loadfrom` command to load the tasks from the 
text file.

## Citations & References

- The star background from the James Webb Space Telescope was taken from their [Flickr page.](https://www.flickr.com/photos/nasawebbtelescope/52404135772/in/album-72177720301006030/), and is permitted for non-commercial use as per the guidelines [here](https://www.nasa.gov/nasa-brand-center/images-and-media/)
- References to KIPP and image icons were taken from the comic [Absolute Zero](https://thefilmstage.com/read-christopher-nolan-penned-interstellar-prequel-comic-telling-dr-manns-story/)
- GitHub Copilot was used as an auto-complete tool throughout this project.