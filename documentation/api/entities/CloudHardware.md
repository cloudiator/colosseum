# CloudHardware Actions
***

## GET /api/cloudHardware

**Request Parameters** None

**Request** This operation does not require a request body.

**Response** A list of all cloudHardware entities stored in the database.

```
[  
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/cloudHardware/1",
            "rel":"self"
         }
      ],
      "cloud":1,
      "hardware":1,
      "cloudUuid":"939c4993-8562-42af-a80c-d8829863d433"
   },
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/cloudHardware/2",
            "rel":"self"
         }
      ],
      "cloud":1,
      "hardware":2,
      "cloudUuid":"939c4993-8562-42af-a80c-d8829863d433"
   }
]
```

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

## GET /api/cloudHardware/{cloudHardware_id}

**Request Parameters**

Parameter           | Description
-------------       | -------------
cloudHardware_id    | The id of the cloudHardware.

**Request** This operation does not require a request body.

**Response** Shows the selected cloudHardware entity.

```
{  
   "links":[  
      {  
         "href":"http://example.com:9000/cloudHardware/1",
         "rel":"self"
      }
   ],
   "cloud":1,
   "hardware":1,
   "cloudUuid":"939c4993-8562-42af-a80c-d8829863d433"
}
```

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

## PUT /api/cloudHardware

**Request Parameters** none

**Request** The cloudHardware attributes cloud, hardware and uuid.

```
{  
   "cloud":1,
   "hardware":1,
   "cloudUuid":"939c4993-8562-42af-a80c-d8829863d433"
}
```

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

## POST /api/cloudHardware/{cloudHardware_id}

**Request Parameters** 

Parameter           | Description
-------------       | -------------
cloudHardware_id    | The id of the cloudHardware to update.

**Request** The cloudHardware attributes cloud, hardware and uuid.

```
{  
   "cloud":1,
   "hardware":1,
   "cloudUuid":"939c4993-8562-42af-a80c-d8829863d433"
}
```

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

## DELETE /api/cloudHardware/{cloudHardware_id}

**Request Parameters** 

Parameter          | Description
-------------      | -------------
cloudHardware_id   | The id of the cloudHardware to delete.

**Request** None.

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)