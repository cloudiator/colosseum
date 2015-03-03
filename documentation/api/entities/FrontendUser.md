# FrontendUser Actions
***

## GET /api/frontendUser

**Request Parameters** None

**Request** This operation does not require a request body.

**Response** A list of all frontendUser entities stored in the database.

```
[  
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/frontendUser/1",
            "rel":"self"
         }
      ],
      "firstName":"John",
      "lastName":"Doe",
      "mail":"john.doe@example.com",
      "password":"2ru0wc03c02c2c30c2c32ßc23ßncß32c",
      "repeat":null
   },
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/frontendUser/2",
            "rel":"self"
         }
      ],
      "firstName":"James",
      "lastName":"Smith",
      "mail":"james.smith@example.com",
      "password":"2ru0wc53c32cfc3vc2ff2ßc%/ßncß32c",
      "repeat":null
   }
]
```

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

## GET /api/frontendUser/{frontendUser_id}

**Request Parameters**

Parameter       | Description
-------------   | -------------
frontendUser_id | The id of the frontendUser.

**Request** This operation does not require a request body.

**Response** Shows the selected frontendUser entity.

```
{  
   "links":[  
      {  
         "href":"http://example.com:9000/frontendUser/1",
         "rel":"self"
      }
   ],
   "firstName":"John",
   "lastName":"Doe",
   "mail":"john.doe@example.com",
   "password":"2ru0wc03c02c2c30c2c32ßc23ßncß32c",
   "repeat":null
}
```

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

## PUT /api/frontendUser

**Request Parameters** none

**Request** The frontendUser attributes firstName, lastName, mail, password and repeat.

```    
{  
   "firstName":"John",
   "lastName":"Doe",
   "mail":"john.doe@example.com",
   "password":"topsecret",
   "repeat":"topsecret"
}
```

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

## POST /api/frontendUser/{frontendUser_id}

**Request Parameters** 

Parameter       | Description
-------------   | -------------
frontendUser_id | The id of the frontendUser to update.

**Request** The frontendUser attributes firstName, lastName, mail, password and repeat.

```    
{  
   "firstName":"John",
   "lastName":"Doe",
   "mail":"john.doe@example.com",
   "password":"topsecret",
   "repeat":"topsecret"
}
```

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

## DELETE /api/frontendUser/{frontendUser_id}

**Request Parameters** 

Parameter       | Description
-------------   | -------------
frontendUser_id | The id of the frontendUser to delete.

**Request** None.

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)