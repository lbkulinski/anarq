enum Permission 
{ 
    0, 1; 
} 

public class ConnectedClient {
    String name;
    String ipAddress;
    Permission permissionLevel;
    LocalTime lastActive;

    public ConnectedClient(String name, String ipAddress, Permission permissionLevel, LocalTime lastActive) {
        this.name = name;
        this.ipAddress = ipAddress;
        this.permissionLevel = permissionLevel;
        this.lastActive = lastActive;
    }

    int getMaxRequests() {
        return this.MAX_REQUESTS;
    }

    String getName() {
        return this.name;
    }

    String getIpAddress() {
        return this.ipAddress;
    }

    Permission getPermissionLevel() {
        return this.permissionLevel;
    }

    LocalTime getLocalTime() {
        return this.LocalTime
    }
}