package com.sain.test.JavaAMQ;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class Sender {

	private static final int SEND_NUMBER = 5;

	public static void main(String[] args){
		// 连接工厂，JMS使用它创建连接
		ConnectionFactory connectionFactory;
		// JMS客户端到JMS Provider的连接
		Connection connection = null;
		// 一个发送或接收消息的线程
		Session session;
		// 消息的目的地，消息发送给谁
		Destination destination;
		// 消息的发送者
		MessageProducer messageProducer;
		// 构造COnnectionFactory实例对象，此处采用ActiveMQ的实现jar
		connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, "tcp://192.168.1.166:61616");

		try {
			// 从工厂获得实例对象
			connection = connectionFactory.createConnection();
			// 启动
			connection.start();
			// 获取操作连接
			session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			//
			destination = new ActiveMQQueue("hello");
			// 得到消息生产者[发送者]
			messageProducer = session.createProducer(destination);
			// 设置不持久化，根据项目而定
			messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			
			for (int i = 1; i <= SEND_NUMBER; i++) {
				// 创建发送信息内容
				TextMessage textMessage = session.createTextMessage("信息" + i + "来自Java.");
				System.out.println("发送信息： 信息" + i + "来自Java.");
				// 信息发送者发送信息
				messageProducer.send(textMessage);
			}
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(null != connection){
					connection.close();
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

}
