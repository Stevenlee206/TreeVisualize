package com.example.treevisualize.demo;

import javafx.application.Application;

public class TreeAppLauncher {
    public static void main(String[] args) {
        Application.launch(Main5.class, args);
    }
}

/*
cd "D:/File_In_OS(C)/Documents/TreeVisualizeLatest/src/main/java"
$FX = "D:/File_In_OS(C)/Downloads/openjfx-25.0.1_windows-x64_bin-sdk/javafx-sdk-25.0.1/lib"

$javaFiles = Get-ChildItem -Recurse . -Filter *.java | Select-Object -ExpandProperty FullName
javac --module-path $FX --add-modules javafx.controls,javafx.fxml -d . $javaFiles

java --module-path $FX --add-modules javafx.controls,javafx.fxml com.example.treevisualize.demo.TreeAppLauncher
*/