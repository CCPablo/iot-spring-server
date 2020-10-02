package internal.client.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class URLBuilder {
    private final StringBuilder folders;
    private final StringBuilder params;
    private String connectionType, host;

    public URLBuilder(){
        folders = new StringBuilder();
        params = new StringBuilder();
    }

    public URLBuilder(String connectionType, String host) {
        this();
        this.host = host;
        this.connectionType = connectionType;
    }

    public URLBuilder addSubfolders(List<String> folders) {
        folders.forEach(this::addSubfolder);
        return this;
    }

    public URLBuilder addSubfolder(String folder) {
        folders.append("/");
        folders.append(folder);
        return this;
    }


    public URLBuilder addParameter(String parameter, String value) {
        if(params.toString().length() > 0){params.append("&");}
        params.append(parameter);
        params.append("=");
        params.append(value);
        return this;
    }

    public URLBuilder addParameters(String parameter, List<String> values) {
        values.forEach( (value) -> {
            if(params.toString().length() > 0){params.append("&");}
            params.append(parameter);
            params.append("=");
            params.append(value);
        });
        return this;
    }

    public URLBuilder addParameters(Map<String, Object> values) {
        values.forEach( (key, value) -> {
            if(value instanceof List<?>) {
                addParameters(key, (List<String>) value);
            } else {
                addParameter(key, (String)value);
            }
        });
        return this;
    }

    public String getURLString() throws URISyntaxException, MalformedURLException {
        URI uri = new URI(connectionType, host, folders.toString(),
                params.toString(), null);
        return uri.toURL().toString();
    }

    public URL getURL() throws URISyntaxException, MalformedURLException {
        URI uri = new URI(connectionType, host, folders.toString(),
                params.toString(), null);
        return uri.toURL();
    }

    public String getRelativeURL() throws URISyntaxException, MalformedURLException{
        URI uri = new URI(null, null, folders.toString(), params.toString(), null);
        return uri.toString();
    }
}