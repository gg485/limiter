package model;

public class ResourceCommon {
    String id;
    long capacity;
    long refreshInterval;
    long algorithm;
    long updateTime;
    String description;

    public String getId() {
        return id;
    }

    public long getCapacity() {
        return capacity;
    }

    public long getRefreshInterval() {
        return refreshInterval;
    }

    public long getAlgorithm() {
        return algorithm;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }

    public void setRefreshInterval(long refreshInterval) {
        this.refreshInterval = refreshInterval;
    }

    public void setAlgorithm(long algorithm) {
        this.algorithm = algorithm;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
