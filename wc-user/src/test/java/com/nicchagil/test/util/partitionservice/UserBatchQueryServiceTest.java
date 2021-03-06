package com.nicchagil.test.util.partitionservice;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.nicchagil.util.partitionservice.demo.service.UserBatchQueryService;
import com.nicchagil.util.partitionservice.demo.service.UserService.User;
import com.nicchagil.util.spring.ApplicationContextUtils;
import com.nicchagil.util.test.BaseSpringBootTest;

public class UserBatchQueryServiceTest extends BaseSpringBootTest {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	public void getByIdListWithPartitionQueryTest() {
		List<Integer> inputList = Lists.newArrayList();
		for (int i = 1; i <= 5087; i++) {
			inputList.add(i);
		}
		
		UserBatchQueryService service = ApplicationContextUtils.getBean(UserBatchQueryService.class);
		List<User> userList = service.getByIdListWithPartitionQuery(inputList);
		this.logger.info("userList : {}", userList);
	}
	
}
