package org.opengroup.osdu.odatadms.provider.azure.service;

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

import java.util.*;
import java.text.MessageFormat;
import javax.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.opengroup.osdu.core.common.dms.model.DatasetRetrievalProperties;
import org.opengroup.osdu.core.common.dms.model.RetrievalInstructionsResponse;
import org.opengroup.osdu.core.common.http.json.HttpResponseBodyMapper;
import org.opengroup.osdu.core.common.model.http.DpsHeaders;

import org.opengroup.osdu.odatadms.model.response.DatasetRetrievalDeliveryItem;
import org.opengroup.osdu.odatadms.model.response.GetDatasetRetrievalInstructionsResponse;
import org.opengroup.osdu.odatadms.service.ODataDmsService;
import org.opengroup.osdu.odatadms.provider.azure.config.ODataDMSConfig;
import org.opengroup.osdu.odatadms.model.response.ODataDMSRetrievalDeliveryItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ODataDMSServiceImpl implements ODataDmsService {

    @Inject
    private DpsHeaders headers;
    @Autowired
    private ODataDMSConfig oDataDMSConfig;

    private final HttpResponseBodyMapper bodyMapper;

    @Override
    public RetrievalInstructionsResponse getRetrievalInstructions(List<String> datasetRegistryIds)
    {
        // Process incoming list of datasetRegistryIDs
        // The input format of a datasetRegistryId is similar to the following:
        //    "osdu:dataset--Odata.DSIS:Well--Param001--Param002--Param003--Param004--Param005"
        //          {0}             {1}          {2}       {3}       {4}       {5}       {6}
        // The output format of the returned URL is similar to the following:
        //    <StorageRestAPIURL>/Param001/Param002/Param003/Param004?$filter=(native_uid%20eq%20%27Param005%27)&$format=json
        // The StorageRestAPIURL (that is, the actual REST API endpoint itself) is obtained from the
        //    Provider-specific ODataDMSConfig.
        // Currently ODataDMSConfig is coded for only a single OData provider
        //    but that can of course easily be expanded in future.

        RetrievalInstructionsResponse response = new RetrievalInstructionsResponse();
        List<DatasetRetrievalProperties> datasetRetrievalPropertiesList = new ArrayList<>();

        for (String datasetRegistryId : datasetRegistryIds)
        {
            ODataDMSRetrievalDeliveryItem oDataDMSRetrievalDeliveryItem = new ODataDMSRetrievalDeliveryItem();
            DatasetRetrievalProperties datasetRetrievalProperties = new DatasetRetrievalProperties();

            //the delimiter inside the ID is "--"
            String[] tokensFromDatasetRegistryID = datasetRegistryId.split("--");
            String tokenizedURL = getTokenizedURLFromConfig(datasetRegistryId);

            oDataDMSRetrievalDeliveryItem.oDataQuery = parseODataQueryURL(tokensFromDatasetRegistryID, tokenizedURL);
            oDataDMSRetrievalDeliveryItem.recordId = datasetRegistryId;

            Map<String, Object> oDataDMSRetrievalPropertiesItem_Mapped = castODataDMSRetrievalPropertiesItemToMap(oDataDMSRetrievalDeliveryItem);
            datasetRetrievalProperties.setRetrievalProperties(oDataDMSRetrievalPropertiesItem_Mapped);
            datasetRetrievalPropertiesList.add(datasetRetrievalProperties);

        }
        try
        {
            response.setDatasets(datasetRetrievalPropertiesList);
        }
        catch (Exception e)
        {

        }

        return response;
    }

    private String parseODataQueryURL(String[] tokensFromDatasetRegistryID, String tokenizedURL)
    {
        MessageFormat urlFormatter = new MessageFormat(tokenizedURL);
        return urlFormatter.format(tokensFromDatasetRegistryID);
    }

    private String getTokenizedURLFromConfig(String dataRegistryID)
    {
        return oDataDMSConfig.getTokenizedURL();
    }

    private Map<String, Object> castODataDMSRetrievalPropertiesItemToMap(ODataDMSRetrievalDeliveryItem oDataDMSRetrievalDeliveryItem)
    {
        Map<String,Object> mappedOutput = new HashMap<String, Object>();

        mappedOutput.put("oDataQuery", oDataDMSRetrievalDeliveryItem.oDataQuery);
        mappedOutput.put("recordId", oDataDMSRetrievalDeliveryItem.recordId);

        return mappedOutput;
    }
}
