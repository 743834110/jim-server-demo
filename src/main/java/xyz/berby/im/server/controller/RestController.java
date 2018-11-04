package xyz.berby.im.server.controller;

import org.jim.common.http.HttpRequest;
import org.jim.common.http.HttpResponse;
import org.jim.server.http.annotation.RequestPath;
import org.jim.server.util.HttpResps;
import xyz.berby.im.util.ApplicationContextHolder;

@RequestPath("rest")
public class RestController {

    public RestController() {
    }


    @RequestPath("test")
    public HttpResponse httpResponse(HttpRequest httpRequest) {

        return HttpResps.js(httpRequest, "fdfdfdfdfdfdfd");
    }
}
