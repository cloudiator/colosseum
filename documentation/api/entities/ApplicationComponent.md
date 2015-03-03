# ApplicationComponent Actions
***

## GET /api/applicationComponent

**Request Parameters** None

**Request** This operation does not require a request body.

**Response** A list of all application component entities stored in the database.

```
[
   {
      "links":[
         {
            "href":"http://example.com:9000/api/applicationComponent/1",
            "rel":"self"
         }
      ],
      "application":1,
      "component":1
   },
   {
      "links":[
         {
            "href":"http://example.com:9000/api/applicationComponent/2",
            "rel":"self"
         }
      ],
      "application":2,
      "component":2
   }
]
```

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

## GET /api/applicationComponent/{applicationComponent_id}

**Request Parameters**

Parameter                  | Description
-------------              | -------------
applicationComponent_id    | The id of the applicationComponent.

**Request** This operation does not require a request body.

**Response** Shows the selected applicationComponent entity.

```
{
   "links":[
      {
         "href":"http://example.com:9000/api/applicationComponent/1",
         "rel":"self"
      }
   ],
   "application":1,
   "component":1
}
```

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

## PUT /api/applicationComponent

**Request Parameters** none

**Request** The applicationComponent attributes application and component.

    {"application":1,"component":1}

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

## POST /api/applicationComponent/{applicationComponent_id}

**Request Parameters** 

Parameter                | Description
-------------            | -------------
applicationComponent_id  | The id of the applicationComponent to update.

**Request** The applicationComponent attributes application and component.

    {"application":1,"component":1}

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

## DELETE /api/applicationComponent/{applicationComponent_id}

**Request Parameters** 

Parameter               | Description
-------------           | -------------
applicationComponent_id | The id of the applicationComponent to delete.

**Request** None.

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)