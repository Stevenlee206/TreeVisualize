package com.example.treevisualize.Description;
public abstract class TreeDescription implements Description {

    protected abstract String getTreeName();
    protected abstract String getDefinition();
    protected abstract String getTimeComplexity();
    protected abstract String getSpaceComplexity();

    @Override
    public final String getDescription() {
        return """
            %s
            ---------------------------------------
            %s
            
            COMPLEXITY:
            =======================================
            1. Time Complexity:
            %s
            
            2. Space Complexity:
               - %s
            """.formatted(
                getTreeName().toUpperCase(),
                getDefinition(),
                getTimeComplexity(),
                getSpaceComplexity()
        );
    }
}
