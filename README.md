# KIPP

> "Do not go gentle into that good night. Old age should burn and rave at close of ay. Rage, rage against the dying of
> the light." - Dylan Thomas

![KIPP Detonates](https://github.com/reuben-thomas/ip/blob/master/docs/images/kipp-detonates.png)

KIPP is an AI Assistant, named after one of the robots from the movie Interstellar. In the movie, KIPP detonates itself
when interacted with. This repository is based on the provided project template for a greenfield Java project. Given
below are instructions on how to use it.

## Setting up in Intellij

Prerequisites: JDK 17, update Intellij to the most recent version.

1. Open Intellij (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project
   first)
1. Open the project into Intellij as follows:
    1. Click `Open`.
    1. Select the project directory, and click `OK`.
    1. If there are any further prompts, accept the defaults.
1. Configure the project to use **JDK 17** (not other versions) as explained
   in [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk).<br>
   In the same dialog, set the **Project language level** field to the `SDK default` option.
1. After that, locate the `src/main/java/kipp.java` file, right-click it, and choose `Run kipp.main()` (if the code
   editor is showing compile errors, try restarting the IDE). If the setup is correct, you should see something like the
   below as the output:
   ```
   ██   ██ ██ ██████  ██████  
   ██  ██  ██ ██   ██ ██   ██ 
   █████   ██ ██████  ██████  
   ██  ██  ██ ██      ██      
   ██   ██ ██ ██      ██      
   Hi there, this is KIPP.
   How can I help?
   ```

**Warning:** Keep the `src\main\java` folder as the root folder for Java files (i.e., don't rename those folders or move
Java files to another folder outside of this folder path), as this is the default location some tools (e.g., Gradle)
expect to find Java files.

## Citations & References
- The star background from the James Webb Space Telescope was taken from their [Flickr page.](https://www.flickr.com/photos/nasawebbtelescope/52404135772/in/album-72177720301006030/), and is permitted for non-commercial use as per the guidelines [here](https://www.nasa.gov/nasa-brand-center/images-and-media/)
- References to KIPP and image icons were taken from the comic [Absolute Zero](https://thefilmstage.com/read-christopher-nolan-penned-interstellar-prequel-comic-telling-dr-manns-story/)
- GitHub Copilot was used as an auto-complete tool throughout this project.