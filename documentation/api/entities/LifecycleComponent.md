# LifecycleComponent Actions
***

## GET /api/lifecycleComponent

**Request Parameters** None

**Request** This operation does not require a request body.

**Response** A list of all lifecycleComponent entities stored in the database.

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
      "name":"Loadbalancer",
      "download":"wget www.loadbalancer.com",
      "install":"install.sh",
      "start":"start.sh",
      "stop":"stop.sh"
   }
]
```

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

## GET /api/lifecycleComponent/{lifecycleComponent_id}

**Request Parameters**

Parameter             | Description
-------------         | -------------
lifecycleComponent_id | The id of the lifecycleComponent.

**Request** This operation does not require a request body.

**Response** Shows the selected lifecycleComponent entity.

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

## PUT /api/lifecycleComponent

**Request Parameters** none

**Request** The lifecycleComponent attributes name, donwload, install, start and stop.

```
{  
   "name":"MySQLDatabase",
   "download":"wget www.mysql.de",
   "install":"install.sh",
   "start":"start.sh",
   "stop":"stop.sh"
}
```

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

## POST /api/lifecycleComponent/{lifecycleComponent_id}

**Request Parameters** 

Parameter                | Description
-------------            | -------------
lifecycleComponent_id    | The id of the lifecycleComponent to update.

**Request** The lifecycleComponent attributes name, donwload, install, start and stop.

```
{  
   "name":"MySQLDatabase",
   "download":"wget www.mysql.de",
   "install":"install.sh",
   "start":"start.sh",
   "stop":"stop.sh"
}
```

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

## DELETE /api/lifecycleComponent/{lifecycleComponent_id}

**Request Parameters** 

Parameter               | Description
-------------           | -------------
lifecycleComponent_id   | The id of the lifecycleComponent to delete.

**Request** None.

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)