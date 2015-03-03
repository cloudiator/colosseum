# Instance Actions
***

## GET /api/instance

**Request Parameters** None

**Request** This operation does not require a request body.

**Response** A list of all instance entities stored in the database.

```
[  
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/instance/1",
            "rel":"self"
         }
      ],
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
      "applicationComponent":2,
      "virtualMachine":1
   }
]
```

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

## GET /api/instance/{instance_id}

**Request Parameters**

Parameter      | Description
-------------  | -------------
instance_id    | The id of the instance.

**Request** This operation does not require a request body.

**Response** Shows the selected instance entity.

```
{  
   "links":[  
      {  
         "href":"http://example.com:9000/instance/1",
         "rel":"self"
      }
   ],
   "applicationComponent":1,
   "virtualMachine":1
}
```

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

## PUT /api/instance

**Request Parameters** none

**Request** The instance attributes applicationComponent and virtualMachine.

```
{  
   "applicationComponent":1,
   "virtualMachine":1
}    
```

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

## POST /api/instance/{instance_id}

**Request Parameters** 

Parameter      | Description
-------------  | -------------
instance_id    | The id of the instance to update.

**Request** The instance attributes applicationComponent and virtualMachine.

```
{  
   "applicationComponent":1,
   "virtualMachine":1
}
```

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

## DELETE /api/instance/{instance_id}

**Request Parameters** 

Parameter     | Description
------------- | -------------
instance_id   | The id of the instance to delete.

**Request** None.

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)