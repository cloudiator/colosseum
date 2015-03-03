# CloudApi Actions
***

## GET /api/cloudApi

**Request Parameters** None

**Request** This operation does not require a request body.

**Response** A list of all cloudApi entities stored in the database.

```
[  
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/cloudApi/1",
            "rel":"self"
         }
      ],
      "api":2,
      "cloud":1,
      "endpoint":"https://neutron-host:9696"
   },
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/cloudApi/2",
            "rel":"self"
         }
      ],
      "api":1,
      "cloud":1,
      "endpoint":"https://neutron-host:8774"
   }
]
```
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

## GET /api/cloudApi/{cloudApi_id}

**Request Parameters**

Parameter      | Description
-------------  | -------------
cloudApi_id    | The id of the cloudApi.

**Request** This operation does not require a request body.

**Response** Shows the selected cloudApi entity.
```
{  
   "links":[  
      {  
         "href":"http://example.com:9000/cloudApi/1",
         "rel":"self"
      }
   ],
   "api":2,
   "cloud":1,
   "endpoint":"https://neutron-host:9696"
}
```
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

## PUT /api/cloudApi

**Request Parameters** none

**Request** The cloudApi attributes api, cloud and endpoint.
```
{  
   "api":1,
   "cloud":1,
   "endpoint":"https://neutron-host:8774"
}
```
**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

## POST /api/cloudApi/{cloudApi_id}

**Request Parameters** 

Parameter      | Description
-------------  | -------------
cloudApi_id    | The id of the cloudApi to update.

**Request** The cloudApi attributes api, cloud and endpoint.
```
{  
   "api":1,
   "cloud":1,
   "endpoint":"https://neutron-host:8774"
}
```
**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

## DELETE /api/cloudApi/{cloudApi_id}

**Request Parameters** 

Parameter     | Description
------------- | -------------
cloudApi_id   | The id of the cloudApi to delete.

**Request** None.

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)