package com.fbee.modules.interceptor;

import com.fbee.modules.bean.Constants;
import com.fbee.modules.bean.MemberBean;
import com.fbee.modules.consts.ErrorCode;
import com.fbee.modules.consts.RedisKey;
import com.fbee.modules.core.utils.SessionUtils;
import com.fbee.modules.interceptor.anno.Auth;
import com.fbee.modules.interceptor.anno.Guest;
import com.fbee.modules.jsonData.basic.JsonResult;
import com.fbee.modules.redis.JedisTemplate;
import com.fbee.modules.redis.JedisUtils;
import com.fbee.modules.utils.JsonUtils;
import com.fbee.modules.wechat.bean.WechatUserinfoBean;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

@Aspect
public class AuthInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);


    private JedisTemplate redis = JedisUtils.getJedisTemplate();

    @Pointcut("execution(public * com.fbee.modules.controller..*.*(..))")
    public void point() {
        log.info("aop init");
    }

    @Around("point()")
    public Object aroundAdvice(ProceedingJoinPoint joinpoint) throws Throwable {
        Method m = ((MethodSignature) joinpoint.getSignature()).getMethod();
        boolean guest = m.isAnnotationPresent(Guest.class);
        boolean auth = m.isAnnotationPresent(Auth.class);

        log.info(String.format("method : [%s], token[%s]", m.getName(), SessionUtils.getHeaderValue(Constants.AUTH_KEY.TOKEN)));
        String referer = SessionUtils.getHeaderValue(Constants.AUTH_KEY.REFERER);

        String subhost = Constants.MOBILE_HOST_NAME.substring(0, Constants.MOBILE_HOST_NAME.indexOf('.'));

        String weChatJson = null;
        String userJson = null;
        if(StringUtils.isNotBlank(SessionUtils.getHeaderValue(Constants.AUTH_KEY.TOKEN))){
            weChatJson = redis.get(RedisKey.User.WECHAT_INFO.getKey());
        }
        if(StringUtils.isNotBlank(SessionUtils.getHeaderValue(Constants.AUTH_KEY.UID))){
            userJson = redis.get(RedisKey.User.USER_INFO.getKey());
        }

        MemberBean memberBean = null;
        if (StringUtils.isNotBlank(userJson) && StringUtils.isNotBlank(referer) && referer.startsWith(subhost)) {
        //if (StringUtils.isNotBlank(userJson)) {
            WechatUserinfoBean wechat = JsonUtils.fromJson(weChatJson, WechatUserinfoBean.class);
            memberBean = JsonUtils.fromJson(userJson, MemberBean.class);
            if (memberBean != null) {
                if(StringUtils.isBlank(memberBean.getOpenId())){
                    memberBean.setOpenId(wechat.getOpenid());
                }
                log.info("set user to session : " + userJson);
                SessionUtils.getSession().setAttribute(Constants.MEMBER_SESSION, memberBean);
            }
        }

        //若请求方法允许游客访问，则不验证
        //如果不是手机端微信访问，则不验证
        if (guest || StringUtils.isBlank(referer) || !referer.startsWith(subhost)) {
            log.info("guest method, skipping |0|"+referer);
            log.info("guest method, skipping |1|"+userJson);
            log.info("guest method, skipping |2|"+JsonUtils.toJson(memberBean));
            return joinpoint.proceed();
        }

        //如果auth授权访问，则需要有token，并且有微信用户信息
        if (auth) {
            if (StringUtils.isBlank(weChatJson)) {
                log.info("auth visit , check token and wechat auth info");
                JsonResult jr = JsonResult.failure(ErrorCode.WX_AUTH_ERROR);
                return jr;
            }
            log.info("auth method, skipping");
            return joinpoint.proceed();
        }

        //没有注解，则默认必须用户登陆访问
        if (StringUtils.isBlank(userJson)) {
            log.info("login visit , check token and user login info1");
            JsonResult jr = JsonResult.failure(ErrorCode.USER_NEED_LOGIN);
            return jr;
        }

        if(memberBean != null){
            log.info(String.format("%s , %s , %s", memberBean.getId(), SessionUtils.getHeaderValue(Constants.AUTH_KEY.UID), SessionUtils.getHeaderValue(Constants.AUTH_KEY.UID).equals(memberBean.getId().toString())));
        }
        if (memberBean == null || !SessionUtils.getHeaderValue(Constants.AUTH_KEY.UID).equals(memberBean.getId().toString())) {
            log.info("login visit , check uid and user login info2");
            JsonResult jr = JsonResult.failure(ErrorCode.USER_NEED_LOGIN);
            return jr;
        }

        return joinpoint.proceed();
    }

}
