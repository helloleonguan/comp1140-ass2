Final mark:  /11

Comments:



Key:  'Y' - yes, 'N' - no, '~' - partial compliance

## Criteria for gaining 4 / 11

* All of the required files are correct and have been committed and pushed.
 * admin/originality-G.md
 * admin/contribution-G.md
 * admin/best-<uid>.md
 * admin/G-features.md
 * All .java files
 * game.jar
 * presentation.pdf
* Authorship of each class needs to be clearly stated in a comment of each class. (Note: 'authorship details' is fairly open-ended. It may mean stating that the code was written by the group, or by one or two members, or that it was heavily adapted from some third party. Either way, it should be absolutely clear to the reader which person or persons were responsible for the code and the ideas behind it.).
* Mercurial logs must indicate appropriate use of version control.
* The program must compile and run (from the .jar file).

## Criteria for gaining 6 / 11

*The previous criteria plus...*
* Your game must have at least pass-level functionality.
 * A working implementation of a basic Blokus game implemented in JavaFX (supporting just the normal four players).
 * Executable on a standard lab computer from a runnable jar file, `game.jar`, which resides in the root level of your group repo.
 * Successfully implements `BlokGame.legitimateGame()` and during the game prevents the player from making illegal moves.
 * Successfully implements `BlokGame.scoreGame()`, and correctly presents the score for the game.
* You have made appropriate use of object-oriented features such as inheritence and enum types.
* Your presentation pdf must have all required components.

## Criteria for gaining 7-8 / 11

*The previous criteria plus...*
* The program is well designed.
* Comments are clear and sufficient.
* Your game must have at least credit-level functionality.
 * The game supports two, three, and four human players, following the suggestions in the [rules](http://www.boardgamecapital.com/game_rules/blokus.pdf) for two and three player games.
 * Successfully implements `BlokGame.makeMove()`,  providing an basic computer opponent (need not be a strong player).
* Your coding style is good.
* You make good / appropriate use of JUnit tests throughout the project.

## Criteria for gaining 9-11 / 11

*The previous criteria plus...*
* Your design and code must be of very high quality.
* Your program demonstrates interesting extensions that go beyond the basic task.
* Your game must have at least distinction-level functionality.
 * The game allows a single player to play the *eighty four* game described in the [rules](http://www.boardgamecapital.com/game_rules/blokus.pdf).
 * The game identifies all unplayable pieces when a turn is being taken (the unplayable pieces could be dimmed, indicating to the player that they are unplayable).  An unplayable piece is one that can't possibly be used in the given game state.   A given player's game ends once all of their unplaced tiles are unplayable.
* Your game works well and is easy for a new user to run.
* Your game must have at least high-distinction-level functionality.
 * The game implements a good computer opponent.

