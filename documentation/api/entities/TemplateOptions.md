# TemplateOptions Actions
***
## Description
Template Options that can be used to change the start behaviour of virtual machines.
***
## GET /api/templateOptions
###Description
Lists all template options available in the system.

###Request Parameters
None

###Response
A list of all template options entities stored in the database.

###Response Example
```
[  
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/templateOptions/1",
            "rel":"self"
         }
      ],
      "tags": {
        "key":"value"
      }
   },
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/templateOptions/2",
            "rel":"self"
         }
      ],
      "tags": {
        "key":"value",
        "anotherKey":"anotherValue"
      }
   }
]
```
###Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)
***
## GET /api/templateOptions/{templateOptions_id}

###Description
Shows the template option entity identified by the given {templateOptions_id}.

###Request Parameters
Parameter             | Description
-------------         | -------------
templateOptions_id     | The id of the template options entity.

###Response
Shows the selected template options entity.

###Response Example
```
{  
  "links":[  
     {  
        "href":"http://example.com:9000/templateOptions/2",
        "rel":"self"
     }
  ],
  "tags": {
    "key":"value",
    "anotherKey":"anotherValue"
  }
}
```
###Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

***
## POST /api/templateOptions

###Description
Creates a template options and returns it.

###Request Parameters

Parameter     | Description
------------- | ----------------------------------------------------------
tags          | Meta-data in form of key/value pairs

###Request Example
```
"tags": {
"key":"value",
"anotherKey":"anotherValue"
}
```

###Response
Returns the created template options. See /api/templateOptions/{templateOptions_id}

###Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

***
## PUT /api/templateOptions/{templateOptions_id}

###Description
Updates the virtual machine with the information provided in the request.

###Request Parameters
Parameter            | Description
-------------------- | -------------
templateOptions_id     | The id of the template options entity.
tags          | Meta-data in form of key/value pairs

###Request Example
```
PUT /api/templateOptions/1
```
```
"tags": {
  "key":"value",
  "anotherKey":"anotherValue"
}
```
###Response
The updated template options entity. See /api/templateOptions/{templateOptions_id}

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***
## DELETE /api/virtualMachine/{virtualMachine_id}

###Request Parameters

Parameter           | Description
------------------- | ---------------------------------------
templateOptions_id     | The id of the template options entity.

### Response
No data.

### Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
