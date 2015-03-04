# LifecycleComponent Actions
***
## Description
The lifecycle component entity is a component using lifecycle shell scripts to execute the lifecycle actions.
***
## GET /api/lifecycleComponent

### Description
Returns a list of all registered lifecycle components.

### Request Parameters
None.

### Response
A list of all lifecycleComponent entities stored in the database.

### Response Example
```
[  
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/lifecycleComponent/1",
            "rel":"self"
         }
      ],
      "name":"MySQLDatabase",
      "download":"wget www.mysql.de",
      "install":"install.sh",
      "start":"start.sh",
      "stop":"stop.sh"
   },
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/lifecycleComponent/2",
            "rel":"self"
         }
      ],
      "name":"apacheLB",
      "download":"wget www.loadbalancer.com",
      "install":"install.sh",
      "start":"start.sh",
      "stop":"stop.sh"
   }
]
```
### Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

***
## GET /api/lifecycleComponent/{lifecycleComponent_id}

### Request Parameters

Parameter             | Description
-------------         | -------------
lifecycleComponent_id | The id of the lifecycleComponent.

### Response
Shows the selected lifecycleComponent entity.

### Response Example
```
{  
   "links":[  
      {  
         "href":"http://example.com:9000/lifecycleComponent/1",
         "rel":"self"
      }
   ],
   "name":"MySQLDatabase",
   "download":"wget www.mysql.de",
   "install":"install.sh",
   "start":"start.sh",
   "stop":"stop.sh"
}
```

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
***
## POST /api/lifecycleComponent

### Description

Creates a new lifecycle component and returns it.

### Request Parameters

Parameter | Description
--------- | ------------------------------------
name      | The name for the lifeycle component.
download  | The action for the download lifecycle step.
install   | The action for the install lifecycle step.
start     | The action for the start lifecycle step.
stop      | The action for the stop lifecycle step.

### Request Example

```
{  
   "name":"MySQLDatabase",
   "download":"wget www.mysql.de",
   "install":"install.sh",
   "start":"start.sh",
   "stop":"stop.sh"
}
```

### Response

The create lifecycle component. See GET /api/lifecycleComponent/{lifecycleComponent_id}

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

***
## PUT /api/lifecycleComponent/{lifecycleComponent_id}

### Description
Updates the lifecycle component located at the given id with the data from the request.

### Request Parameters

Parameter             | Description
--------------------- | ---------------------------------------------
lifecycleComponent_id | The id of the lifecycleComponent to update.
name                  | The name for the lifeycle component.
download              | The action for the download lifecycle step.
install               | The action for the install lifecycle step.
start                 | The action for the start lifecycle step.
stop                  | The action for the stop lifecycle step.

### Request Example
```
PUT /api/lifecycleComponent/1
```
```
{  
   "name":"MySQLDatabase",
   "download":"wget www.mysql.de",
   "install":"install.sh",
   "start":"start.sh",
   "stop":"stop.sh"
}
```
### Response

The updated lifecycle component entity, see GET /api/lifecycleComponent/{lifecycleComponent_id}.

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***
## DELETE /api/lifecycleComponent/{lifecycleComponent_id}

### Description

Deletes the lifecycleComponent residing at the given identified.

### Request Parameters

Parameter               | Description
-------------           | -------------
lifecycleComponent_id   | The id of the lifecycleComponent to delete.

### Response

No data.

### Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)