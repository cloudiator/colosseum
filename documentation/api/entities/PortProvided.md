# Provided Port Actions
***

## Description
A provided Port represents a communication port provided by an application component.
***

## GET /api/portProv

### Description
Returns a list of all provided port entities.

### Request Parameters
None

### Response
A list of all provided port entities stored in the database.

### Response Example
```
[
   {
      "links":[
         {
            "href":"http://example.com:9000/api/portProv/1",
            "rel":"self"
         }
      ],
      "name":"Play9000Port",
      "applicationComponent":1,
      "port":9000
   },
   {
      "links":[
         {
            "href":"http://example.com:9000/api/portProv/2",
            "rel":"self"
         }
      ],
      "name":"Tomcat8080Port",
      "applicationComponent":2,
      "port":8080
   }
]
```
### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)
***
## GET /api/portProv/{portProv_id}

### Description
Returns the provided port entity identified by the given {portProv_id}.

### Request Parameters

Parameter        | Description
---------------- | -----------------------------------
portProv_id | The id of the provided port.

### Response
Shows the selected provided port entity.

### Response Example
```
{
   "links":[
      {
         "href":"http://example.com:9000/api/portProv/1",
         "rel":"self"
      }
   ],
   "name":"Tomcat8080Port",
   "applicationComponent":2,
   "port":8080
}
```

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
***
## POST /api/portProv

### Description
Creates a new provided port entity. The created entity will be returned.

### Request Parameters

Parameter | Description
--------- | -------------
name                  | A unique name for the port.
applicationComponent  | The application component providing the port.
port                  | The port number.

### Request Example
```
{  
   "name":"Tomcat8080Port",
   "applicationComponent":2,
   "port":8080
}
```
### Response
The created entity. See GET /api/portProv/{portProv_id}

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

***

## PUT /api/portProv/{portProv_id}

### Description
Updates the provided port entity identified by the given id.

### Request Parameters

Parameter        | Description
---------------- | ----------------------------------------------------------
portProv_id | The id of the provided port.
name                  | A unique name for the port.
applicationComponent  | The application component providing the port.
port                  | The port number.

### Request Example
```
PUT /api/portProv/1
```
```
{  
    "name":"Tomcat8080Port",
    "applicationComponent":2,
    "port":8080
}
```
### Response
The updated entity. See GET /api/portProv/{portProv_id}

###Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***

## DELETE /api/portProv/{portProv_id}

### Description
Deletes the provided port entity identified by the given id.

### Request Parameters 

Parameter        | Description
---------------- | ----------------------------------------------------------
portProv_id | The id of the provided port.


### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
