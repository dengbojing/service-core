package com.yichen.api;

import com.yichen.core.param.file.FileParam;
import com.yichen.major.manager.PhotoFixManager;
import com.yichen.major.service.AccountService;
import com.yichen.major.service.AppKeyService;
import com.yichen.request.RequestHolder;
import com.yichen.response.CommonResponse;
import com.yichen.util.OpenApiStatus;
import lombok.val;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dengbojing
 */
@RestController
@RequestMapping("/api/v1")
public class OpenApi {
    private final static String  TOKEN = "token";
    private final static String  TIME_SPAN = "timeSpan";
    private final static String APP_KEY = "appKey";

    private final PhotoFixManager photoFixManager;

    private final AppKeyService appKeyService;

    public OpenApi(PhotoFixManager photoFixManager, AppKeyService appKeyService) {
        this.photoFixManager = photoFixManager;
        this.appKeyService = appKeyService;
    }

    /**
     *
     * @param param 校验参数(token, appeky, timestamp)
     * @return
     * @throws Exception
     */
    @PostMapping("/file")
    public CommonResponse<?> uploadFile(@RequestBody FileParam param) throws Exception {
        CommonResponse response;
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if (null != requestAttributes) {
            request = (HttpServletRequest) (requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST));
        }
        String token = request.getHeader(TOKEN);
        String timeSpan = request.getHeader(TIME_SPAN);
        String appKey = request.getHeader(APP_KEY);
        OpenApiStatus apiStatus = appKeyService.check(appKey,token,Long.parseLong(timeSpan));
        if(apiStatus.equals(OpenApiStatus.OK)){
            String userId = appKeyService.getAccountId(appKey);
            response = CommonResponse.success(photoFixManager.fixPhoto(param,userId));
        }else{
            response = new CommonResponse();
            response.setCode(apiStatus.getCode());
            response.setMessage(apiStatus.getDesc());
        }
        return response;
    }
}
