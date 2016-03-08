# ApplicationComponent Actions
***

## Description

An ApplicationComponent represents an entity which is associated with an application 
and a component.
***

## GET /api/ac

### Description
Returns a list of all application components.

### Request Parameters
None

### Response
A list of all application component entities stored in the database.

### Response Example
```
[
   {
      "links":[
         {
            "href":"http://example.com:9000/api/ac/1",
            "rel":"self"
         }
      ],
      "application":1,
      "component":1,
      "virtualMachineTemplate":1,
      "containerType":"dockering"
   },
   {
      "links":[
         {
            "href":"http://example.com:9000/api/ac/2",
            "rel":"self"
         }
      ],
      "application":2,
      "component":2,
      "virtualMachineTemplate":2,
      "containerType":"plain"
   }
]
```
### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)
***
## GET /api/ac/{applicationComponent_id}

### Description
Returns the ApplicationComponent entity identified by the given {applicationComponent_id}.

### Request Parameters

Parameter                  | Description
-------------              | -------------
applicationComponent_id    | The id of the applicationComponent.


### Response
Shows the selected ApplicationComponent entity.

### Response Example
```
{
   "links":[
      {
         "href":"http://example.com:9000/api/ac/1",
         "rel":"self"
      }
   ],
   "application":1,
   "component":1,
   "virtualMachineTemplate":1,
   "containerType":"dockering"
}
```

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
***
## POST /api/ac

### Description
Creates a new ApplicationComponent entity. The new entity will be returned.

### Request Parameters

Parameter                | Description
-------------            | -------------
application              | The associated application.
component                | The associated component.
virtualMachineTemplate   | The associated virtual machine template.
containerType            | An optional container type used for deploying the application component.

### Request Example
```
{  
   "application":1,
   "component":1,
   "virtualMachineTemplate":1,
   "containerType":"dockering"
}
```
### Response
The created entity. See GET /api/applicationComponent/{applicationComponent_id}

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

***

## PUT /api/ac/{applicationComponent_id}

### Description
Updates the ApplicationComponent entity identified by the given id.

### Request Parameters

Parameter                | Description
-------------            | -------------
applicationComponent_id  | The id of the applicationComponent to update.
application              | The associated application.
component                | The associated component.
virtualMachineTemplate   | The associated virtual machine template.
containerType            | An optional container type used for deploying the application component.


### Request Example
```
PUT /api/ac/1
```
```
{  
   "application":1,
   "component":1,
   "virtualMachineTemplate":1,
   "containerType":"dockering"
}
```
### Response
The updated entity. See GET /api/ac/{applicationComponent_id}

###Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***

## DELETE /api/ac/{applicationComponent_id}

### Description
Deletes the ApplicationComponent entity identified by the given {applicationComponent_id}.

### Request Parameters 

Parameter               | Description
-------------           | -------------
applicationComponent_id | The id of the applicationComponent to delete.

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
