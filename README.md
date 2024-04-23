#  WebCrawler - Group 12

## About

- This Java application serves as a robust web crawler, designed to traverse websites starting from a user-provided URL and explore them up to a specified crawl depth.


## Run / Setup

`Through IntelliJ`

1. IntelliJ IDEA is an integrated development environment written in Java for developing computer software written in Java, Kotlin, Groovy, and other JVM-based languages.
Key Features:

2. When the project is created, in the Project tool window (Alt 01), locate the src | main | java | Main.java file and open it in the editor.
In the editor, click the  gutter icon to run the application and select Run 'Main.main()'. IntelliJ IDEA runs your code. After that, the Run tool window opens at the bottom of the screen.

Read more: [IntelliJ Docs](https://www.jetbrains.com/help/idea/run-java-applications.html#run_application)

`Command Prompt (Windows)`

1. Compile the Java Code:
First, you need to compile your Java code to create .class files. Assuming your current directory is the project root, and your source files are in src/main/java, you would compile your Main.java like this:
```bat
javac src/main/java/crawler/Main.java
```
2. Run the Compiled Java Program:
   After compiling the code, you need to run the .class file. You need to set the classpath to the root directory of your class files and specify the fully qualified name of the main class. Assuming you're still at the project root and your class files are inside src/main/java, you can run your program with:
```bat
java -cp src/main/java crawler.Main
```
3. Running with Arguments:
```bat
java -cp src/main/java crawler.Main arg1 arg2 arg3
```

Read more: [WikiHow](https://www.wikihow.com/Compile-%26-Run-Java-Program-Using-Command-Prompt#:~:text=At%20the%20command%20prompt%2C%20type,program%20after%20it%20is%20compiled.)

`Terminal (macOS)`

1. Open the Terminal
2. Navigate to the Project Directory
   1. Once the Terminal is open, navigate to the directory where your Java files are located using the cd (change directory) command. For example, if your project is located in your Documents folder, you might use a command like:
```bat
cd ~/Documents/WebCrawler
```
3. Compile the Java Code
```bat
javac src/main/java/crawler/Main.java
```
4. Run the Compiled Java Program
```bat
java -cp src/main/java crawler.Main
```
5. Passing Arguments
```bat
java -cp src/main/java crawler.Main arg1 arg2 arg3
```

Read more : [StackOverflow Answer](https://stackoverflow.com/a/2361108/13667327)

## Features

-Apple AR

Apple AR is a platform that allows developers to create augmented reality experiences for iOS, macOS, and tvOS devices. AR is a technology that overlays digital information onto the real world, allowing users to see and interact with virtual objects in their environment.

Apple AR is powered by ARKit, a framework that provides developers with the tools they need to create AR apps. ARKit uses the device's camera and sensors to track the user's position and orientation in the real world. This information is then used to place and render virtual objects in the scene.

-RealityKit

RealityKit is a high-performance 3D simulation and rendering framework for AR and VR. It is built on top of ARKit and provides developers with a number of features that make it easier to create realistic and immersive AR experiences.

RealityKit includes features such as:

Physically based materials
Environment reflections
Grounding shadows
Camera noise
Motion blur
Custom rendering
Custom systems

-Apple AR and RealityKit in Use

Apple AR and RealityKit are being used to create a wide variety of AR experiences, including:

Games
Educational apps
Shopping apps
Navigation apps
Product design apps
Medical apps
