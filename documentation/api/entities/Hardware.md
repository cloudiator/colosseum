# Hardware Actions
***

##Description

The hardware entity represents a specific hardware flavor which is associated to a cloud and a hardware.
***
## GET /api/hardware

###Description

Returns a list of hardware entities supported by the system.

###Request Parameters
None

###Response
A list of all hardware entities stored in the database.

###Response Example
```
[  
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/hardware/1",
            "rel":"self"
         }
      ],
      "cloud":1,
      "hardwareOffer":1,
      "remoteId":"939c4993-8562-42af-a80c-d8829863d433",
      "location":1,
      "cloudCredentials": [
        1
      ]
   },
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/hardware/2",
            "rel":"self"
         }
      ],
      "cloud":1,
      "hardwareOffer":2,
      "remoteId":"939c4993-8562-42af-a80c-d8829863d433",
      "location":1,
      "cloudCredentials": [
        1
      ]
   }
]
```
###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

***

## GET /api/hardware/{hardware_id}

###Description

Returns the hardware entities identified by the given {hardware_id}.

###Request Parameters

Parameter           | Description
-------------       | -------------
hardware_id         | The id of the hardware.

###Response

The hardware entity identified by the given id.

###Response

```
{  
   "links":[  
      {  
         "href":"http://example.com:9000/hardware/1",
         "rel":"self"
      }
   ],
   "cloud":1,
   "hardwareOffer":1,
   "remoteId":"939c4993-8562-42af-a80c-d8829863d433",
   "location":1,
   "cloudCredentials": [
     1
   ]
}
```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

***

## POST /api/hardware

###Description

Creates a new hardware entity. The new entity will be returned.

###Request Parameters

Parameter           | Description
-------------       | -------------
cloud               | The cloud associated with the hardware.
hardwareOffer       | The hardwareOffer associated with the hardware.
remoteId           | The UUID of the hardware.
location           | The location where this hardware is available.
cloudCredentials    | The cloud credentials used for retrieving this image.

###Request Example 

```
{  
   "cloud":1,
   "hardwareOffer":1,
   "remoteId":"939c4993-8562-42af-a80c-d8829863d433",
   "location":1,
   "cloudCredentials": [
     1
   ]
}
```

###Response
The created entity. See GET /api/hardware/{hardware_id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

***

## PUT /api/hardware/{hardware_id}

###Description

Updates the Hardware entity identified by the given id.

###Request Parameters

Parameter           | Description
-------------       | -------------
hardware_id         | The id of the hardware to update.
cloud               | The cloud associated with the hardware.
hardwareOffer       | The hardware offer associated with the hardware.
remoteId           | The UUID of the hardware.
location           | The location where this hardware is available.
cloudCredentials    | The cloud credentials used for retrieving this image.

###Request Example

PUT /api/hardware/1

```
{  
   "cloud":1,
   "hardwareOffer":1,
   "remoteId":"939c4993-8562-42af-a80c-d8829863d433",
   "location":1,
   "cloudCredentials": [
     1
   ]
}
```

###Response

The updated entity. See GET /api/hardware/{hardware_id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***

## DELETE /api/hardware/{hardware_id}

###Description

Deletes the hardware entity identified by the given {hardware_id}.

**Request Parameters** 

Parameter          | Description
-------------      | -------------
hardware_id        | The id of the hardware to delete.


###Response
No data.

###Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
