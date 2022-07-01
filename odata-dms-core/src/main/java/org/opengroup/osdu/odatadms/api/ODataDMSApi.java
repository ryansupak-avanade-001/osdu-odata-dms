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

package org.opengroup.osdu.odatadms.api;
import org.opengroup.osdu.odatadms.model.request.DeliveryRole;
import org.opengroup.osdu.odatadms.model.request.GetODataQueryRequest;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.opengroup.osdu.core.common.model.http.DpsHeaders;
import org.opengroup.osdu.odatadms.logging.AuditLogger;
import org.opengroup.osdu.odatadms.service.ODataDmsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;
import org.opengroup.osdu.odatadms.model.request.DeliveryRole;

@RestController
@RequestMapping("/")
@RequestScope
@Validated
public class ODataDMSApi {

    @Inject
	private DpsHeaders headers;
	
	@Inject
	private ODataDmsService ODataDmsService;

	@Inject
	private AuditLogger auditLogger;

	@PostMapping(value={"/retrievalInstructions","/getRetrievalInstructions"})
	@PreAuthorize("@authorizationFilter.hasRole('" + DeliveryRole.VIEWER + "')")
	// this was excluded
	public ResponseEntity<Object> retrievalInstructions_post
	(@RequestBody @Valid @NotNull GetODataQueryRequest request)
	{
		ResponseEntity response = getRetrievalInstructions(request.datasetRegistryIds);
		return response;
	}

	private ResponseEntity<Object> getRetrievalInstructions(List<String> datasetRegistryIds)
	{
		Object response = this.ODataDmsService.getRetrievalInstructions(datasetRegistryIds);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
