package org.example.model;

public enum Type {
    CANTONALE("CANTONALE", "Caisse cantonale"),
    PROFESSIONNELLE("PROFESSIONNELLE", "Caisse professionnelle");

    private final String databaseType;
    private final String clientType;
    Type(String databaseType, String clientType) {
        this.databaseType = databaseType;
        this.clientType = clientType;
    }
    public String getDatabaseType() {
        return databaseType;
    }
    public String getClientType() {
        return clientType;
    }
    public static String getDatabaseType(String clientType) {
        for (Type type : values()) {
            if (type.getClientType().equals(clientType)) {
                return type.getDatabaseType();
            }
        }
        return "";
    }

    public static String getClientType(String databaseType) {
        for (Type type : values()) {
            if (type.getDatabaseType().equals(databaseType)) {
                return type.getClientType();
            }
        }
        return "";
    }
}
