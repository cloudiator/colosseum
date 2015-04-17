# FrontendGroup Actions
***

## Description

A frontend group groups multiple frontend users into a logical entity.

## GET /api/fg

### Description

Retrieves all group stored in the system.

### Request Parameters
None

### Response
A list of all frontend group entities stored in the database.

### Response Example
```
[
  {
    "name":"My FrontendGroup",
    "frontendUsers":[
      1
    ],
    "link":[
      {
        "href":"http://example.com:9000/api/fg/1",
        "rel":"self"
      }
    ]
  },
  {
    "name":"PaaSage",
    "frontendUsers":[
      1,
      2
    ],
    "link":[
      {
        "href":"http://example.com:9000/api/fg/2",
        "rel":"self"
      }
    ]
  }
]
```

### Response codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)

## GET /api/fg/{fg_id}

### Description

Retrieves the frontend group identified by the given id.

### Request Parameters

Parameter       | Description
--------------- | -------------
fg_id           | The id of the frontend group.

### Response
Shows the selected frontend group entity.

### Response Example

```
{
  "name":"PaaSage",
  "frontendUsers":[
    1,
    2
  ],
  "link":[
    {
      "href":"http://example.com:9000/api/fg/2",
      "rel":"self"
    }
  ]
}
```

### Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

## POST /api/fg

### Description

Creates a new frontend group entity.

### Request Parameters

Parameter       | Description
--------------- | -------------
name            | The name of the group.
frontendUsers   | A list of all frontend users which belong to the group.

### Request Example
```    
{
  "name":"PaaSage",
  "frontendUsers":[
    1
  ]
}
```

### Response

The created frontend group entity. See /api/fg/{fg_id}.

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

## PUT /api/fg/{fg}

### Description

Updates the frontend group identified by the given id with the data from the request. Returns
the updated entity.

### Request Parameters

Parameter       | Description
--------------- | -------------
fg_id           | The id of the group to update.
name            | The name of the group.
frontendUsers   | A list of all frontend users which belong to the group.

### Request Example
```
PUT /api/fg/1
```
```
{
  "name":"PaaSage",
  "frontendUsers":[
    1
  ]
}
```

### Response

The updated frontend group entity. See /api/fg/{fg_id}

### Response Codes

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

## DELETE /api/fg/{fg_id}

### Description
Deletes the frontend group identified by the given id.

### Request Parameters

Parameter       | Description
-------------   | -------------
fg_id           | The id of the frontend group to delete.

### Response
No data.

### Response Codes
**Normal Response Code** 200

**Error Response Csode** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
