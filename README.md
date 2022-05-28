# Blog built with plain Java

## About The Project

This is simple, standalone program with 5 accessible endpoints.\
The goal was to build CRUD application without using any prefabricated frameworks.

### Built With
* Java
* H2 database
* Maven

## Endpoints

### GET /blog
Returns list of entries in `blog` table. (e.g. GET http://domain/blog)

### POST /blog
There are **three** possibilities under this path when requesting with POST.\
Selection of demanded operiation based on `action` query parameter.

###### QUERY PARAMETERS
- `String action` - specifies demanded action. Available values are **login**, **new** or **new_user**.
  - action=**login:** allows user to log into system\
   (e.g. POST http://domain/blog?action=login&user=admin&password=admin)
  - action=**new:** allows user to add new entry to blog\
  (e.g. POST http://domain/blog?action=new&text=testtext)
  - action=**new_user:** allows user to create new user\
  (e.g. POST http://domain/blog?action=new_user&username=test&password=test&permission=superuser&readonly=yes)
- `String user` - specifies username to test when action **login** is chosen.
- `String password` - specifies password to test when action **login** is chosen OR password of created user when a **new_user** is chosen.
- `String username` - specifies username of created user when a **new_user** is chosen.
- `String permission` - specifies permission attribute of created user when action **new_user** is chosen.
- `String readonly` - specifies readonly attribute of created user when action **new_user** is chosen.
- `String text` - specifies text of new blog entry to save when action **new_user** is chosen.

### DELETE /blog
Deletes element with given ID from `blog` table. (e.g. DELETE http://domain/blog?action=delete&id=1)

###### QUERY PARAMETERS
- `String action` - specifies demanded action. Must be set to **delete**.
- `int id` - specifies ID of element to be deleted.


## Contact

Arkadiusz Jędrzejewski\
arkadiusz.jedrzej@gmail.com

### Links:
* [Project repo](https://github.com/Arkodiusz/blog)
* [My portfolio](https://arkodiusz.github.io)
