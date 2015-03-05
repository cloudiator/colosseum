# Hardware Actions
***

## Description

A hardware entity describes abstract hardware properties.

## GET /api/hardware

### Description

Returns all hardware entities currently stored in the system.

### Request Parameters
None

### Response
A list of all hardware entities stored in the database.

### Response Example
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
### Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

## GET /api/hardware/{hardware_id}

### Description

Returns the hardware entity identified by the given id.

### Request Parameters

Parameter     | Description
------------- | -------------
hardware_id   | The id of the hardware.

### Response
Shows the selected hardware entity.

### Response Example
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

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

## POST /api/hardware

### Description

Creates a new hardware entity base on the given request data and returns it.

### Request Parameters**

Parameter      | Description
-------------- | -------------
numberOfCpu    | The number of CPU cores.
mbOfRam        | The amount of RAM in MB.
localDiskSpace | The amount of locally available disk space.

### Request Example
```
{  
   "numberOfCpu":4,
   "mbOfRam":4096,
   "localDiskSpace":20000
} 
```

### Response
The create entity. See /api/hardware/{hardware_id}

### Response Error Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

## PUT /api/hardware/{hardware_id}

### Description

Updates the hardware entity identified by the given id with the data from the request and returns the updated entity.

### Request Parameters** 

Parameter      | Description
-------------- | ------------------------------------------
hardware_id    | The id of the hardware to update.
numberOfCpu    | The number of CPU cores.
mbOfRam        | The amount of RAM in MB.
localDiskSpace | The amount of locally available disk space.

### Request Example
```
PUT /api/hardware/1
```
```
{  
   "numberOfCpu":4,
   "mbOfRam":4096,
   "localDiskSpace":20000
}
```

### Response
The updates entity. See /api/hardare/{hardware_id}.

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

## DELETE /api/hardware/{hardware_id}

### Description

Deletes the hardware entity identified by the given id.

### Request Parameters

Parameter     | Description
------------- | -------------
hardware_id   | The id of the hardware to delete.

### Response
No data.

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)