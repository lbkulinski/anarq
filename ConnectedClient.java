import java.time.LocalTime;

public class ConnectedClient {
    String name;
    String ipAddress;
    public enum Permission 
    { 
        DJ, JAMMER; 
    } 
    Permission permissionLevel;
    LocalTime lastActive;

    public ConnectedClient(String name, String ipAddress, Permission permissionLevel, LocalTime lastActive) {
        this.name = name;
        this.ipAddress = ipAddress;
        this.permissionLevel = permissionLevel;
        this.lastActive = lastActive;
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

    LocalTime getLastActive() {
        return this.lastActive;
    }
}