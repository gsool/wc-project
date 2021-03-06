package com.nicchagil.util.mail.reliability;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nicchagil.orm.entity.MessageSendLog;
import com.nicchagil.orm.entity.MessageSendLogExample;
import com.nicchagil.orm.mapper.MessageSendLogExtraMapper;
import com.nicchagil.orm.mapper.MessageSendLogMapper;
import com.nicchagil.util.mail.reliability.send.MailSendService;

@Service
public class MailLogBatchSendService {
	
	@Autowired
	private MessageSendLogMapper messageSendLogMapper;
	
	@Autowired
	private MessageSendLogExtraMapper messageSendLogExtraMapper;
	
	@Autowired
	private MailSendService mailSendService;
	
	/**
	 * 新增
	 */
	public int insert(MessageSendLog record) {
		return this.messageSendLogMapper.insert(record);
	}
	
	/**
	 * 发送需要发送的此批次邮件
	 */
	public void sendRemainMail() {
		List<MessageSendLog> list = this.getSendMailList();
		
		for (MessageSendLog mailLog : list) {
			this.mailSendService.send(mailLog);
		}
	}
	
	/**
	 * 查询需要发送的此批次邮件列表
	 */
	public List<MessageSendLog> getSendMailList() {
		MessageSendLogExample e = new MessageSendLogExample();
		e.createCriteria().andStatusEqualTo("N").andTriesLessThan(3);
		e.setOrderByClause("id desc");
		
		List<MessageSendLog> list = this.messageSendLogMapper.selectByExample(e);
		return list;
	}
	
	/**
	 * 递增尝试次数（新事务）
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public int increaseTriesInNewTransaction(MessageSendLog record) {
		return this.messageSendLogExtraMapper.increaseTries(record);
	}

	/**
	 * 更新状态为成功（当前事务中）
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public int updateStatusInRequiredTransaction(MessageSendLog record) {
		return this.messageSendLogExtraMapper.updateStatus(record);
	}
	
	/**
	 * 更新异常（新事务）
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public int updateExceptionMsgInNewTransaction(MessageSendLog record) {
		MessageSendLog mailLog = new MessageSendLog();
		mailLog.setId(record.getId());
		mailLog.setExceptionMsg(record.getExceptionMsg());
		
		return this.messageSendLogMapper.updateByPrimaryKeySelective(record);
	}

}
