# Jetbrains Academy - Flashcards

My solutions for the Jetbrains Academy Problem Flashcards

https://hyperskill.org/projects/83

The solution is build up step by step over several stages. 
Stage 1 is the first and simple one. The following stages 
build up on the previous stages and get more and more advanced.
There are seven stages in total.

Because each stage is completely independent of the previous one,
IntelliJ might show some warnings about duplicated code between 
the stages.

## Stage 1

[click here to see description @ JetBrains Academy](https://hyperskill.org/projects/83/stages/457/implement)

Just some super simple output.

just execute this:

    gradle -PmainClass=stage1.MainKt run --console=plain
    
    Card:
    animal
    Definition:
    dog

## Stage 2

[click here to see description @ JetBrains Academy](https://hyperskill.org/projects/83/stages/458/implement)

Still super simple. Just enter three things.

just execute this:

    gradle -PmainClass=stage2.MainKt run --console=plain

    bla
    foo
    foo
    
    Your answer is right!

## Stage 3

[click here to see description @ JetBrains Academy](https://hyperskill.org/projects/83/stages/459/implement)

Here you can create the first flash cards.

just execute this:

    gradle -PmainClass=stage3.MainKt run --console=plain

    Input the number of cards:
    1
    Card #1
    test
    The definition for card #1
    foo
    Print the definition of "test":
    bar     
    Wrong. The right answer is "foo".

## Stage 4

Here we check for duplicates.

[click here to see description @ JetBrains Academy](https://hyperskill.org/projects/83/stages/460/implement)

just execute this:

    gradle -PmainClass=stage4.MainKt run --console=plain
    
    Input the number of cards:
    2
    Card #1
    test1
    The definition for card #1
    foo
    Card #2
    test2
    The definition for card #2
    f00
    Print the definition of "test1":
    foo
    Correct!
    Print the definition of "test2":
    foo
    Wrong. The right answer is "f00", but your definition is correct for "test1".

## Stage 5

We add a menu, so the user can choose his next action.

[click here to see description @ JetBrains Academy](https://hyperskill.org/projects/83/stages/461/implement)

just execute this:

    gradle -PmainClass=stage5.MainKt run --console=plain

    Input the action (add, remove, import, export ask, exit):
    add
    The card:
    test
    The definition of the card:
    foo
    The pair ("test":"foo") has been added
    Input the action (add, remove, import, export ask, exit):
    exit
    Bye bye!

## Stage 6 

We add statistics, so the user can check which cards he missed the most.

[click here to see description @ JetBrains Academy](https://hyperskill.org/projects/83/stages/462/implement)

just execute this (one line):

    gradle -PmainClass=stage6.MainKt run --console=plain
    
    Input the action (add, remove, import, export ask, exit, log, hardest card, reset stats):
    log
    File name:
    test
    The log has been saved.
    Input the action (add, remove, import, export ask, exit, log, hardest card, reset stats):
    add
    The card:
    test
    The definition of the card:
    foo
    The pair ("test":"foo") has been added
    Input the action (add, remove, import, export ask, exit, log, hardest card, reset stats):
    ask
    How many times to ask?
    1
    Print the definition of "test":
    foo
    Correct!
    Input the action (add, remove, import, export ask, exit, log, hardest card, reset stats):
    hardest card
    There are no cards with errors.
    Input the action (add, remove, import, export ask, exit, log, hardest card, reset stats):
    exit
    Bye bye!

## Stage 7

We add import and export to files, so cards can be stored and reused.

[click here to see description @ JetBrains Academy](https://hyperskill.org/projects/83/stages/463/implement)

just execute this (one line):

    gradle -PmainClass=stage6.MainKt run --console=plain

    Input the action (add, remove, import, export ask, exit, log, hardest card, reset stats):
    add
    The card:
    test
    The definition of the card:
    foo
    The pair ("test":"foo") has been added
    Input the action (add, remove, import, export ask, exit, log, hardest card, reset stats):
    export
    File name:
    myCards.txt
    1 cards have been saved.
    Input the action (add, remove, import, export ask, exit, log, hardest card, reset stats):
    exit
    Bye bye!