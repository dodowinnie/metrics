package com.brandon.metrics.gateway.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brandon.metrics.gateway.annotation.JwtCheck;
import com.brandon.metrics.gateway.dto.ReturnData;
import com.brandon.metrics.gateway.dto.UserDTO;
import com.brandon.metrics.gateway.jwt.JwtModel;
import com.brandon.metrics.gateway.jwt.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/auth")
public class AccountController {
	
	@Value("${mygateway.effective-time}")
	private String effectiveTime;
	
	private ObjectMapper objectMapper;
	
	public AccountController(ObjectMapper objectMapper) {
		super();
		this.objectMapper = objectMapper;
	}



	/**
	 * 登陆认证接口
	 * @param userDTO
	 * @return
	 */
	@PostMapping("/login")
	public ReturnData<String> login(@RequestBody UserDTO userDTO) throws Exception {
	    ArrayList<String> roleIdList = new ArrayList<>(1);
	    roleIdList.add("role_test_1");
	    JwtModel jwtModel = new JwtModel("test", roleIdList);
	    int effectivTimeInt = Integer.valueOf(effectiveTime.substring(0,effectiveTime.length()-1));
	    String effectivTimeUnit = effectiveTime.substring(effectiveTime.length()-1,effectiveTime.length());
	    String jwt = null;
	    switch (effectivTimeUnit){
	        case "s" :{
	            //秒
	            jwt = JwtUtil.createJwt("test", "test", objectMapper.writeValueAsString(jwtModel), effectivTimeInt * 1000L);
	            break;
	        }
	        case "m" :{
	            //分钟
	            jwt = JwtUtil.createJwt("test", "test", objectMapper.writeValueAsString(jwtModel), effectivTimeInt * 60L * 1000L);
	            break;
	        }
	        case "h" :{
	            //小时
	            jwt = JwtUtil.createJwt("test", "test", objectMapper.writeValueAsString(jwtModel), effectivTimeInt * 60L * 60L * 1000L);
	            break;
	        }
	        case "d" :{
	            //小时
	            jwt = JwtUtil.createJwt("test", "test", objectMapper.writeValueAsString(jwtModel), effectivTimeInt * 24L * 60L * 60L * 1000L);
	            break;
	        }
	    }
	    return new ReturnData<String>(200,"认证成功",jwt);
	}
	
	/**
     * 为授权提示
     */
    @GetMapping("/unauthorized")
    public ReturnData<String> unauthorized(){
        return new ReturnData<String> (403,"未认证,请重新登陆",null);
    }

    /**
     * jwt 检查注解测试 测试
     * @return
     */
    @GetMapping("/testJwtCheck")
    @JwtCheck
    public ReturnData<String> testJwtCheck(@RequestHeader("Authorization")String token,@RequestParam("name")@Valid String name){

        return new ReturnData<String>(200,"请求成功咯","请求成功咯"+name);

    }
}
