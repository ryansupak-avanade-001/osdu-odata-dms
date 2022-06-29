package org.opengroup.osdu.odatadms.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.opengroup.osdu.core.common.dms.model.RetrievalInstructionsResponse;
import org.opengroup.osdu.core.common.model.http.AppException;
import org.opengroup.osdu.core.common.model.http.DpsHeaders;
import org.opengroup.osdu.odatadms.dms.DmsServiceProperties;
import org.opengroup.osdu.odatadms.service.ODataDmsService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


@RunWith(MockitoJUnitRunner.class)
public class ODataDmsServiceImplTest {


    @Mock
    private DpsHeaders headers;


    @Mock
    DmsServiceProperties dmsServiceProperties;

    @InjectMocks
    ODataDmsService datasetDmsService;

    private final String RECORD_ID = "opendes:odatadms--file:data";
    private final String INVALID_RECORD_ID = "closedes:odatadms--file:data";
    private final String DATA_PARTITION_ID = "opendes";
    private final String KIND = "odatadms--file";
    private final String INVALID_KIND = "odatadms---file";
    private final String KIND_TYPE_2 = "odatadms--file.*";

    Map<String, DmsServiceProperties> kindSubTypeToDmsServiceMap;
    List<String> datasetRegistryIds;

    @Before
    public void setup() {
        initMocks(this);
        kindSubTypeToDmsServiceMap = new HashMap<>();
        kindSubTypeToDmsServiceMap.put(KIND, dmsServiceProperties);
        when(headers.getPartitionId()).thenReturn(DATA_PARTITION_ID);
    }

    @Test
    public void testGetRetrievalInstructions() throws Exception {
        datasetRegistryIds = Collections.singletonList(RECORD_ID);
        testRetrievalInstructions();
    }

    @Test
    public void testGetRetrievalInstructionsWithKindType2() throws Exception {

        addKindType2InMap();
        datasetRegistryIds = Collections.singletonList(RECORD_ID);
        testRetrievalInstructions();
        removeKindType2InMap();

    }

    @Test(expected = AppException.class)
    public void testGetRetrievalInstructionsWithInvalidRecordID() throws Exception {

        datasetRegistryIds = Collections.singletonList(INVALID_RECORD_ID);
        testRetrievalInstructions();
    }

    private void addKindType2InMap() {
        kindSubTypeToDmsServiceMap.remove(KIND);
        kindSubTypeToDmsServiceMap.put(KIND_TYPE_2, dmsServiceProperties);
    }

    private void removeKindType2InMap() {
        kindSubTypeToDmsServiceMap.remove(KIND_TYPE_2);
    }

    private void testRetrievalInstructions() throws Exception {
        RetrievalInstructionsResponse entryResponse = new RetrievalInstructionsResponse();
        //injectWhenClauseForDmsServiceMapAndDmsFactory();
        //when(dmsProvider.getRetrievalInstructions(any())).thenReturn(entryResponse);
        RetrievalInstructionsResponse actualResponse = datasetDmsService.getRetrievalInstructions(datasetRegistryIds);
        //verifydmsServiceMapAndDmsFactory();
        //verify(dmsProvider, times(1)).getRetrievalInstructions(any());
    }


}