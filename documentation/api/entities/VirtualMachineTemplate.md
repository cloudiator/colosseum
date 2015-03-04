# VirtualMachineTemplate Actions
***

## Description

The VirtualMachineTemplate represents a template for creating virtual machines. It describes the resources need to start a virtual machine for the application component it is attached to.
***

## GET /api/virtualMachineTemplate

**Description** Returns a list of virtual machine templates supported by the system.

**Request Parameters** None

**Request Body** This operation does not require a request body.

**Response** A list of all virtual machine template entities stored in the database.

**Response Body Example**
```
[
   {
      "links":[
         {
            "href":"http://example.com:9000/api/virtualMachineTemplate/1",
            "rel":"self"
         }
      ],
      "cloud":1
      "cloudImage":1
      "cloudLocation":2
      "cloudHardware":3s
   }
]
```
**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized)
***
## GET /api/virtualMachineTemplate/{vmt_id}

**Description** Returns the virtual machine template identified by the given {vmt_id}.

**Request Parameters**

Parameter     | Description
------------- | -------------
vmt_id        | The id of the virtual machine template.

**Request Body** This operation does not require a request body.

**Response Body Example** Shows the selected virtual machine template entity.

```
{
  "links":[
     {
        "href":"http://example.com:9000/api/virtualMachineTemplate/1",
        "rel":"self"
     }s
  ],
  "cloud":1
  "cloudImage":1
  "cloudLocation":2
  "cloudHardware":3s
}
```

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)
***
## POST /api/virtualMachineTemplate

**Description** Creates a new virtual machine template entity. The new entity will be returned.

**Request Parameters**

Parameter     | Description
------------- | ----------------------------------------------------------
cloud         | The cloud the virtual machine will be started in.
cloudImage    | The cloud image that will be used to start the machine.
cloudLocation | The cloud location that will be used to start the machine.
cloudHardware | The cloud hardware that will be used to start the machine.

**Request Body Example**
```
{
  "cloud":1
  "cloudImage":1
  "cloudLocation":2
  "cloudHardware":3
}
```
**Response** The created entity. See GET /api/virtualMachineTemplate/{vmt_id}

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 400 (bad request)
***
## PUT /api/virtualMachineTemplate/{vmt_id}

**Description** Updates the virtual machine template identified by the given id.

**Request Parameters** 

Parameter     | Description
------------- | ----------------------------------------------------------
cloud         | The cloud the virtual machine will be started in.
cloudImage    | The cloud image that will be used to start the machine.
cloudLocation | The cloud location that will be used to start the machine.
cloudHardware | The cloud hardware that will be used to start the machine.

**Request Body Example**
```
{
  "cloud":1
  "cloudImage":1
  "cloudLocation":1
  "cloudHardware":1
}
```
**Response** The updated entity. See GET /api/virtualMachineTemplate/{vmt_id}

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found), 400 (bad request)
***
## DELETE /api/virtualMachineTemplate/{vmt_id}

**Description** Deletes the virtual machine template identified by the given {vmt_id}.

**Request Parameters** 

Parameter     | Description
------------- | -----------------------------------------------------
vmt_id        | The id of the virtual machine template to be deleted.

**Response** No data.

**Normal Response Code** 200

**Error Response Code** 500 (server error), 403 (forbidden), 401 (unauthorized), 404 (not found)