# ApplicationInstance Actions
***

## Description

An application instance represents an instance of application. It groups all instances
of the components of the related applications. Therefore it allows multiple instances of
an application at the same time.
***

## GET /api/applicationComponent

### Description
Returns a list of all application instances.

### Request Parameters
None

### Response
A list of all application instances entities stored in the database.

### Response Example
```
[
   {
      "links":[
         {
            "href":"http://example.com:9000/api/applicationInstance/1",
            "rel":"self"
         }
      ],
      "application":1
   },
   {
      "links":[
         {
            "href":"http://example.com:9000/api/applicationInstance/2",
            "rel":"self"
         }
      ],
      "application":2
   }
]
```
### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)
***
## GET /api/applicationInstance/{applicationInstance_id}

### Description
Returns the application instance entity identified by the given {applicationInstance_id}.

### Request Parameters

Parameter                  | Description
-------------              | -------------
applicationInstance_id     | The id of the application instance.


### Response
Shows the selected application instance entity.

### Response Example
```
{
   "links":[
      {
         "href":"http://example.com:9000/api/applicationInstance/1",
         "rel":"self"
      }
   ],
   "application":1
}
```

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
***
## POST /api/applicationInstance

### Description
Creates a new application instance entity. The new entity will be returned.

### Request Parameters

Parameter                | Description
-------------            | -------------
application              | The associated application.

### Request Example
```
{  
   "application":1
}
```
### Response
The created entity. See GET /api/applicationInstance/{applicationInstance_id}

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

***

## PUT /api/applicationInstance/{applicationInstance_id}

### Description
Updates the application instance entity identified by the given id.

### Request Parameters

Parameter                | Description
-------------            | -------------
applicationInstance _id  | The id of the application instance to update.
application              | The associated application.

### Request Example
```
PUT /api/applicationInstance/1
```
```
{  
   "application":1
}
```
### Response
The updated entity. See GET /api/applicationComponent/{applicationComponent_id}

###Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***

## DELETE /api/applicationInstance/{applicationInstance_id}

### Description
Deletes the application instance entity identified by the given {applicationInstance_id}.

### Request Parameters 

Parameter               | Description
-------------           | -------------
applicationInstance_id  | The id of the applicationInstance to delete.

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
