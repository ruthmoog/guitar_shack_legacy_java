# Guitar Shack

Exercise from codemanship https://github.com/jasongorman/guitar_shack_legacy_java

## My Notes:
### Modularity

Core ideas on modularity in software were well established by the late 70s. Swapping components in our code allows us to make easily unit testable software (we can test a modular unit without calling a database or writing  to file, or calling webservices, for example).

We can compare code modularity with hifi systems...
Integrated hifis have a bunch of components welded together, whereas
modular hifis have a separate piece for each job (cassette, speakers, radio etc) flexibly connected together to make a hifi system.
The equivalent with integrated software is where code to do any job (save to the database, the web front end etc), are all in the same module. 
- We would like to be able to swap out or wire different components to give us choice, like with the modular hifi.  Thats what modularity is about.


### SHOC

SHOC is a collection of design principles to keep code modular - easier to test, easier to change.

modules should:

- **be swappable**
you should be able to take one thing & replace with something else that fits into that slot

- **hide internal workings** 
we want the data to be hidden and the working to be as small as possible. Limit the ripple effect when you make a change - things that change together belong together, encapsulation!

- **do ONE job**
much greater composibility if we have units that do only one thing like our cassette player, speakers etc.  UNIX Pipes is a set of programs which do one job, you can write scripts that chain these together (eg to uppercase)

- **Have client driven interfaces**
should be designed from the clients POV - what does it need to do only expose the features which the client needs to use - what does the client require.

### Feedback cycles

Testing pyramid

>>> **System tests**, aka **end to end** - as few as possible

>> **Integration tests** - takes longer to run, inter dependencies

> **Unit tests** - no external dependencies, runs fast, same memory address space

slow running tests can bring down businesses > slow builds > slow release cycles > expensive change > bugs > :(

Using SHOC principles make unit testing easier - if your codebase's testing pyramid is not bigger at the base, making the code more modular will allow you to rebalance your testing pyramid.

### Example...

eg Room carpet... Room interface, rectangular room calculates the area, carpet calculates how much carpet you need. The test decides what shape room the code doesn't care about the room shape.



> ## guitar_shack_legacy_java

> "Legacy code" version of the Guitar Shack implementation for folk to practice on

> I refuctored my Java solution for the Guitar Shack exercise into a Big Ball of Mud with hardwired external dependencies that make unit testing currently impossible.

> There are, of course, no automated tests. But there's a Program.main() method you can run with 2 arguments to process a sale: product ID (int) and quantity sold (int).

> The program will check stock and sales data from two AWS web services to decide if an alert needs to be sent to reorder new stock.

> The Guitar Shack owner wants to change the logic of how we calculate the reorder level for a product. Currently it uses the rate of sales during the previous 30 days.

> It must now calculate the rate of sales during the NEXT 30 days, but in the /previous/ year. e.g., if today's date is Dec 24th 2020, the rate of sales will be calculated for sales of that product which happened during Dec 25th 2019 - Jan 24th 2020. 

> To make this change safely, you're going to need to add fast-running tests. To add fast-running tests you're going to have to separate some of the concerns in the Big Ball of Mud.

> Once you've got some fast-running tests around it, make the change the customer requested. Then, while you're in there, apply the "Boy Scout Rule" and clean up the code a bit more to make life easier for the next person who has to work on it.

> Happy refactoring!


