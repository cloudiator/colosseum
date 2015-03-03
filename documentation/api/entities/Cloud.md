# Cloud Actions
***

## GET /api/cloud

**Request Parameters** None

**Request** This operation does not require a request body.

**Response** A list of all cloud entities stored in the database.
```
[
   {
      "links":[
         {
            "href":"http://example.com:9000/cloud/1",
            "rel":"self"
         }
      ],
      "name":"amazon"
   },
   {
      "links":[
         {
            "href":"http://example.com:9000/cloud/2",
            "rel":"self"
         }
      ],
      "name":"flexiant"
   }
]
```
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

## GET /api/cloud/{cloud_id}

**Request Parameters**

Parameter     | Description
------------- | -------------
cloud_id      | The id of the cloud.

**Request** This operation does not require a request body.

**Response** Shows the selected cloud entity.
```
{  
   "links":[  
      {  
         "href":"http://example.com:9000/cloud/1",
         "rel":"self"
      }
   ],
   "name":"amazon"
}
```
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

## PUT /api/cloud

**Request Parameters** none

**Request** The name of the cloud.
```
{  
   "name":"Flexiant"
}
```
**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

## POST /api/cloud/{cloud_id}

**Request Parameters** 

Parameter     | Description
------------- | -------------
cloud_id      | The id of the cloud to update.

**Request** The name of the cloud.
```
{  
   "name":"Flexiant"
}
```
**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

## DELETE /api/cloud/{cloud_id}

**Request Parameters** 

Parameter     | Description
------------- | -------------
cloud_id      | The id of the cloud to delete.

**Request** None.

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)