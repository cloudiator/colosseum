# CommunicationChannel Actions
***

## Description
A communication channel entity represents an instance of a communication entity. It depicts a concrete communication between to application instances running on a virtual machines.
***

## GET /api/communicationChannel

### Description
Returns a list of all communication channel entities.

### Request Parameters
None

### Response
A list of all communication channel entities stored in the database.

### Response Example
```
[
   {
      "links":[
         {
            "href":"http://example.com:9000/api/communicationChannel/1",
            "rel":"self"
         }
      ],
      "provider":1,
      "consumer":2,
      "communication":1
   },
   {
      "links":[
         {
            "href":"http://example.com:9000/api/communicationChannel/2",
            "rel":"self"
         }
      ],
      "provider":3,
      "consumer":4,
      "communication":2
   }
]
```
### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)
***
## GET /api/communicationChannel/{communicationChannel_id}

### Description
Returns the communication channel entity identified by the given {communicationChannel_id}.

### Request Parameters

Parameter               | Description
----------------------- | -----------------------------------
communicationChannel_id | The id of the communication channel.

### Response
Shows the selected comunication channel entity.

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
   "communication":1
}
```

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
***
## POST /api/communicationChannel

### Description
Creates a new communication channel entity. The created entity will be returned.

### Request Parameters

Parameter     | Description
------------- | -------------
provider      | The provider (instance) of the communication.
consumer      | The consumer (instance) of the communication.
communication | The communication this channel relates to.

### Request Example
```
{  
   "provider":1,
   "consumer":1,
   "communication":1
}
```
### Response
The created entity. See GET /api/communicationChannel/{communicationChannel_id}

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

***

## PUT /api/communicationChannel/{communicationChannel_id}

### Description
Updates the communication channel entity identified by the given id.

### Request Parameters

Parameter               | Description
----------------------- | ----------------------------------------------------------
communicationChannel_id | The id of the communication.
provider                | The provider (application component) of the communication.
consumer                | The consumer (application component) of the communication.
communication           | The communication this channel relates to.

### Request Example
```
PUT /api/communicationChannel/1
```
```
{  
    "provider":1,
    "consumer":1,
    "communication":1
}
```
### Response
The updated entity. See GET /api/communicationChannel/{communicationChannel_id}

###Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***

## DELETE /api/communicationChannel/{communicationChannel_id}

### Description
Deletes the communication channel entity identified by the given id.

### Request Parameters 

Parameter               | Description
----------------------- | ----------------------------------------------------------
communicationChannel_id | The id of the communication channel.

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)