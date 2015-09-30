# Cloud Actions
***

##Description
The cloud property entity represents a property for a specific cloud. This could be e.g.
the public ip pool or the network name in Openstack.

## GET /api/cloudProperty

###Description
Returns a list of Cloud types supported by the system.

###Request Parameters
None

###Response
A list of all cloud properties stored in the system.

###Response Example
```
[
   {
      "links":[
         {
            "href":"http://example.com:9000/cloudProperty/1",
            "rel":"self"
         }
      ],
      "key":"openstack.floating.ip.pool",
      "value":"public",
      "cloud":1
   },
   {
      "links":[
         {
            "href":"http://example.com:9000/cloudProperty/2",
            "rel":"self"
         }
      ],
      "key":"openstack.network.name",
      "value":"external",
      "cloud":1
   }
]
```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

***

## GET /api/cloudProperty/{cloudProperty_id}

###Description

Returns the cloud property entity identified by the given {cloudProperty_id}.

###Request Parameters

Parameter     | Description
------------- | -------------
cloudProperty_id      | The id of the cloud property.

###Response 
The cloud property entity identified by the given id.

###Response Example
```
{
      "links":[
         {
            "href":"http://example.com:9000/cloudProperty/1",
            "rel":"self"
         }
      ],
      "key":"openstack.floating.ip.pool",
      "value":"public",
      "cloud":1
}
```

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

***

## POST /api/cloudProperty

###Description

Creates a new cloud property entity. The new entity will be returned.

###Request Parameters
Parameter     | Description
------------- | -------------
key           | The key of the property.
value         | The value of the property.
cloud         | Relation to the cloud.

###Request Example
```
{  
    "key":"openstack.floating.ip.pool",
    "value":"public",
    "cloud":1
}
```

##Response

The created entity. See GET /api/cloudProperty/{cloudProperty_id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

***

## PUT /api/cloudProperty/{cloudProperty_id}

###Description

Updates the cloud property entity identified by the given id.

**Request Parameters** 

Parameter     | Description
------------- | -------------
cloudProperty_id      | The id of the cloud property.
key           | The key of the property.
value         | The value of the property.
cloud         | Relation to the cloud.

###Request Example
```
PUT /api/cloudProperty/1
```
```
{  
    "key":"openstack.floating.ip.pool",
    "value":"public",
    "cloud":1
}
```

###Response

The updated entity. See GET /api/cloudProperty/{cloudProperty_id}

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***

## DELETE /api/cloud/{cloudProperty_id}

###Description

Deletes the cloud property entity identified by the given {cloudProperty_id}.

###Request Parameters

Parameter     | Description
------------- | -------------
cloudProperty_id      | The id of the cloud property.

###Response

No data.

###Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
