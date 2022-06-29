// Copyright © 2021 Amazon Web Services
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.opengroup.osdu.dataset.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.opengroup.osdu.core.common.dms.constants.DatasetConstants;
import org.opengroup.osdu.core.common.model.http.DpsHeaders;
import org.opengroup.osdu.dataset.logging.AuditLogger;
import org.opengroup.osdu.dataset.model.request.DeliveryRole;
import org.opengroup.osdu.dataset.model.request.GetDatasetRegistryRequest;
import org.opengroup.osdu.dataset.model.response.GetDatasetStorageInstructionsResponse;
import org.opengroup.osdu.dataset.service.DatasetDmsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;
	
@RestController
@RequestMapping("/")
@RequestScope
@Validated
public class DatasetDmsApi {

    @Inject
	private DpsHeaders headers;
	
	@Inject
	private DatasetDmsService datasetDmsService;

	@Inject
	private AuditLogger auditLogger;


    @GetMapping("/getStorageInstructions")	
	@PreAuthorize("@authorizationFilter.hasRole('" + DeliveryRole.VIEWER + "')")
	public ResponseEntity<GetDatasetStorageInstructionsResponse> getStorageInstructions( 
		@RequestParam(value = "kindSubType") String kindSubType) {

			GetDatasetStorageInstructionsResponse response = this.datasetDmsService.getStorageInstructions(kindSubType);
			this.auditLogger.readStorageInstructionsSuccess(Collections.singletonList(response.toString()));
			return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/storageInstructions")
	@PreAuthorize("@authorizationFilter.hasRole('" + DatasetConstants.DATASET_EDITOR_ROLE + "')")
	public ResponseEntity<GetDatasetStorageInstructionsResponse> storageInstructions(
			@RequestParam(value = "kindSubType") String kindSubType) {
		GetDatasetStorageInstructionsResponse response = this.datasetDmsService.getStorageInstructions(kindSubType);
		this.auditLogger.readStorageInstructionsSuccess(Collections.singletonList(response.toString()));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/*
	@GetMapping("/getRetrievalInstructions")	
	@PreAuthorize("@authorizationFilter.hasRole('" + DeliveryRole.VIEWER + "')")
	public ResponseEntity<Object> getRetrievalInstructions(
		@RequestParam(value = "id") String datasetRegistryId) {

			List<String> datasetRegistryIds = new ArrayList<>();
			datasetRegistryIds.add(datasetRegistryId);

			Object response = this.datasetDmsService.getDatasetRetrievalInstructions(datasetRegistryIds);
			this.auditLogger.readRetrievalInstructionsSuccess(Collections.singletonList(response.toString()));
			return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/getRetrievalInstructions")	
	@PreAuthorize("@authorizationFilter.hasRole('" + DeliveryRole.VIEWER + "')")
	public ResponseEntity<Object> getRetrievalInstructions( 
		@RequestBody @Valid @NotNull GetDatasetRegistryRequest request) {

			Object response = this.datasetDmsService.getDatasetRetrievalInstructions(request.datasetRegistryIds);
			this.auditLogger.readRetrievalInstructionsSuccess(Collections.singletonList(response.toString()));
			return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
	*/

	@GetMapping(value={"/retrievalInstructions","/getRetrievalInstructions"})
	//@PreAuthorize("@authorizationFilter.hasRole('" + DatasetConstants.DATASET_VIEWER_ROLE  + "')")
	public ResponseEntity<Object> retrievalInstructions_get(
			@RequestParam(value = "id") String datasetRegistryId) {

		List<String> datasetRegistryIds = new ArrayList<>();
		datasetRegistryIds.add(datasetRegistryId);

		return getRetrievalInstructions(datasetRegistryIds);
	}

	@PostMapping(value={"/retrievalInstructions","/getRetrievalInstructions"})
	@PreAuthorize("@authorizationFilter.hasRole('" + DeliveryRole.VIEWER + "')")
	public ResponseEntity<Object> retrievalInstructions_post(
			@RequestBody @Valid @NotNull GetDatasetRegistryRequest request) {

		return getRetrievalInstructions(request.datasetRegistryIds);
	}


	@PostMapping(value={"/testEndpoint"})
	@PreAuthorize("@authorizationFilter.hasRole('" + DeliveryRole.VIEWER + "')")
	public @Valid @NotNull String testEndpoint(@RequestBody @Valid @NotNull String request)
	{
		//return getRetrievalInstructions(request);
		return request;
	}

	private ResponseEntity<Object> getRetrievalInstructions(List<String> datasetRegistryIds)
	{
		Object response = this.datasetDmsService.getRetrievalInstructions(datasetRegistryIds);
		this.auditLogger.readRetrievalInstructionsSuccess(Collections.singletonList(response.toString()));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
