package com.example.treevisualize;

import javafx.application.Application; // Nhớ import thư viện này

public class TreeAppLauncher {
    public static void main(String[] args) {
        // Cách mới: Gọi trực tiếp trình khởi chạy của JavaFX cho class Main3
        Application.launch(Main5.class, args);
    }
}

/*
cd "d:\File_In_OS(C)\Documents\OOP-2025.1\TreeVisualize\src\main\java\"
$FX = "D:\File_In_OS(C)\Downloads\openjfx-25.0.1_windows-x64_bin-sdk\javafx-sdk-25.0.1\lib"

$javaFiles = Get-ChildItem -Recurse . -Filter *.java | Select-Object -ExpandProperty FullName
javac --module-path $FX --add-modules javafx.controls,javafx.fxml -d . $javaFiles

java --module-path $FX --add-modules javafx.controls,javafx.fxml com.example.treevisualize.TreeAppLauncher
*/