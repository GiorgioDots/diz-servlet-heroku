# DizServletHeroku

## Configure
___You need maven and heroku cli___

Clone this project, and run mvn package from the root (do this every time you modify something).

To run local use this command from the root: `target/bin/webapp.bat`

Push it to you heroku application. 

__You can try my app [here](https://diz-servlet.herokuapp.com/hello)__
___
## API Description

Base URL: __your-heroku-app-url/hello__

- ### GET - get meaning:

    - Parameters: ?word=_wordToGetMeaning_

    - Response example:

        ```
        {
            "word": [
                "meaning1",
                "meaning2"
            ]    
        }
        ```

- ### PUT - add word:

    - Parameters: ?word=_wordToAdd_&meaning=_meaningOfTheWord_

    - Response example:

        ```
        {
            "wordAdded": [
                "meaning"
            ]    
        }
        ```


- ### POST - modify word:

    - Parameters: ?word=_wordToModify_&meaning=_meaning_&index=_indexOfArrayToModify_
    
    Note: __if the index is not specified, it will add a meaning__

    - Response example:

        ```
        {
            "word": [
                "meaning1",
                "meaning2"
            ]    
        }
        ```

- ### DELETE - delete a word and his meaning:

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