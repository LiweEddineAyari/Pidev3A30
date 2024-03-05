module main.projet {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;
    requires org.apache.pdfbox;
    requires com.google.api.client.auth;
    requires com.google.api.client.extensions.java6.auth;
    requires com.google.api.client.extensions.jetty.auth;
    requires google.api.client;
    requires com.google.api.client;
    requires com.google.api.client.json.gson;
    requires com.google.api.services.gmail;
    requires org.apache.commons.codec;
    requires activation;
    requires java.mail;
    requires stripe.java;
    requires com.google.zxing;
    requires java.desktop;
    requires javafx.swing;
    requires javafx.web;
    requires jdk.jsobject;
    requires org.json;


    opens main.projet to javafx.fxml;
    exports main.projet;
    opens entity to javafx.base;
}