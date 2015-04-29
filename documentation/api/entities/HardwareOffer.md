# HardwareOffer Actions
***

## Description

A hardware entity describes properties of hardware offerings of a provider.

## GET /api/hardwareOffer

### Description

Returns all hardware offers currently stored in the system.

### Request Parameters
None

### Response
A list of all hardware offers stored in the database.

### Response Example
```
[  
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/hardwareOffer/1",
            "rel":"self"
         }
      ],
      "numberOfCores":4,
      "mbOfRam":4096,
      "localDiskSpace":20000
   },
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/hardwareOffer/2",
            "rel":"self"
         }
      ],
      "numberOfCores":8,
      "mbOfRam":8192,
      "localDiskSpace":40000
   }
]
```
### Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

## GET /api/hardwareOffer/{hardwareOffer_id}

### Description

Returns the hardware offer identified by the given id.

### Request Parameters

Parameter     | Description
------------- | -------------
hardwareOffer_id   | The id of the hardware.

### Response
Shows the selected hardware offer.

### Response Example
```
{  
   "links":[  
      {  
         "href":"http://example.com:9000/hardwareOffer/1",
         "rel":"self"
      }
   ],
   "numberOfCores":4,
   "mbOfRam":4096,
   "localDiskSpace":20000
}
```

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

## POST /api/hardwareOffer

### Description

Creates a new hardware offer based on the given request data and returns it.

### Request Parameters**

Parameter      | Description
-------------- | -------------
numberOfCores    | The number of CPU cores.
mbOfRam        | The amount of RAM in MB.
localDiskSpace | The amount of locally available disk space.

### Request Example
```
{  
   "numberOfCores":4,
   "mbOfRam":4096,
   "localDiskSpace":20000
} 
```

### Response
The create entity. See /api/hardwareOffer/{hardwareOffer_id}

### Response Error Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

## PUT /api/hardwareOffer/{hardwareOffer_id}

### Description

Updates the hardware offer identified by the given id with the data from the request and returns the updated offer.

### Request Parameters** 

Parameter         | Description
----------------- | ------------------------------------------
hardwareOffer_id  | The id of the hardware offer to update.
numberOfCores       | The number of CPU cores.
mbOfRam           | The amount of RAM in MB.
localDiskSpace    | The amount of locally available disk space.

### Request Example
```
PUT /api/hardwareOffer/1
```
```
{  
   "numberOfCores":4,
   "mbOfRam":4096,
   "localDiskSpace":20000
}
```

### Response
The updates entity. See /api/hardareOffer/{hardwareOffer_id}.

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

## DELETE /api/hardwareOffer/{hardwareOffer_id}

### Description

Deletes the hardware offer identified by the given id.

### Request Parameters

Parameter     | Description
------------- | -------------
hardwareOffer_id   | The id of the hardware offer to delete.

### Response
No data.

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
