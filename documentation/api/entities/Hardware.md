# Hardware Actions
***

## GET /api/hardware

**Request Parameters** None

**Request** This operation does not require a request body.

**Response** A list of all hardware entities stored in the database.

```
[  
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/hardware/1",
            "rel":"self"
         }
      ],
      "numberOfCpu":4,
      "mbOfRam":4096,
      "localDiskSpace":20000
   },
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/hardware/2",
            "rel":"self"
         }
      ],
      "numberOfCpu":8,
      "mbOfRam":8192,
      "localDiskSpace":40000
   }
]
```

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

## GET /api/hardware/{hardware_id}

**Request Parameters**

Parameter     | Description
------------- | -------------
hardware_id   | The id of the hardware.

**Request** This operation does not require a request body.

**Response** Shows the selected hardware entity.

```
{  
   "links":[  
      {  
         "href":"http://example.com:9000/hardware/1",
         "rel":"self"
      }
   ],
   "numberOfCpu":4,
   "mbOfRam":4096,
   "localDiskSpace":20000
}
```

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

## PUT /api/hardware

**Request Parameters** none

**Request** The hardware attributes localDiskSpace, mbOfRam and numberOfCpu.

```
{  
   "numberOfCpu":4,
   "mbOfRam":4096,
   "localDiskSpace":20000
} 
```

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

## POST /api/hardware/{hardware_id}

**Request Parameters** 

Parameter     | Description
------------- | -------------
hardware_id   | The id of the hardware to update.

**Request** The hardware attributes localDiskSpace, mbOfRam and numberOfCpu.

```
{  
   "numberOfCpu":4,
   "mbOfRam":4096,
   "localDiskSpace":20000
}
```

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

## DELETE /api/hardware/{hardware_id}

**Request Parameters** 

Parameter     | Description
------------- | -------------
hardware_id   | The id of the hardware to delete.

**Request** None.

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)