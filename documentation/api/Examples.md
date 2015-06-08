# Examples

## Install an application component on a virtual machine.

### 1. Query or create the application

Query if the application does already exist: [GET /api/application](entities/Application.md)

If the application does not already exist, create a new one: [POST /api/application](entities/Application.md)


### 2. Query or create the component

> Note: The only supported component currently is the [LifecycleComponent](entities/LifecycleComponent.md)

Query if the lifecycle compent already exists: [GET /api/lifecycleComponent](entities/LifecycleComponent.md)

If it does not exist yet, create it: [POST /api/lifecycleComponent](entities/LifecycleComponent.md)

### 3. Query or create a virtual machine template.

> Note: This step is only necessary if you created a new application or a new component.

Query if a matching virtual machine template already exists: [GET /api/vmt](entities/VirtualMachineTemplate.md).

If it does not exist yet, create it: [POST /api/vmt](entities/VirtualMachineTemplate.md)

### 4. Connect the newly created application with its components (or vice versa)

> Note: This step is only necessary if you created a new application or a new component.

Create a new application component entity, using the ids retrieved from the previous steps: [POST /api/ac](entities/ApplicationComponent.md)

Repeat this until all components are added to the application.

### 5. Add communication

> Note: This step is only necessary if you created application components in the previous step.

Create the required communication entities between the application components (setting them as provider/consumer): [PUT /api/communication](entities/Communication.md)

### 6. Query or start a new virtual machine

If you want to use an existing virtual machine query for it: [GET /api/virtualMachine](entities/VirtualMachine.md).

If you want to start a new virtual machine, create it: [POST /api/virtualMachine](entities/VirtualMachine.md)

### 7. Instantiate the application.

Create a new application instance [POST /api/applicationInstance](entities/ApplicationInstance.md).

### 8. Install the application component on the virtual machine

Create a new instance entity: [POST /api/instance](entities/Instance.md).
