package com.fbee.modules.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface CallbackService {

    Object handleService(HttpServletRequest req) throws Exception;

    String process(String decode) throws Exception;
}
