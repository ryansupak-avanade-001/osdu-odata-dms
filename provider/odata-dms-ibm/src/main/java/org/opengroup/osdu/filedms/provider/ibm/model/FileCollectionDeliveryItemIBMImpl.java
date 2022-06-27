/* Licensed Materials - Property of IBM              */
/* (c) Copyright IBM Corp. 2020. All Rights Reserved.*/

package org.opengroup.osdu.odatadms.provider.ibm.model;

import java.time.Instant;

import org.opengroup.osdu.odatadms.model.FileDeliveryItem;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FileCollectionDeliveryItemIBMImpl implements FileDeliveryItem {
	
	 	@JsonProperty("unsignedUrl")
	    String unsignedUrl;

	    @JsonProperty("createdAt")
	    Instant createdAt;

	    @JsonProperty("indexFilePath")
	    String indexFilePath;

	    @JsonProperty("connectionString")
	    String connectionString;

	    @JsonProperty("credentials")
	    private TemporaryCredentials credentials;

	    @JsonProperty("region")
	    private String region;

}
