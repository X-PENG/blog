package com.peng.blog.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "peng.upload")
public class UploadFileProperties {
    private String accessPath;
    private String resourcesLocation;

    public String getAccessPath() {
        return accessPath;
    }

    public void setAccessPath(String accessPath) {
        this.accessPath = accessPath;
    }

    public String getResourcesLocation() {
        return resourcesLocation;
    }

    public void setResourcesLocation(String resourcesLocation) {
        this.resourcesLocation = resourcesLocation;
    }
}
