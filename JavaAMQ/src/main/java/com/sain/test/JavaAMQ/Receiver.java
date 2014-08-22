package com.sain.test.JavaAMQ;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class Receiver {

	public static void main(String[] args) {
		// 连接工厂，JMS用它创建连接
		ConnectionFactory connectionFactory;
		// JMS客户端到JMS Provider的连接
		Connection connection = null;
		// 一个发送或接收信息的线程
		Session session;
		// 消息的目的地，消息发送给谁
		Destination destination;
		// 消费者，消息提供者
		MessageConsumer messageConsumer;
		// 创建COnnectionFactory实例
		connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, "tcp://192.168.1.166:61616");

		try {
			// 从连接工厂得到连接对象
			connection = connectionFactory.createConnection();
			// 
			connection.start();
			// 获得操作连接
			session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			// 
			destination = new ActiveMQQueue("hello");
			// 创建消息消费者
			messageConsumer = session.createConsumer(destination);

			while(Boolean.TRUE){
				Thread.sleep(1000);
				// 设置接收者接收信息的时间，为了便于测试，这里设置为100s
				TextMessage message = (TextMessage) messageConsumer.receive();
				if(null != message){
					System.out.println("收到信息： " + message.getText());
				} else {
					break;
				}
			}
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
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
