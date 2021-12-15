=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: 45039816
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Arrays

	My use of 2D arrays in my program implementation was necessary in order for me to
successfully store and access the values of the 4x4 game board grid that would be displayed
throughout game execution. I used a 2D array to create a field called board in my game_2048
class - this field was a 4x4 Integer array.


  2. Collections/Maps (LinkedList, HashMap)

	My use of Collections and Maps in my program was necessary because I used them to
implement an undo functionality in my code. I push the current board into a LinkedList<Integer[][]>
data structure and the current score within a separate LinkedList<Integer> data structure. Using this functionality, I could push and pop elements onto the two LinkedLists with the click of a button and display previous game states when desired.


  3. File I/O

	My use of File I/O was to implement a Save functionality within my game. I would 
iteratively write all 4 rows of a selected board state, as well as that board's score on
a line separated by white-space, and continue to do so until the ends of the two LinkedLists were
reached. In addition, I had my load functionality work so that it would iteratively construct
elements of my LinkedLists by reading lines of the .txt file and recreating the desired game 
state.


  4. JUnit Testing

	My use of JUnit Testing was necessary to check my implementation of movement, reset, 
and other core game model functionalities. One example of my testing was that I tested the
reset button by having the reset button take the game back to the same starting point each time
and checking whether the button successfully worked. In addition, I also tested the undo,
save/load, and instruction buttons to verify their correct implementation.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

	My code contains the GameBoard, game_2048, GameTest, and Run2048 classes. 

The first class contains the code for implementing the GUI's board where action will occur (such as updateStatus and paintComponent), as well as calls to the gameplay methods for when a GUI is instantiated (including save/load, movement, reset/undo, and instruction methods). 

	The game_2048 class was how I implemented my "model" component of the Model-View-Controller framework - I defined the relevant fields for 2048, created necessary setter/getter methods for external components to update and access fields and board elements, and implemented the actual gameplay methods (whose names are written above) that I would use to play the game itself. (I simulated these methods in a main method within the game_2048 class.)

	The GameTest class was where I wrote all of my test cases that I used to ensure the accuracy of my gameplay and code implementation. I tested all of my buttons' functionalities, as well as correct movement.

	Finally, my Run2048 class (which was modeled based off of RunTicTacToe, the sample turn-based game) created the "View" and part of the "Controller" functionality - specifically, in this class, I defined the GUI's frame, as well as the GameBoard and labeled panels, and also added the board.reset() command to actually begin the gameplay when called by Game.java. 


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

	An issue I faced that was a persistent bug was the implementation of my LinkedLists to create
an undo functionality. Specifically, when I attempted to push a new current state to a single LinkedList that stores all past game states, my code seemingly erased all past states and, instead, had all elements in the LinkedList now become the new state that was just added (I verified this using my save functionality, which generated such an output.) This has made it difficult for me to successfully undo states that were just added because there are technically no old states for the LinkedList to display. My solution was to split up the saved boards and saved scores into two separate LinkedLists and push/pop relevant values to each LinkedList when needed.


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

	I would say I encapsulated my code well throughout - I extensively use getter and setter methods in my game_2048 class (the "Model" class) and make the corresponding fields in that class private so that the only way other classes, such as the GUI implementation in my GameBoard class, can access and edit values in game_2048 is through an encapsulated method. In addition, I use clear switch cases in my code to distinguish between the four different types of movement in the game (up, down, left, right) and define how the game should unique operate based on each key press.

If given the chance, I would refactor my code so that I simplify the switch-case code in my makeMove method (from my game_2048 class) so that it calls some helper function with parameters corresponding to the direction I go to, rather than defining large blocks of code for each one (primarily because a large amount of logic is repeated in each direction and my code could be simplified if that weren't the case.)


========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

N/A
