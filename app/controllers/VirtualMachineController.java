/*
 * Copyright (c) 2014-2015 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package controllers;

import cloud.CloudService;
import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Named;
import components.execution.SimpleBlockingQueue;
import components.job.CreateVirtualMachineJob;
import components.job.Job;
import controllers.generic.GenericApiController;
import dtos.VirtualMachineDto;
import dtos.conversion.ModelDtoConversionService;
import models.VirtualMachine;
import models.service.api.generic.ModelService;

/**
 * Created by daniel on 09.04.15.
 */
public class VirtualMachineController extends
    GenericApiController<VirtualMachine, VirtualMachineDto, VirtualMachineDto, VirtualMachineDto> {

    private final CloudService cloudService;
    private final SimpleBlockingQueue<Job> jobQueue;

    /**
     * Constructs a GenericApiController.
     *
     * @param modelService      the model service for retrieving the models.
     * @param typeLiteral       a type literal for the model type
     * @param conversionService the conversion service for converting models and dtos.
     * @param cloudService
     * @param jobQueue
     * @throws NullPointerException if any of the above parameters is null.
     */
    @Inject public VirtualMachineController(ModelService<VirtualMachine> modelService,
        TypeLiteral<VirtualMachine> typeLiteral, ModelDtoConversionService conversionService,
        CloudService cloudService, @Named("jobQueue") SimpleBlockingQueue<Job> jobQueue) {
        super(modelService, typeLiteral, conversionService);
        this.cloudService = cloudService;
        this.jobQueue = jobQueue;
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.VirtualMachineController.get(id).absoluteURL(request());
    }

    @Override protected void postPost(VirtualMachine entity) {
        CreateVirtualMachineJob createVirtualMachineJob =
            new CreateVirtualMachineJob(entity, getModelService(), cloudService);
        jobQueue.add(createVirtualMachineJob);
    }
}
