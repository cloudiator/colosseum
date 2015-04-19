# LocationOffer Actions
***

##Description

The location offer entity represents properties of a location offering.

***
## GET /api/locationOffer

###Description

Returns a list of locations offer entities supported by the system.

###Request Parameters
None

###Response

A list of all location offer entities stored in the database.

###Response Example


```
[  
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/locationOffer/1",
            "rel":"self"
         }
      ],
      "parent":null,
      "locationScope": "REGION",
      "isAssignable":"false",
      "geoLocation":1
   },
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/locationOffer/2",
            "rel":"self"
         }
      ],
      "parent":1,
      "locationScope": "ZONE",
      "isAssignable":"true",
      "geoLocation":1
   }
]
```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

***

## GET /api/locationOffer/{locationOffer_id}

###Description

Returns the location offer entity identified by the given {locationOffer_id}.

###Request Parameters

Parameter             | Description
------------------    | -------------
locationOffer_id      | The id of the location offer.

###Response

The location offer entity identified by the given id.

###Response Example

```
{
  "links":[
    {
      "href":"http://example.com:9000/locationOffer/2",
      "rel":"self"
    }
  ],
  "parent":1,
  "locationScope":"ZONE",
  "isAssignable":"true",
  "geoLocation":1
}
```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

***

## POST /api/locationOffer

###Description

Creates a new location offer entity. The new entity will be returned.

###Request Parameters

Parameter        | Description
-------------    | -------------


###Request Example

```
{  
  "parent":1,
  "locationScope":"ZONE",
  "isAssignable":"true",
  "geoLocation":1
}
```

###Response

The created entity. See GET /api/locationOffer/{locationOffer_id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

***

## PUT /api/locationOffer/{locationOffer_id}

###Description

Updates the location offer entity identified by the given id.

###Request Parameters

Parameter        | Description
---------------- | -------------
locationOffer_id | The id of the location to update.
parent           | The parent location (hierachical)
locationScope    | The scope of the location (REGION,ZONE,HOST)
isAssignable     | If the location can be assigned to a virtual machine.
geoLocation      | The id of the geographical location assigned to this offer.

###Request Example 

```
PUT /api/locationOffer/1
```
```
{  
  "parent":1,
  "locationScope":"ZONE",
  "isAssignable":"true",
  "geoLocation":1
}
```


###Response

The updated entity. See GET /api/locationOffer/{locationOffer_id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***

## DELETE /api/locationOffer/{locationOffer_id}

###Description

Deletes the location offer entity identified by the given {locationOffer_id}.

###Request Parameters

Parameter          | Description
-------------      | -------------
locationOffer_id   | The id of the location to delete.


###Response
No data.

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
