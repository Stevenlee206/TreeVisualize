package com.example.treevisualize.Description.Profiles;

public class TreeMetadata {
    private final String displayName;
    private final String iconPath;
    private final boolean requiresParentInput;

    public TreeMetadata(String displayName, String iconPath, boolean requiresParentInput) {
        this.displayName = displayName;
        this.iconPath = iconPath;
        this.requiresParentInput = requiresParentInput;
    }

    // Getters...
    public String getDisplayName() { return displayName; }
    public String getIconPath() { return iconPath; }
    public boolean isRequiresParentInput() { return requiresParentInput; }
}
