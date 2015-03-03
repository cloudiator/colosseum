# Application Actions
***

## Description
The application entity represents a single application within the system. An application 
***

## GET /api/application

**Request Parameters** None

**Request** This operation does not require a request body.

**Response** A list of all application entities stored in the database.

```
[  
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/api/application/1",
            "rel":"self"
         }
      ],
      "name":"Hyperflow"
   },
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/api/application/2",
            "rel":"self"
         }
      ],
      "name":"Scalarm"
   }
]
```

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

## GET /api/application/{application_id}

**Request Parameters**

Parameter      | Description
-------------  | -------------
application_id | The id of the application.

**Request** This operation does not require a request body.

**Response** Shows the selected application entity.

```
{
   "links":[
      {
         "href":"http://example.com:9000/api/application/1",
         "rel":"self"
      }
   ],
   "name":"Hyperflow"
}
```

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

## PUT /api/application

**Request Parameters** none

**Request** The application attribute name.

    {"name":"Scalarm"}

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

## POST /api/application/{application_id}

**Request Parameters** 

Parameter      | Description
-------------  | -------------
application_id | The id of the application to update.

**Request** The application attribute name.

    {"name":"Scalarm"}

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

## DELETE /api/application/{application_id}

**Request Parameters** 

Parameter      | Description
-------------  | -------------
application_id | The id of the application to delete.

**Request** None.

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)