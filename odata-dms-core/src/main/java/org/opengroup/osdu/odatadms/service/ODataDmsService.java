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

package org.opengroup.osdu.odatadms.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.opengroup.osdu.core.common.dms.model.RetrievalInstructionsResponse;
import org.opengroup.osdu.odatadms.model.response.GetDatasetRetrievalInstructionsResponse;

public interface ODataDmsService {

    default RetrievalInstructionsResponse getRetrievalInstructions(List<String> datasetRegistryIds) throws UnsupportedEncodingException, JsonProcessingException {
        return null;
    }
}
