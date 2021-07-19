## Code Sharing Platform

In this project I am using Spring Boot to learn how to combine the backend and frontend by
building a platform where users can share code snippets. You can access it in the browser
to create new code snippets, see the latest codes, and access specific codes by ID. Code 
snippets are optionally access restricted by number of views or with a time limit. The data
is stored in a local database file. Features syntax highlighting using highlight.js.


### Usage

The server listens on `http://localhost:8080`. Bold values in the paths are placeholders 
and must be replaced by the appropriate numbers before sending the requests. Ignore the 
line breaks in the paths.

Access restrictions are optional. If set at 0 the code snippet will be unrestricted and 
viewable by everyone. Otherwise you need the id to access it. You can restrict the number
of views or the number of seconds after creation.


#### Web frontend

Use a web browser to visit these endpoints.

| Action | Path | Description
| --- | --- | --- |
| Add a code snippet | /code/new | A web form where you can post your code and optionally set access restrictions. After clicking "submit" a popup will tell you the code id.  |
| Get a code snippet | /code/**id** | Web page with the requested code snippet |
| Get 10 latest codes | /code/latest | Web page with a list of the 10 latest unrestricted code snippets |

The H2 console is available via at `http://localhost:8080/h2`. There you can view
and modify the database directly. To login set JDBC URL to `jdbc:h2:file:./database` and 
User Name to `user`. No password.


#### JSON API


| Action | Method | Path | Message Body | Response |
| --- | --- | --- | --- | --- |
| Add a code snippet | POST | /api/code/new | JSON with the code, see below | JSON with the id of this snippet |
| View a code | GET | /api/code/**id** |  | JSON with the code, dates, and restrictions, see below |
| Get 10 latest codes | GET | /api/code/latest |  | JSON array with unrestricted code snippets |


This is an example message body for adding a code snippet to the database via the API.
Views and time (in seconds) are optional and default to 0. This code snippet will be 
available 5 times within one hour after creation.
```json
{
    "code": "System.out.println(\"Hello, World!\");",
    "views": 5,
    "time": 3600
}
```
This is the message body when viewing the code snippet via the GET method. It has been 
viewed 3 times already and will become unavailable in 3402 seconds.
```json
{
  "code": "System.out.println(\"Hello, World!\");",
  "date": "2021-07-19 16:54:12",
  "views": 2,
  "time": 3402
}
```