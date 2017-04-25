# ng2-generator
Generates a Angular Project including Components, TS-Classes and a Menue defined by Scala-Classes and some settings.

## Current State
 The project is "in work". There are many things todo by now. So what will it provide you? 
 It will generate an Angular2-Project by using the Angular-CLI. The project includes defined components including 
 routing and a drop-down menu on the top.
 
 The generated sources aren't any kind of complicated and will work directly in the browser. Don't look
 at the core of the generator. It's poorly documented and not straight forward. I started this as a fun
 project to see how far this can go and how useful it could be to have a generator for boilerplate and reoccurring things.
 
 ## How can I use/test it?
 Prerequisites:
 You need to have angular-cli installed. It's working with the latest version (now: 1.0.0-beta.31).
 Older versions should run without problems.
 
 Basically the commands "ng generate component <COMPONENT NAME>" and "ng new <PROJECT NAME>" should be available.
 
 What to do now?
 1. Clone this repository
 2. Open it as intellij project
 3. Take a look at the example package and start the SimpleGenerator.class (It will generate the project at c:/tmp/ng2gentest you can simply change it)
 4. Create some classes, add them to the Model and see what happens.
 5. Take a look at the generated components and fit them your needs.

 ## The Generator
 The SimpleGenerator.class should be quite self explanatory, but that is a personal opinion and may not be true.
 
From the root folder navigate to "src/main/scala-2.12/example". You should find the classes Generator, SimpleGenerator and
a folder called model. The folder contains two classes Item and Person. 

It basically does two things. Defining the Model (S = Show, L = List, N = New, D = Delete) and setting up the project. At the moment only the menu and the routes will be 
influenced by that configuration.

    val generator = new Generator("C:/tmp", "ng2gentest", model) with AngularGen
    generator.generate
    
The first line sets the target folder, the project name and the previously defined model.

## Future
Iam not working on this project on a regular basis at the moment. But these two points are the most basic targets
- Inline comments (as documentation)
- Generated dialogs (List, Show, New)
- Communication with the backend via http

Everything with as less configuration as possible but open to all ways of customization.
At the moment there is no reason to go deeper into the configuration possibilities, because the project
is under permanent change and details may change. This would result in more maintenance work which is not
intended because it is a spare time project.