## 2496 Code Test Protocols and Procedures

The following is the protocol to be followed at ALL times when it comes to writing, testing,
and running code on team 2496's robot. 

**I. Goals:**

- Establish strict control over what code goes onto the final robot  
- Maintain high standard of code
- Ensure all code is reviewed 
- Allow efficient, but controlled testing 
- Give hands-on opportunity to programmers at all levels
- Keep a safe testing environment
- Minimize failure at competition 

**II. Developing Code:** 
- All development will be done on Windows OR a Linux flavor
- No development should occur on either lab RPI server at any time
- The following list includes the bare minimum required tools and environment setup:
	- Eclipse Mars or Neon 
	- JRE and JVM no older then 1.8_65 
	- FRC wpilibj toolchain and plugin
	- FRC driver station
	- Appropriate git client on Windows or package on Linux
	- Arduino IDE OR Amtel AVR GCC
- Early stages of development code should stay on local machines, or be placed in RPI file backup (see V)
- When applicable, attempt to follow the structure illustrated in CodeStructure.md. If this structure isn't followed, your code may be removed or ignored
- Code in further stages can be put in a personal branch OR 'development' branch 
    
**III. Testing Code:**
-  When you are ready to test, follow this process: 
	1. Push code to 'untested' branch, ask your lead to merge and review, backup as needed 
	2. Be careful of overwriting other work, if needed, queue untested branch changes and coordinate with others. 
	3. All members are allowed to download the untested branch to the lab test-board, given the following conditions are met:
		- You call out to inform everyone in the room you are testing
		- Testing is documented in notebooks or logs
		- You check that it is a safe environment
	4. Once you have tested on the board, optional further testing may be advisable on the test robot. Consult coaches and the team lead to have code downloaded onto the test robot. 
- In rare cases, testing may not be needed on the test board. The alternative procedure is described below:
	1. Code can be loaded directly to the Robot in small, controlled unit testing 
	2. Coaches and team lead must give approval, and monitor the test 
	3. Extensive documentation is required, including justification for skipping test steps 
	4. Have at least 2 testers, one prepared to E-stop robot if/when necessary
	5. Keep room clear, warn anyone in the vicinity 
- The following is competition test procedure, ONLY to be used at competition as needed:
	1. Critical code changes may be needed at competition to keep the Robot 
	2. First ascertain the value of this change to the team: Will it increase success? What is the risk/reward trade off? Is there a serious risk off failure from this change? 
	3. Once going through this, consult any available coaches, you must get their authorization for the change 
	4. Attempt to test to the best of your ability within the pit, robot on the cart, make sure that everyone is aware and safe 
	5. Observe in next match and change as needed
- Note the following: 
	- Even if you feel that running your code on the lab test-board or test robot is unnecessary, often times it can help you locate difficult to find or even dangerous bugs through basic I/O and debugging techniques
	- No code MAY EVER been merged into master unless tested or is competition critical.
	- All code in master needs to be tested AND documented 
	- Documentation should be done by both leads and the author of relevant code

**IV. Debugging Code:**

- TBD

**V. Git Repo/Backing up code:** 
- Code on the git-server RPI will be automatically to github and the backup sever with "backup.sh" file contained in the repo
- Code in development may be placed in your users Documents folder on the backup server. This is highly suggested 
- ...
	 
*Written by Ashwin Gupta on 5/15/2017* 