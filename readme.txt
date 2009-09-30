Written by Paul Pearce - June 2009 - paul@control-q.com
=======================================================

Java Solution to PROBLEM THREE: MARS ROVERS
-------------------------------------------

By executing run.bat, the solution code will be unit tested.

The full problem input is used by NasaTest.testGoodMarsMission test, and
the inputs/output are written to stdout.

At the conclusion of the run, the following reports can be found in the
reports sub-directory :-

    reports/pmd/pmd_report.html    - Code static analysis by PMD
    reports/coverage/coverage.html - Unit test code coverage by EMMA
    reports/junit/html/index.html  - Unit test results by JUNIT
    reports/javadocs               - JavaDocs


Environmental Assumptions
-------------------------

1) Java v1.5 or higher must be found in the PATH
2) Ant v1.7 or higher is found in the PATH (but should work with 1.6).
3) Log4j output can be found in marsrover.log - the logging level is set to INFO
   in log4j.xml
4) Windows environment for the test.bat script.


Very Quick Code Overview
------------------------

The problem indicates that there will be multiple Mars Rovers placed and driven
in a shared Plateau, therefore the Plateau is constructor-injected into a
MarsRover, along with initial positioning and command information.

The method Nasa.MarsMission is reponsible for parsing (and validating) the
freeform text input lines before creating, deploying and running the Mars Rovers.

The two enum's (Command and Orientation) make parameter passing more robust as
well as easily translating input location/command text.

The abstract base class ARover provides common object(s) for use by concrete
extensions, as well as forcing an implementation of the run method.

The interface IRover is used to de-couple implementation details of the Rover's,
as well as provide the opportunity to run them either serially or simultaneously.


Code Assumptions
----------------

Given the following two lines from the problem description :-

1) The rest of the input is information pertaining to the rovers that have been
deployed.

2) Each rover will be finished sequentially, which means that the second rover
won't start to move until the first one has finished moving.

I have assumed that all the Mars Rovers have to be created and placed on the plateau
first, then drive them sequentially.

If a Mars Rover is initially placed outside the boundaries of a plateau, it will
throw an Exception.

If a Move command would cause a Mars Rover to cross a plateau's boundary, it will
be ignored, and a warning will be written to the log.

It's valid to create and position a rover, but give it no commands to execute.


Notes
-----

Files(F) and directories(D) used in this solution :-

D src                   Source code for this solution.
D test                  Unit test source code.
D reports               Output for the reports.
D local_lib             Java libraries used by this solution.
D nbproject             NetBeans use only.

F build.xml             Ant build script
F log4j.xml             Logging configuration
F nbbuild.xml           NetBeans use only.
F *.properties          Used by build.xml for OS dependent values.
F project.version       Use by AntHill or CruiseControl.
F run.bat               Wrapper around Ant to test the solution.
F marsrover.log         Output from logging.


The file nbbuild.xml and the directory nbproject are for the use of NetBeans
v6.5, but they are just a convenient wrapper around build.xml. It is intended
that the command-line run.bat be used to test the solution.



PROBLEM THREE: MARS ROVERS
==========================

A squad of robotic rovers are to be landed by NASA on a plateau on Mars. This
plateau, which is curiously rectangular, must be navigated by the rovers so that
their on-board cameras can get a complete view of the surrounding terrain to
send back to Earth.

A rover's position and location is represented by a combination of x and y
co-ordinates and a letter representing one of the four cardinal compass points.
The plateau is divided up into a grid to simplify navigation. An example position
might be 0, 0, N, which means the rover is in the bottom left corner and facing
North.

In order to control a rover, NASA sends a simple string of letters. The possible
letters are 'L', 'R' and 'M'. 'L' and 'R' makes the rover spin 90 degrees left
or right respectively, without moving from its current spot. 'M' means move
forward one grid point, and maintain the same heading.

Assume that the square directly North from (x, y) is (x, y+1).

INPUT:
The first line of input is the upper-right coordinates of the plateau, the
lower-left coordinates are assumed to be 0,0.

The rest of the input is information pertaining to the rovers that have been
deployed. Each rover has two lines of input. The first line gives the rover's
position, and the second line is a series of instructions telling the rover how
to explore the plateau.

The position is made up of two integers and a letter separated by spaces,
corresponding to the x and y co-ordinates and the rover's orientation.

Each rover will be finished sequentially, which means that the second rover
won't start to move until the first one has finished moving.


OUTPUT
The output for each rover should be its final co-ordinates and heading.

INPUT AND OUTPUT

Test Input:
5 5
1 2 N
LMLMLMLMM
3 3 E
MMRMMRMRRM

Expected Output:
1 3 N
5 1 E


