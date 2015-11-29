# Required Port Actions
***

## Description
A required Port represents a communication port required by an application component.
***

## GET /api/portReq

### Description
Returns a list of all required port entities.

### Request Parameters
None

### Response
A list of all required port entities stored in the database.

### Response Example
```
[
   {
      "links":[
         {
            "href":"http://example.com:9000/api/portReq/1",
            "rel":"self"
         }
      ],
      "name":"RequiredDB",
      "updateAction":"update.sh",
      "isMandatory":"true",
      "applicationComponent":1
   },
   {
      "links":[
         {
            "href":"http://example.com:9000/api/portReq/2",
            "rel":"self"
         }
      ],
      "name":"RequiredTomcat",
      "updateAction":"update.sh",
      "isMandatory":"false",
      "applicationComponent":2
   }
]
```
### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)
***
## GET /api/portReq/{portReq_id}

### Description
Returns the required port entity identified by the given {portReq_id}.

### Request Parameters

Parameter        | Description
---------------- | -----------------------------------
portReq_id | The id of the required port.

### Response
Shows the selected required port entity.

### Response Example
```
{
   "links":[
      {
         "href":"http://example.com:9000/api/portReq/1",
         "rel":"self"
      }
   ],
   "name":"RequiredDB",
   "updateAction":"update.sh",
   "isMandatory":"true",
   "applicationComponent":1
}
```

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
***
## POST /api/portReq

### Description
Creates a new required port entity. The created entity will be returned.

### Request Parameters

Parameter | Description
--------- | -------------
name                  | A unique name for the port.
applicationComponent  | The application component requiring the port.
updateAction | An optional update action, that is executed when the port is updated.
isMandatory | States if the required port is mandatory or if the the communication can start without it.

### Request Example
```
{  
   "name":"RequiredDB",
   "updateAction":"update.sh",
   "isMandatory":"true",
   "applicationComponent":1
}
```
### Response
The created entity. See GET /api/portReq/{portReq_id}

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

***

## PUT /api/portReq/{portReq_id}

### Description
Updates the required port entity identified by the given id.

### Request Parameters

Parameter        | Description
---------------- | ----------------------------------------------------------
portReq_id | The id of the required port.
name                  | A unique name for the port.
applicationComponent  | The application component providing the port.
updateAction | An optional update action, that is executed when the port is updated.
isMandatory | States if the required port is mandatory or if the the communication can start without it.

### Request Example
```
PUT /api/portReq/1
```
```
{  
    "name":"RequiredDB",
    "updateAction":"update.sh",
    "isMandatory":"true",
    "applicationComponent":1
}
```
### Response
The updated entity. See GET /api/portReq/{portReq_id}

###Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***

## DELETE /api/portReq/{portReq_id}

### Description
Deletes the required port entity identified by the given id.

### Request Parameters 

Parameter        | Description
---------------- | ----------------------------------------------------------
portReq_id | The id of the required port.

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
