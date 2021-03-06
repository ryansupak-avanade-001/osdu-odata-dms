/*
 * Copyright 2021 Google LLC
 * Copyright 2021 EPAM Systems, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opengroup.osdu.odatadms.provider.gcp.dms;

import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.opengroup.osdu.odatadms.provider.gcp.config.GcpConfigProperties;
import org.opengroup.osdu.odatadms.provider.gcp.model.dataset.DmsServicePropertiesEntity;
import org.opengroup.osdu.odatadms.provider.gcp.mappers.osm.repository.DmsServicePropertiesRepository;

@RunWith(MockitoJUnitRunner.class)
public class ODataDmsServiceMapImplTest {

  private static final String DATASET_KIND = "example";

  @Mock
  private DmsServicePropertiesRepository dmsServicePropertiesEntityRepository;

  @Mock
  private GcpConfigProperties gcpConfigProperties;


  @Before
  public void setUp() {
    DmsServicePropertiesEntity entity = new DmsServicePropertiesEntity();
    entity.setDatasetKind(DATASET_KIND);
    Iterable<DmsServicePropertiesEntity> entities = Collections.singletonList(entity);
    Mockito.when(this.dmsServicePropertiesEntityRepository.findAll()).thenReturn(entities);
  }

}
