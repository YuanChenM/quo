package com.quotation.web.core.control;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by yang_shoulai on 8/14/2017.
 */
@Controller
public class TimeoutController extends BaseController {

    @RequestMapping(value = "/session/timeout", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BaseResponse timeout() {
        return super.timeout();
    }

}
