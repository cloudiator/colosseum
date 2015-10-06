# VirtualMachine Actions
***
## Description
The virtual machine entity represents a specific virtual machine.
***
## GET /api/virtualMachine
###Description
Lists all virtual machines available in the system.

###Request Parameters
None

###Response
A list of all virtual machine entities stored in the database.

###Response Example
```
[  
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/virtualMachine/1",
            "rel":"self"
         }
      ],
      "name":"scalarm_vm_1",
      "cloud":1,
      "image":1,
      "hardware":1,
      "location":1,
      "templateOptions":1
   },
   {  
      "links":[  
         {  
            "href":"http://example.com:9000/virtualMachine/2",
            "rel":"self"
         }
      ],
      "name":"scalarm_vm_2",
      "cloud":1,
      "image":1,
      "hardware":1,
      "location":2
   }
]
```
###Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)
***
## GET /api/virtualMachine/{virtualMachine_id}

###Description
Shows the virtual machine entity identified by the given {virtualMachine_id}.

###Request Parameters
Parameter             | Description
-------------         | -------------
virtualMachine_id     | The id of the virtualMachine.

###Response
Shows the selected virtualMachine entity.

###Response Example
```
{  
   "links":[  
      {  
         "href":"http://example.com:9000/virtualMachine/1",
         "rel":"self"
      }
   ],
   "name":"scalarm_vm_1",
   "cloud":1,
   "image":1,
   "hardware":1,
   "location":1,
   "templateOptions":1
}
```
###Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)

***
## POST /api/virtualMachine

###Description
Creates a virtual machine and returns it.

###Request Parameters

Parameter     | Description
------------- | ----------------------------------------------------------
cloud         | The cloud the virtual machine will be started in.
image    | The cloud image that will be used to start the machine.
location | The cloud location that will be used to start the machine.
hardware | The cloud hardware that will be used to start the machine.
templateOptions | An optional template options entity used to start the machine.

###Request Example
```
{  
   "name":"scalarm_vm_1",
   "cloud":1,
   "image":1,
   "hardware":1,
   "location":1,
   "templateOptions":1
}
```

###Response
Returns the create virtual machine. See /api/virtualMachine/{virtualMachine_id}

###Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)

***
## PUT /api/virtualMachine/{virtualMachine_id}

###Description
Updates the virtual machine with the information provided in the request.

###Request Parameters
Parameter            | Description
-------------------- | -------------
virtualMachine_id    | The id of the virtual machine to be updated.
cloud                | The cloud the virtual machine will be started in.
image           | The cloud image that will be used to start the machine.
location        | The cloud location that will be used to start the machine.
hardware        | The cloud hardware that will be used to start the machine.
templateOptions | An optional template options entity used to start the machine.

###Request Example
```
PUT /api/virtualMachine/1
```
```
{  
   "name":"Scalarm",
   "cloud":1,
   "image":1,
   "hardware":1,
   "location":1,
   "templateOptions":1
}
```
###Response
The updated virtual machine. See GET /api/virtualMachine/{virtualMachine_id}

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)

***
## DELETE /api/virtualMachine/{virtualMachine_id}

###Request Parameters

Parameter           | Description
------------------- | ---------------------------------------
virtualMachine_id   | The id of the virtualMachine to delete.

### Response
No data.

### Response Codes
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
