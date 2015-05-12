# Instance Actions
***
## Description

Represents an instance of a application component on a virtual machine. Adding a instance connects the application component to the virtual machine, meaning that the component represented by the application component gets install on the virtual machine.
***
## GET /api/instance

### Description
Get a list of all instances currently registered.

### Request Parameters
None

### Response
A list of all instance entities stored in the database.

### Response Example
```
[  
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/instance/1",
            "rel":"self"
         }
      ],
      "applicationInstance":1,
      "applicationComponent":1,
      "virtualMachine":1
   },
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/instance/2",
            "rel":"self"
         }
      ],
      "applicationInstance":1,
      "applicationComponent":2,
      "virtualMachine":1
   }
]
```
### Response Error Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)
***

## GET /api/instance/{instance_id}

### Description
Returns the details of the instance entity.

### Request Parameters

Parameter      | Description
-------------  | -------------
instance_id    | The id of the instance.

### Response
Shows the selected instance entity.

### Response Example
```
{  
   "links":[  
      {  
         "href":"http://example.com:9000/instance/1",
         "rel":"self"
      }
   ],
   "applicationInstance":1,
   "applicationComponent":1,
   "virtualMachine":1
}
```

### Response Error Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
***
## POST /api/instance

### Description

Creates a new instance entity and returns it.

### Request Parameters**

Parameter            | Description
-------------------- | -------------------------------------
applicationInstance  | The id of the application instance.
applicationComponent | The id of the application component.
virtualMachine       | The id of the virtual machine.


### Request Example
```
{  
   "applicationInstance":1,
   "applicationComponent":1,
   "virtualMachine":1
}    
```

### Reponse

The created instance entity. See /api/instance/{instance_id}

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

***
## PUT /api/instance/{instance_id}

### Description

Updates the application instance residing under the given id.

### Request Parameters

Parameter            | Description
-------------------- | -------------------------------------
instance_id          | The id of the instance to update.
applicationInstance  | The id of the application instance.
applicationComponent | The id of the application component.
virtualMachine       | The id of the virtual machine.

### Request Example
```
PUT /api/instance/1
```
```
{  
   "applicationInstance":1,
   "applicationComponent":1,
   "virtualMachine":1
}
```

### Response
The updated entity. See /api/instance/{instance_id}.

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***
## DELETE /api/instance/{instance_id}

### Description
Deletes the instance entity identified by the given id.

### Request Parameters

Parameter     | Description
------------- | -------------
instance_id   | The id of the instance to delete.

### Response
No data.

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
