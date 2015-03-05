# FrontendUser Actions
***

## Description

The frontend user entity represents a user of the system.

## GET /api/frontendUser

### Description

Retrieves all users of the system.

### Request Parameters
None

### Response
A list of all frontendUser entities stored in the database.

### Response Example
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
   }
]
```

### Response codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

## GET /api/frontendUser/{frontendUser_id}

### Description

Retrieves the frontend user identified by the given id.

### Request Parameters

Parameter       | Description
-------------   | -------------
frontendUser_id | The id of the frontendUser.

### Response
Shows the selected frontendUser entity.

### Response Example

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

### Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

## POST /api/frontendUser

### Description

Creates a new frontend user entity.

### Request Parameters

Parameter       | Description
-------------   | -------------
firstName       | The first name of the user.
lastName        | The last name of the user.
mail            | The mail address of the user.

### Request Example
```    
{  
   "firstName":"John",
   "lastName":"Doe",
   "mail":"john.doe@example.com",
}
```

### Response

The created frontend user entity. See /api/frontendUser/{frontendUser_id}.

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

## PUT /api/frontendUser/{frontendUser_id}

### Description

Updates the frontend user identified by the given id with the data from the request. Returns
the updated entity.

### Request Parameters

Parameter       | Description
-------------   | -------------
frontendUser_id | The id of the frontendUser to update.
firstName       | The first name of the user.
lastName        | The last name of the user.
mail            | The mail address of the user.

### Request Example
```
PUT /api/frontendUser/1
```
```
{  
   "firstName":"John",
   "lastName":"Doe",
   "mail":"john.doe@example.com",
}
```

### Response

The updated frontend user entity. See /api/frontendUser/{frontendUser_id}

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

## DELETE /api/frontendUser/{frontendUser_id}

### Description
Deletes the frontend user identified by the given id.

### Request Parameters

Parameter       | Description
-------------   | -------------
frontendUser_id | The id of the frontendUser to delete.

### Response
No data.

### Response Codes
**Normal Response Code** 200

**Error Response Csode** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)