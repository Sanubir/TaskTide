# TaskTide

TaskTide is a TUI for weekly calendar with tasks and meetings. Done with Java and OOP approach.

You can easily add a task or a meeting with intuitive queries. No more forgotten things with this convenient tool. With all the info easily parsable you'll always know about approaching events.

# Download
To download this project use the *git clone* command from the terminal:
```bash
$ git clone https://github.com/Sanubir/TaskTide.git
```
or use the "Download ZIP" option from the [Github](https://github.com/Sanubir/TaskTide) page and extract the files once downloaded.

# Requirements
 * [Java](https://www.java.com/)
 * [JDK](https://www.oracle.com/java/technologies/downloads/) (you can also use OpenJDK)

Install the above per their documentation.
Feel free to use distro packages.

To check if Java is installed run the this from the terminal:
```bash
$ java --version
```
If everything is ready you are good to go.

# Quick Setup
Run the project by 'cd-ing' into the *TaskTide/* directory and using the *javac* command from the terminal:
```bash
$ cd TaskTide/
$ javac Main.java
```
Next run the compiled classes using *java*:
```bash
$ java Main
``` 

That's basically it, hope you enjoy it.

Repo also includes other examples of mini OOP programs in the subfolders. You can test them the same way as the main application but for their respective folders.

# Examples of the queries in the main app
```
java Main

This program offers these functions:
[1] Add a meeting for a given day
[2] Add a task for a given day
[3] Remove a meeting from a given day
[4] Remove a task from a given day
[5] Show meetings in a given day
[6] Show tasks in a given day
[7] Show meetings in a given day, with a specific priority
[8] Show tasks in a given day, with a specific status
[9] Show meetings in a given day, with a specific priority, starting not earlier than a given hour
[10] Show tasks in a given day, with a specific status, ending not later than a given hour
[0] Exit the program
Choose option [1,2,3,4,5,6,7,8,9,10 or 0]: 1

Valid days: MONDAY TUESDAY WEDNESDAY THURSDAY FRIDAY SATURDAY SUNDAY 
Input a valid day: monday
Input a start time (e.g. 09:45[:59]) (after: 04:00): 3:21
Wrong input!
Input a start time (e.g. 09:45[:59]) (after: 04:00): 12:12 
Input an  end time (e.g. 12:15[:01]) (after: 04:00): 13:02
Valid priorities: LOW MEDIUM HIGH 
Input a valid priority: low
Input a description: The thing

New meeting added.

This program offers these functions:
[1] Add a meeting for a given day
[2] Add a task for a given day
[3] Remove a meeting from a given day
[4] Remove a task from a given day
[5] Show meetings in a given day
[6] Show tasks in a given day
[7] Show meetings in a given day, with a specific priority
[8] Show tasks in a given day, with a specific status
[9] Show meetings in a given day, with a specific priority, starting not earlier than a given hour
[10] Show tasks in a given day, with a specific status, ending not later than a given hour
[0] Exit the program
Choose option [1,2,3,4,5,6,7,8,9,10 or 0]: 7

Valid days: MONDAY TUESDAY WEDNESDAY THURSDAY FRIDAY SATURDAY SUNDAY 
Input a valid day: monday 
Valid priorities: LOW MEDIUM HIGH 
Input a valid priority: low

Meetings for MONDAY with priority LOW:
[1] From: 12:12, To: 13:02, Priority: LOW, Desc: The thing

```

---
_Please notice that this repository is a temporary showcase, which should be polished within the next days._
