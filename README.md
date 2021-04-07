# The Ultimate Laundry Tracker
## (to make sure your clothing doesn't smell)

## Project Proposal

- The application is useful to **keep track of the amount of times clothing has been worn**, and will help by **setting 
reminders to do laundry**. This application will be able to *track the frequency* of when clothing has been worn, 
with the user *inputting when they wear* a piece of clothing, and the application *setting an alert* when a piece of 
clothing should be put in the laundry basket. This application will also be able to *alert the user* when the closet is 
low in clothing, and allows the user to *set and organize* what is being put in the laundry. 
- From a parent completing tasks around the house, to a college student having to do their own laundry, this application 
is useful for just about anyone. With this, you would be able to keep track of when you should be doing the laundry, and
organize what you want to wash each time. While this application is meant to keep track of when clothing should be 
washed, it also allows users to keep track of how often the same clothing has been worn, and when they should think up 
a different outfit. 
- As a college student, I am often too busy to keep track of how often I wear the same pair of jeans, or when I am 
running low on socks and underwear. I'm sure I am not the only student who has this problem, and this is likely worse 
for people with a larger set of clothing (e.g. a big family?), or with similar looking clothing 
(i.e. the same uniforms to a job). Thus, I think that this application will help anyone with **closet organization** and 
**ensuring proper hygiene**. 

## User Stories
- As a user, I want to be able to add a piece of clothing to a closet and specify information on the clothing, and set 
the frequency I can use it
- As a user, I want to be able to remove clothing from a closet of clothing
- As a user, I want to be able to transfer (remove/add) clothing from a closet to a laundry basket for washing
- As a user, I want to be able to set alerts for when to transfer clothes from a closet to a laundry basket, based on 
the number of times the clothing has been worn, and the frequency I had set earlier (limit frequency <= times worn)
- As a user, I want to be able to set alerts for when a closet is low on a type of clothing
- As a user, I want to be able to transfer (remove/add) clothing from a laundry basket to a laundry room, and set which 
clothing to wash in the laundry room
- As a user, I want to be able to view what's in my closet, laundry basket, or laundry room

- As a user, I want to be able to save my location statuses (i.e. clothing currently in wardrobes of closet, laundry, and laundry 
room) to file
- As a user, I want to be able to load the username of the laundry tracker app 
- As a user, I want to be able to load my locations statuses from file

GUI phase:
- As a user, I want a sound to be played whenever specific buttons have been pressed (i.e. when clothing has been 
transferred, when clothing has been added/removed)
- As a user, I want the app to play the laundry jingle whenever the user exits the application with a laundry joke that pops up

Phase 4: Task 2
I am making use of the Map interface, which has already been satisfied in my Wardrobe class.

Within the Wardrobe class I used a hashmap with keys having type ClothingCategory, and values being list of Clothing.
Because my design only has a set number of clothing categories, I did not implement a method that makes use of put(key, value),
aside from the constructor. 

Methods in Wardrobe that modify values in the map:
- addClothing
- removeClothing

Methods that modify keys:
- setCategoryLowStock
- addClothing

Phase 4: Task 3
Looking at the UML class diagram, and reflecting on my attempt at the UI in the first phase, the design depicting the relationships
between the classes within the model package was made in order to increase cohesion within the classes (i.e. ClothingCategory and LaundryLocation),
while maintaining low/moderate coupling. By refactoring and creating new classes, this resulted in the use of the Map interface, upon the suggestion of my Ta,
resulting in the Wardrobe class having an association with the Clothing and ClothingCategory class (using ClothingCategories as 
keys, and list of Clothing as values in the Map). Additionally, this resulted in the use of the abstract LaundryLocation class, and 
subclasses Closet, LaundryBasket, and LaundryRoom. By refactoring and adding classes, this also benefited my work flow as it allowed
me to look at a smaller set of code to adjust/add on to.
However, while coupling was maintained low/moderate between classes of the model package, the diagram does show some unnecessary 
coupling occurring at the classes making up the gui package. This includes classes like the Menu classes (i.e. ClothingDisplay, Closet, GenericLocation) 
having an association with both the LaundryTracker and LaundryLocation class even though the LaundryTracker itself has an association with the LaundryLocation 
class. While not visible in the diagram, a few classes like the LaundryTracker don't necessarily have good cohesion, which made the class hard to understand at some points 
when going through the fourth phase (e.g. having to scroll a lot due to multiple methods or long implementations). (I tried to fix this later on by extracting inner classes
from the Menu classes rather than leaving them within the class like the 4 classes underneath the LaundryTracker class in the diagram)

If I had more time to work on my project I would probably do some of the following:
- extract the remaining inner classes within the LaundryTracker class 
    - *(initially the save and load classes were extracted, but I had problems with having the gui actually load from file, so I would try to solve this problem as well)
- I already had a getter method for the locations within the LaundryTracker so rather than having an extra field/s of LaundryLocation
in my Menu classes, I would use the getter method on the LaundryTracker instead (this would remove the unnecessary coupling shown in the
diagram)
- change the implementations of some methods (or add methods) within some classes of the locations package in the gui package (due to 
being short on time, some implementations may have been made more complicated than they needed to be)
- I might also want to change the display of some frames, as I think the desktop might get messy overtime if the user decides not to close frames
    - e.g. the category dropdown display opens a list frame whenever the user chooses a clothing category; instead I would have the dropdown be part of the list frame
           so that a new list is displayed in the same pane whenever the category changes. This would reduce the amount of frames being opened.
    - have frames close whenever the menu that opened them is also closed; then the user wouldn't have to individually close all the frames they opened
- Refactor the addClothing class; the user has to press enter for all the text fields, or a null value is used for that field (I would change it so pressing Enter is not required)
- Comparing with the ui, whenever the user decides to close the application a joke pops up. In the gui, I made it so the application would close as soon as the X button was pressed,
and the joke pops up at the beginning instead. I would make it so that the joke pops up in the situation that the X button is pressed, instead, as well as including a 
sound.
- Rather than the LaundryTracker having 3 Json Reader and Writer fields, I would have the class have only one, and change the implementations of the save and load methods so that one file would
have data on all three laundry locations, rather than 3 separate files


 