# Communication Actions
***

## Description
A communication entity represents a template for a communication between two application components. The communication always has a direction (consumer -> provider).
***

## GET /api/communication

### Description
Returns a list of all communication entities.

### Request Parameters
None

### Response
A list of all communication entities stored in the database.

### Response Example
```
[
   {
      "links":[
         {
            "href":"http://example.com:9000/api/communication/1",
            "rel":"self"
         }
      ],
      "provider":1,
      "consumer":2,
      "port":80
   },
   {
      "links":[
         {
            "href":"http://example.com:9000/api/communication/2",
            "rel":"self"
         }
      ],
      "provider":3,
      "consumer":4,
      "port":3306
   }
]
```
### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)
***
## GET /api/communication/{communication}

### Description
Returns the communication entity identified by the given {communication_id}.

### Request Parameters

Parameter        | Description
---------------- | -----------------------------------
communication_id | The id of the communication.

### Response
Shows the selected comunication entity.

### Response Example
```
{
   "links":[
      {
         "href":"http://example.com:9000/api/communication/1",
         "rel":"self"
      }
   ],
   "provider":1,
   "consumer":2,
   "port":80
}
```

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
***
## POST /api/communication

### Description
Creates a new communication entity. The created entity will be returned.

### Request Parameters

Parameter | Description
--------- | -------------
provider  | The provider (application component) of the communication.
consumer  | The consumer (application component) of the communication.
port      | The port of the communication.

### Request Example
```
{  
   "provider":1,
   "consumer":1,
   "port":80
}
```
### Response
The created entity. See GET /api/communication/{communication_id}

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

***

## PUT /api/communication/{communication_id}

### Description
Updates the communication entity identified by the given id.

### Request Parameters

Parameter        | Description
---------------- | ----------------------------------------------------------
communication_id | The id of the communication.
provider         | The provider (application component) of the communication.
consumer         | The consumer (application component) of the communication.
port             | The port of the communication.

### Request Example
```
PUT /api/communication/1
```
```
{  
    "provider":1,
    "consumer":1,
    "port":8080
}
```
### Response
The updated entity. See GET /api/communication/{communication_id}

###Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***

## DELETE /api/communication/{communication_id}

### Description
Deletes the communication entity identified by the given id.

### Request Parameters 

Parameter        | Description
---------------- | ----------------------------------------------------------
communication_id | The id of the communication.

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)