package com.nicchagil.util.dubbo.consumer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.rpc.service.EchoService;
import com.nicchagil.dubbo.interfaces.user.UserDubboService;
import com.nicchagil.dubbo.model.user.UserDubboInput;
import com.nicchagil.dubbo.model.user.UserDubboOutput;
import com.nicchagil.util.exception.StandardResponse;

@RestController
@RequestMapping("/user/dubbo")
public class UserDubboConsumerController {
	
	@Resource(name = "userDubboService")
	private UserDubboService userDubboService;
	
	@GetMapping("/getByCriteria")
	public StandardResponse<UserDubboOutput> getByCriteria(HttpServletRequest request, Integer id, String name) {
		UserDubboOutput userDubboOutput = this.userDubboService.getByCriteria(new UserDubboInput(id, name));
		return StandardResponse.getSuccessResponse(userDubboOutput);
	}

	@GetMapping("/insert")
    public StandardResponse<UserDubboOutput> insert(Integer id, String name) {
		UserDubboOutput userDubboOutput = this.userDubboService.insert(new UserDubboInput(id, name));
		return StandardResponse.getSuccessResponse(userDubboOutput);
	}
	
	@GetMapping("/echo")
	public StandardResponse<Object> echo(HttpServletRequest request, Integer id, String name) {
		EchoService echoService = (EchoService) userDubboService;
		Object echoResult = echoService.$echo("OK");
		return StandardResponse.getSuccessResponse(echoResult);
	}

}
