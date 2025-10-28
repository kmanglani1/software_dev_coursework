# Software Development Coursework ReadMe
- A concurrent multi-player card game where players aim to hold 4 cards of the same value in order to win. 

## Features
- Gameplay works by having each player n draw from an assigned deck n+1, and discard to the neighbouring deck n+2.
- The game reads a “pack” file of 8*n integers, then runs as one thread per player (n players).
- The game is thread-safe: includes synchronized classes, and a volatile flag.
- The game's final outputs are stored in the relevant player and deck-content files.

## Installation
- Complete the following commands in the terminal for installation and compilation:
- mkdir -p out/classes out/test
- javac -d out/classes src/cardgame/*.java

## Usage
- A pack file must be prepared (with 8*n integers), where each integer is separated by a new line.
- The game runs with the following command: java -cp out/classes cardgame.CardGame
- Answer the required prompts regarding the number of players, and pack file location.
- The gameplay's final outputs can be found the in the player and deck output files.

## Requirements
- Java version 11+ is required to run the game.
- JUnit version 4.13.2 and Hamcrest 1.3 jars installation is required to test the game.

## Contributions
- Contributions are welcome by forking the code.
- Current version to be used for marking purposes.

## License
- Coursework Project.
- Authors: Keira Manglani and Tanisha Sharma