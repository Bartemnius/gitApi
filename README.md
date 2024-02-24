# GitApi


## Table of Contents

1. [Description](#description)
2. [Installation](#installation)
3. [Usage](#usage)
4. [Documentation](#documentation)

## Description 

The purpuse of this application is to make few calles to git API. Thru this API you can check if user exist and if so you can list all of user's repositories that are not forks along with all the brnaches with last commit sha for each branch.  

## Installation

Steps to install the project:

I. Download the reposiotry or the newest release form github. 

II. Make sure that u have java in version 21 install on your PC!

III. If u downloaded repository u can run this project in your selected IDE. If you downloaded the jar file u can run the jar in terminal thru command:
```
java -jar gitApi-0.0.1-SNAPSHOT.jar. 
```

Application should start properly in both cases.

## Usage 

If you followed the steps from previous section now you can retieve some information about github user of your choice.
To do so you can just paste this commands into your browser or you can use some dedicated program for REST API call testing like POSTMAN. 
The application will be deafult run on your localhost on port 8080. 
The info that you can get:


 GET USER -> will return login of user 

http://localhost:8080/api/v1/users/{user_name} 

For example: http://localhost:8080/api/v1/users/Bartemnius

Will return: 
```json
{
    "login": "Bartemnius"
}
```



GET LIST OF REPOS -> will return the list of reposiotries with the information if the reposiotry is fork or not. 

http://localhost:8080/api/v1/users/{user_name}/repos

For example: http://localhost:8080/api/v1/users/Bartemnius/repos

Will return: 
```json
[
    {
        "name": "gitApi",
        "fork": false
    },
    {
        "name": "LawAndOrder",
        "fork": false
    },
    {
        "name": "QuizApplication",
        "fork": false
    },
    {
        "name": "Registration",
        "fork": false
    },
    {
        "name": "RobotControll",
        "fork": false
    },
    {
        "name": "todoList",
        "fork": false
    }
]
```




GET LIST OF BRANCHES -> will return the list of branches and last commit sha for each branch for given repo and user name 

http://localhost:8080/api/v1/users/{user_name}/{repo_name}/branches

For example: http://localhost:8080/api/v1/users/Bartemnius/todolist/branches
Will return:
```json
    {
        "name": "master",
        "commit": {
            "sha": "6a72b18884a46740a977ee4a4f879e6c929fda2d"
        }
    }
```




GET FULL INFO -> will return the full info (three previos requests combain) about user (if exists)

http://localhost:8080/api/v1/getInfo/{user_name}

For example: http://localhost:8080/api/v1/getInfo/Bartemnius

Will return: 
```json
{
    "login": "Bartemnius",
    "repos": {
        "todoList": [
            {
                "name": "master",
                "commit": {
                    "sha": "6a72b18884a46740a977ee4a4f879e6c929fda2d"
                }
            }
        ],
        "QuizApplication": [
            {
                "name": "master",
                "commit": {
                    "sha": "faf2ee5f368db113e92b1d23bdabf62e35499059"
                }
            }
        ],
        "Registration": [
            {
                "name": "master",
                "commit": {
                    "sha": "ff92a37b7856aed75604fe43ea09411fc95c1136"
                }
            }
        ],
        "gitApi": [
            {
                "name": "master",
                "commit": {
                    "sha": "af1a3e030f3bc42275c756d3307317bf1f7739b0"
                }
            }
        ],
        "LawAndOrder": [
            {
                "name": "master",
                "commit": {
                    "sha": "34f5b2194bb80b77410ac57663d661b8dc9a8bf4"
                }
            }
        ],
        "RobotControll": [
            {
                "name": "main",
                "commit": {
                    "sha": "6a897093b06687001fced92604658b6277018b86"
                }
            }
        ]
    }
}
```

## Documentation

For more documentation of if want to develop this application further for your own purpose please refer to github API documentation -> https://docs.github.com/en/rest?apiVersion=2022-11-28




 


