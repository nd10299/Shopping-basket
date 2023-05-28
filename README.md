# Shopping-basket
## Software requirements:
1.Install Java Developement Kit(JDK)

2.Install SBT

SBT is essential to build and run this code.
## Setup
Clone this repo using git clone command.
```bash
git clone <repo url>
cd Shopping-basket
```  
Use command ```sbt ``` in command line to enter into sbt shell.
## Compile
In sbt shell ```clean compile``` command to compile the source code. 
## Test
Use command ```test``` to run the tests of the project. This will execute test suite and display the test result.
## Run
To run the project use command ```run Apples Milk Soup```. provide basket value as an argument with run command.

Try run command with different arguments to test different scenarios.

This project is capable to handle errors like wrong goods name or no goods provided.
