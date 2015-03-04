# CloudHardware Actions
***

##Description

The CloudHardware entity represents a specific hardware flavor which is associated to a cloud and a hardware.
***
## GET /api/cloudHardware

###Description

Returns a list of CloudHardware entities supported by the system.

###Request Parameters
None

###Response
A list of all CloudHardware entities stored in the database.

###Response Example
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
###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

***

## GET /api/cloudHardware/{cloudHardware_id}

###Description

Returns the CloudHardware entities identified by the given {cloudHardware_id}.

###Request Parameters

Parameter           | Description
-------------       | -------------
cloudHardware_id    | The id of the cloudHardware.

###Response

The CloudHardware entity identified by the given id.

###Response

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

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

***

## POST /api/cloudHardware

###Description

Creates a new CloudHardware entity. The new entity will be returned.

###Request Parameters

Parameter           | Description
-------------       | -------------
cloud               | The cloud associated with the CloudHardware.
hardware            | The hardware associated with the CloudHardware.
cloudUuid           | The UUID of the cloudHardware.

###Request Example 

```
{  
   "cloud":1,
   "hardware":1,
   "cloudUuid":"939c4993-8562-42af-a80c-d8829863d433"
}
```

###Response
The created entity. See GET /api/cloudHardware/{cloudHardware_id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

***

## PUT /api/cloudHardware/{cloudHardware_id}

###Description

Updates the CloudHardware entity identified by the given id.

###Request Parameters

Parameter           | Description
-------------       | -------------
cloudHardware_id    | The id of the cloudHardware to update.
cloud               | The cloud associated with the CloudHardware.
hardware            | The hardware associated with the CloudHardware.
cloudUuid           | The UUID of the cloudHardware.

###Request Example

PUT /api/cloudHardware/1

```
{  
   "cloud":1,
   "hardware":1,
   "cloudUuid":"939c4993-8562-42af-a80c-d8829863d433"
}
```

###Response

The updated entity. See GET /api/cloudHardware/{cloudHardware_id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***

## DELETE /api/cloudHardware/{cloudHardware_id}

###Description

Deletes the CloudHardware entity identified by the given {cloudHardware_id}.

**Request Parameters** 

Parameter          | Description
-------------      | -------------
cloudHardware_id   | The id of the cloudHardware to delete.


###Response
No data.

###Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)