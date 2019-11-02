# DizServletHeroku

## Configure

### Install maven and heroku cli

- Manven:
    - Download maven from [this link](https://maven.apache.org/download.cgi);
    
    - Follow [this guide](https://maven.apache.org/install.html) to install it.

- Heroku:
    - Follow [this guide](https://devcenter.heroku.com/articles/heroku-cli#download-and-install) to install it.

### Configure project

In the root create a __pom.xml__ file and add this content:

```
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.heroku.sample</groupId>
  <artifactId>embeddedTomcatSample</artifactId>
  <version>1.0-SNAPSHOT</version>
  <packaging>jar</packaging>
  <name>embeddedTomcatSample Maven Webapp</name>
  <url>http://maven.apache.org</url>
  <properties>
    <tomcat.version>8.5.23</tomcat.version>
  </properties>
  <dependencies>
    <dependency>
        <groupId>com.googlecode.json-simple</groupId>
        <artifactId>json-simple</artifactId>
        <version>1.1.1</version>
    </dependency>
    <dependency>
        <groupId>org.apache.tomcat.embed</groupId>
        <artifactId>tomcat-embed-core</artifactId>
        <version>9.0.27</version>
    </dependency>
    <dependency>
        <groupId>org.apache.tomcat.embed</groupId>
        <artifactId>tomcat-embed-jasper</artifactId>
        <version>${tomcat.version}</version>
    </dependency>
    <dependency>
        <groupId>org.apache.tomcat</groupId>
        <artifactId>tomcat-jasper</artifactId>
        <version>${tomcat.version}</version>
    </dependency>
    <dependency>
        <groupId>org.apache.tomcat</groupId>
        <artifactId>tomcat-jasper-el</artifactId>
        <version>${tomcat.version}</version>
    </dependency>
    <dependency>
        <groupId>org.apache.tomcat</groupId>
        <artifactId>tomcat-jsp-api</artifactId>
        <version>${tomcat.version}</version>
    </dependency>
  </dependencies>
  <build>
    <finalName>embeddedTomcatSample</finalName>
    <plugins>
        <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>appassembler-maven-plugin</artifactId>
            <version>2.0.0</version>
            <configuration>
                <assembleDirectory>target</assembleDirectory>
                <programs>
                    <program>
                        <mainClass>launch.Main</mainClass>
                        <name>webapp</name>
                    </program>
                </programs>
            </configuration>
            <executions>
                <execution>
                    <phase>package</phase>
                    <goals>
                        <goal>assemble</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
  </build>
</project>
```

Now run `mvn package` from the root of this project.

### Test it locally

From the root of the project run `target/bin/webapp.bat`, it will start the server to this URL __localhost:8080__.

### Deploy to heroku

- Create a new heroku app;

- From __git bash__ cd into your project and run `heroku login` (then login with your credentials);

- Run: 
    - `heroku git:remote -a your-app-name`

    - `git add . | git commit -m "first commit" | git push heroku master`.

- Enjoy your application!

__You can try my app [here](https://diz-servlet.herokuapp.com/hello)__

## API Description

Base URL: __your-heroku-app-url/hello__

### GET - get meaning:

- Parameters: ?word=_wordToGetHisMeaning_

- Response example:

        ```
        {
            "word": [
                "meaning1",
                "meaning2"
            ]    
        }
        ```

### PUT - add word:

- Parameters: ?word=_wordToAdd_&meaning=_meaningOfTheWord_

- Response example:

        ```
        {
            "wordAdded": [
                "meaning"
            ]    
        }
        ```


### POST - modify word:

- Parameters: ?word=_wordToModify_&meaning=_meaning_&index=_indexOfArrayToModify_

Note: __if the index is not specified, it will sostitute the index 0__

- Response example:

        ```
        {
            "word": [
                "meaning1",
                "meaning2"
            ]    
        }
        ```

### DELETE - delete a word and his meaning:

- Parameters: ?word=_wordToDelete_

- Response example:

        ```
        {
            "wordDeleted": [
                "meaning1",
                "meaning2"
            ]    
        }
        ```