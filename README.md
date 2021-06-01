# Personenkartei

## The task

Develop a software solution (OOP) that stores personal data (CRUD) and displays it in a table.

## IDE

I used IntelliJ by Jetbrains, but you can of course use Eclipse or any other IDE, if you so desire.

### How to set up Intellij

1. Start IntelliJ
2. Select `Open Project` in the start menu
3. Select the `Personenkartei` folder you acquired from GitHub by cloning or downloading the repository
4. The IDE will load up the project and its dependencies
5. You might have to reconfigure your `debug configuration` so that it can fit your expectations
6. You are good to go!

### Debug mode

To set the application into debug mode, please start with the program argument `debug`.

### The workspace

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies
- `.run`: IntelliJ debug configuration

## Dependencies

| Name | Link |
| :------------- | --- |
| `Apache` Commons CSV | [Visit the Documentation](https://commons.apache.org/proper/commons-csv/) |
| `Apache` Commons IO | [Visit the Documentation](https://commons.apache.org/proper/commons-io/) |
| `Apache` Commons Validator | [Visit the Documentation](https://commons.apache.org/proper/commons-validator/) |

## To-Do

1. Import of a CSV file with compatible structure ✓
2. Tabular representation of the persons from the CSV file ✓
3. Creation of new data records using the same scheme ✓
4. Editing of existing data records ✓
5. Export of all data as CSV file with the same scheme in "realtime" ✓