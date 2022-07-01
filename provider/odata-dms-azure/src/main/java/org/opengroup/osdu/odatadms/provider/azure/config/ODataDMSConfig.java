package org.opengroup.osdu.odatadms.provider.azure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("osdu.odatadms.dms")
@Configuration
@Getter
@Setter
public class ODataDMSConfig
{
    // the URLs for the (typically external) target Storage API itself
    // {single curly braces with numbers} are used to signify tokens
    // examples:
    // "https://mock-dsis-api.azurewebsites.net/api/{2}/{3}/{4}/{5}?$filter=(native_uid%20eq%20%27{6}%27)&$format=json"
    // "https://mock-dsis-api.azurewebsites.net/api/{2}/{3}/{4}/{5}?$filter=(native_uid%20eq%20%{6}%27%20and%20well_id%20eq%20%27{7}%27%20and%20wellbore_id%20eq%20%27{8}%27%20and%20assembly_id%20eq%20%27{9}%27)&$format=json"
    private String tokenizedURL;
    private String tokenizedURL_compositeKey;

}
