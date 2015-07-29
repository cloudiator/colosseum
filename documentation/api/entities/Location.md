# Location Actions
***

## Description

The location entity represents a specific location which is associated with a cloud and a location.

***
## GET /api/location

### Description

Returns a list of locations entities supported by the system.

### Request Parameters
None

### Response

A list of all location entities stored in the database.

### Response Example


```
[  
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/location/1",
            "rel":"self"
         }
      ],
      "cloud":1,
      "remoteId":"regionOne"
      "parent":null,
      "locationScope": "REGION",
      "isAssignable":"false",
      "geoLocation":1,
      "cloudCredentials": [
        1
      ]
   },
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/location/2",
            "rel":"self"
         }
      ],
      "cloud":1,
      "remoteId":"regionOne"
      "parent":1,
      "locationScope": "ZONE",
      "isAssignable":"true",
      "geoLocation":1,  
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

## GET /api/location/{location_id}

###Description

Returns the location entity identified by the given {location_id}.

###Request Parameters

Parameter        | Description
-------------    | -------------
location_id      | The id of the location.

###Response

The location entity identified by the given id.

###Response Example

```
{  
   "links":[  
      {  
         "href":"http://example.com:9000/location/1",
         "rel":"self"
      }
   ],
   "cloud":1,
   "remoteId":"regionOne"
   "parent":1,
   "locationScope": "ZONE",
   "isAssignable":"true",
   "geoLocation":1,
   "cloudCredentials": [
     1
   ]
}
```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

***

## POST /api/location

###Description

Creates a new location entity. The new entity will be returned.

###Request Parameters

Parameter        | Description
-------------    | -------------
cloud            | The cloud associated with the location.
remoteId        | The UUID of the location.
parent           | The parent location (hierachical)
locationScope    | The scope of the location (REGION,ZONE,HOST)
isAssignable     | If the location can be assigned to a virtual machine.
geoLocation      | The id of the geographical location assigned to this offer.
cloudCredentials | The cloud credentials used to retrieve this location.

###Request Example

```
{  
   "cloud":1,
   "remoteId":"regionOne"
   "parent":1,
   "locationScope": "ZONE",
   "isAssignable":"true",
   "geoLocation":1,
   "cloudCredentials": [
     1
   ]
}
```

###Response

The created entity. See GET /api/location/{location_id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

***

## PUT /api/location/{location_id}

###Description

Updates the location entity identified by the given id.

###Request Parameters

Parameter           | Description
-------------       | -------------
location_id         | The id of the location to update.
cloud               | The cloud associated with the location.
remoteId           | The UUID of the location.
parent              | The parent location (hierachical)
locationScope       | The scope of the location (REGION,ZONE,HOST)
isAssignable        | If the location can be assigned to a virtual machine.
geoLocation         | The id of the geographical location assigned to this offer.
cloudCredentials    | The cloud credentials used to retrieve this location.

###Request Example 
```
PUT /location/1
```
```
{  
   "cloud":1,
   "remoteId":"regionOne"
   "parent":1,
   "locationScope": "ZONE",
   "isAssignable":"true",
   "geoLocation":1,
   "cloudCredentials": [
     1
   ]
}
```

###Response

The updated entity. See GET /api/location/{location_id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***

## DELETE /api/location/{location_id}

###Description

Deletes the location entity identified by the given {location_id}.

###Request Parameters

Parameter          | Description
-------------      | -------------
location_id   | The id of the location to delete.


###Response
No data.

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
