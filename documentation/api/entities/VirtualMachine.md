# VirtualMachine Actions
***

## GET /api/virtualMachine

**Request Parameters** None

**Request** This operation does not require a request body.

**Response** A list of all virtualMachine entities stored in the database.

```
[  
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/virtualMachine/1",
            "rel":"self"
         }
      ],
      "name":"Scalarm",
      "cloud":1,
      "cloudImage":1,
      "cloudHardware":1,
      "cloudLocation":1
   },
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/virtualMachine/2",
            "rel":"self"
         }
      ],
      "name":"Hyperflow",
      "cloud":1,
      "cloudImage":1,
      "cloudHardware":1,
      "cloudLocation":2
   }
]
```

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

## GET /api/virtualMachine/{virtualMachine_id}

**Request Parameters**

Parameter             | Description
-------------         | -------------
virtualMachine_id     | The id of the virtualMachine.

**Request** This operation does not require a request body.

**Response** Shows the selected virtualMachine entity.

```
{  
   "links":[  
      {  
         "href":"http://example.com:9000/virtualMachine/1",
         "rel":"self"
      }
   ],
   "name":"Scalarm",
   "cloud":1,
   "cloudImage":1,
   "cloudHardware":1,
   "cloudLocation":1
}
```

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

## PUT /api/virtualMachine

**Request Parameters** none

**Request** The virtualMachine attributes name, cloud, cloudImage, cloudHardware and cloudLocation.

```
{  
   "name":"Scalarm",
   "cloud":1,
   "cloudImage":1,
   "cloudHardware":1,
   "cloudLocation":1
}
```

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

## POST /api/virtualMachine/{virtualMachine_id}

**Request Parameters** 

Parameter            | Description
-------------        | -------------
virtualMachine_id    | The id of the virtualMachine to update.

**Request** The virtualMachine attributes name, cloud, cloudImage, cloudHardware and cloudLocation.

```
{  
   "name":"Scalarm",
   "cloud":1,
   "cloudImage":1,
   "cloudHardware":1,
   "cloudLocation":1
}
```

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

## DELETE /api/virtualMachine/{virtualMachine_id}

**Request Parameters** 

Parameter           | Description
-------------       | -------------
virtualMachine_id   | The id of the virtualMachine to delete.

**Request** None.

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)