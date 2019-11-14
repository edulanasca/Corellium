package Corellium.Ventana.Web.modelo;

import javafx.concurrent.Task;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.HttpClientBuilder;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

public class DescargarArchivo {

//    private static final int DEFAULT_BUFFER_SIZE = 1024;
//
//    private HttpClient httpClient;
//    private String remoteUrl;
//    private File localFile;
//    private int bufferSize;
//
//    public DescargarArchivo(String remoteUrl, File localFile) {
//        this(HttpClientBuilder.create().build(), remoteUrl, localFile, DEFAULT_BUFFER_SIZE);
//    }
//
//    public DescargarArchivo(HttpClient httpClient, String remoteUrl, File localFile, int bufferSize) {
//        this.httpClient = httpClient;
//        this.remoteUrl = remoteUrl;
//        this.localFile = localFile;
//        this.bufferSize = bufferSize;
//
//        stateProperty().addListener((src, os, ns) -> {
//            if(ns.equals(State.SUCCEEDED)) {
//                getOnSucceeded();
//            } else if(ns.equals(State.FAILED)) {
//                getOnFailed();
//            }
//        });
//    }
//
//    public String getRemoteUrl() {
//        return remoteUrl;
//    }
//
//    public File getLocalFile() {
//        return localFile;
//    }
//
//    protected File call() throws Exception {
//        HttpGet httpGet = new HttpGet(this.remoteUrl);
//        HttpResponse httpResponse = httpClient.execute(httpGet);
//        InputStream rempteContentStream = httpResponse.getEntity().getContent();
//        OutputStream localFileStream = null;
//        try {
//            long fileSize = httpResponse.getEntity().getContentLength();
//
//
//        }
//
//    }
}
