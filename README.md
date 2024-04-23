#  WebCrawler - Group 12

## About / Usage

- This Java application serves as a robust web crawler, designed to traverse websites starting from a user-provided URL and explore them up to a specified crawl depth.

- This application accepts several command-line arguments to control its web crawling and processing behavior:

  - URL:
      - Description: The starting URL from which the crawler will begin its operation.
      - Command: Enter the URL when prompted: Please enter the URL:
      - Example: https://example.com
  - Crawl Depth:
      - Description: The maximum depth the crawler will traverse from the starting URL.
      - Command: Specify the depth when prompted: Please enter the Crawl depth
      - Example: 3 (This would mean the crawler goes three levels deep from the starting page.)
  - Domains to be Crawled:
      - Description: Limits the crawler to specific domains to avoid wandering off to unwanted areas of the internet.
      - Command: Enter the domains when prompted, separated by commas: Please enter the domains to be crawled (comma-separated, no spaces):
      - Example: example.com,sub.example.com
  - Translation Language (Optional):
      - Description: If specified, the text found during crawling will be translated into the chosen language.
      - Command: Enter the language code when prompted, or press 'Enter' to skip: Please enter the language the text should be translated to (Optional* -> Press 'Enter' if You want to skip):
      - Example: fr (French), Leave blank to skip translation.

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

The crawler implements  the following features:
- input the URL, the depth of websites to crawl, and the domain(s) of websites to be crawled
- create a compact overview of the crawled websites
- record only the headings
- represent the depth of the crawled websites with proper indentation (see example)
- record the URLs of the crawled sites
- highlight broken links
- find the links to other websites and recursively do the analysis for those websites (it is enough if you analyze the pages at a depth of 2 without visiting further links, you might also allow the user to configure this depth via the command line)
- store the results in a single markdown file (.md extension)
