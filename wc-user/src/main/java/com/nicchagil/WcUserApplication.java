package com.nicchagil;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.nicchagil.designpattern.strategy.spring.UseStrategyService;
import com.nicchagil.designpattern.strategy.spring.UseStrategyService.SomethingEnum;
import com.nicchagil.util.log.test.LogPrintTest;
import com.nicchagil.util.spring.ApplicationContextUtils;

@SpringBootApplication
@ComponentScan(basePackages="com.nicchagil")
@MapperScan("com.nicchagil.orm.mapper")
@EnableScheduling
public class WcUserApplication {
	
	private static Logger logger = LoggerFactory.getLogger(WcUserApplication.class);

	/**
	 * 启动应用
	 * @param args 0:配置dubbo的监听端口
	 */
	public static void main(String[] args) {
		ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(WcUserApplication.class, args);
		
		/* 以下为调试日志打印 */
		logger.debug("启动完毕...");
		logger.info("启动完毕...");
		logger.error("启动完毕...（测试错误日志打印）");
		LogPrintTest.printLogInSpecialPackage();
		
		/* 各地址打印 */
		logger.error("Druid console : {}", "/druid/");
		
		/* 测试策略模式 */
		UseStrategyService useStrategyService = ApplicationContextUtils.getBean(UseStrategyService.class);
		useStrategyService.useStrategyPatternDemo(SomethingEnum.BALL);
	}
}
