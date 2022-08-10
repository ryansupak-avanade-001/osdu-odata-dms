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

import java.io.UnsupportedEncodingException;
import java.util.*;
import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.opencensus.trace.Link;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.dynamic.scaffold.MethodGraph;
import org.opengroup.osdu.core.common.dms.model.DatasetRetrievalProperties;
import org.opengroup.osdu.core.common.dms.model.RetrievalInstructionsResponse;
import org.opengroup.osdu.core.common.http.HttpRequest;
import org.opengroup.osdu.core.common.http.HttpResponse;
import org.opengroup.osdu.core.common.http.json.HttpResponseBodyMapper;
import org.opengroup.osdu.core.common.model.http.DpsHeaders;

import org.opengroup.osdu.core.common.model.storage.Record;
import org.opengroup.osdu.core.common.model.storage.RecordData;
import org.opengroup.osdu.odatadms.provider.azure.config.ODataAPIConfig;
import org.opengroup.osdu.odatadms.service.ODataDmsService;
import org.opengroup.osdu.odatadms.model.response.ODataDMSRetrievalDeliveryItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.opengroup.osdu.core.common.http.IHttpClient;

import org.opengroup.osdu.core.common.model.storage.RecordData;

@Service
@RequiredArgsConstructor
public class ODataDMSServiceImpl implements ODataDmsService {

    @Inject
    private DpsHeaders headers;
    @Autowired
    private ODataAPIConfig oDataAPIConfig;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final IHttpClient httpClient;
    private final HttpResponseBodyMapper bodyMapper;

    @Override
    public RetrievalInstructionsResponse getRetrievalInstructions(List<String> datasetRegistryIds) throws UnsupportedEncodingException, JsonProcessingException {
        //for each datasetRegistryID:
        //call Storage Service and get back the corresponding record.
        //parse the URL and parse the (singleton) inline params
        //then parse the contents of PrimaryKey recursively

        RetrievalInstructionsResponse response = new RetrievalInstructionsResponse();
        List<DatasetRetrievalProperties> datasetRetrievalPropertiesList = new ArrayList<>();

        for (String datasetRegistryId : datasetRegistryIds)
        {
            ODataDMSRetrievalDeliveryItem oDataDMSRetrievalDeliveryItem = new ODataDMSRetrievalDeliveryItem();
            DatasetRetrievalProperties datasetRetrievalProperties = new DatasetRetrievalProperties();

            //parse request URL to storage
            String storageURL = oDataAPIConfig.getStorage() + "/records/" + urlEncodeValue(datasetRegistryId);
            RecordData storageServiceResponse = makeStorageHttpRequest(storageURL);

            oDataDMSRetrievalDeliveryItem.recordId = datasetRegistryId;
            oDataDMSRetrievalDeliveryItem.oDataQuery = parseReturnURLFromStorageServiceResponse(storageServiceResponse);

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

    private RecordData makeStorageHttpRequest(String storageURL) throws JsonProcessingException {
        //make HTTP request to Storage
        HttpResponse result = this.httpClient.send(HttpRequest.get().url(storageURL).headers(this.headers.getHeaders()).build());
        return OBJECT_MAPPER.readValue(result.getBody(), RecordData.class);
        //return result.getBody();
    }

    private String parseReturnURLFromStorageServiceResponse(RecordData storageServiceResponse)
    {
        String parsedURL = "";

        LinkedHashMap<String, Object> data = (LinkedHashMap<String, Object>) storageServiceResponse.getData();
        LinkedHashMap<String, Object> datasetProperties = (LinkedHashMap<String, Object>) data.get("DatasetProperties");
        LinkedHashMap<String, Object> oDataDataSourceInfo = (LinkedHashMap<String, Object>) datasetProperties.get("ODataDataSourceInfo");

        String baseURL = (String) oDataDataSourceInfo.get("BaseUrl");

        LinkedHashMap<String, Object> inlinePathParams = (LinkedHashMap<String, Object>) oDataDataSourceInfo.get("InlinePathParams");
        String model = (String) inlinePathParams.get("Model");
        String version = (String) inlinePathParams.get("Version");
        String dataSource = (String) inlinePathParams.get("DataSource");
        String project = (String) inlinePathParams.get("Project");
        String entityType = (String) inlinePathParams.get("EntityType");

        parsedURL = baseURL + "/" + model + "/" + version + "/" + dataSource + "/" + project + "/" + entityType + "?filter=(";

        LinkedHashMap<String, Object> primaryKey = (LinkedHashMap<String, Object>) oDataDataSourceInfo.get("PrimaryKey");

        //add each PrimaryKey key:value pair to parsed UDL
        for (Map.Entry<String, Object> entry : primaryKey.entrySet())
        {
            String key = entry.getKey();
            String value = (String) primaryKey.get(key);
            parsedURL = parsedURL + key + "%20eq%20%27" + value + "%27" + "%20and%20";
        }

        //remove the final "and" from parsed URL
        int removeIndex = parsedURL.lastIndexOf("%20and%20");
        parsedURL = parsedURL.substring(0, removeIndex);

        //finalize the parsing
        parsedURL = parsedURL + ")&$format=json";
        return parsedURL;
    }

    private String getStorageURLFromConfig()
    {
        return oDataAPIConfig.getStorage();
    }

    private String urlEncodeValue(String value) throws UnsupportedEncodingException
    {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }

    private Map<String, Object> castODataDMSRetrievalPropertiesItemToMap(ODataDMSRetrievalDeliveryItem oDataDMSRetrievalDeliveryItem)
    {
        Map<String,Object> mappedOutput = new HashMap<String, Object>();

        mappedOutput.put("oDataQuery", oDataDMSRetrievalDeliveryItem.oDataQuery);
        mappedOutput.put("recordId", oDataDMSRetrievalDeliveryItem.recordId);

        return mappedOutput;
    }
}
