package com.wantao.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.wantao.bean.Student;

/*
 *@author:wantao
 *@time:Jul 31, 2018 7:53:41 PM
 *@description:
 */
public class HibernateTest {
	SessionFactory sessionFactory = null;
	Configuration configuration = null;
	ServiceRegistry serviceRegistry = null;
	Transaction transaction = null;
	Session session = null;

	@Before
	public void init() {
		configuration = new Configuration().configure();
		serviceRegistry = configuration.getStandardServiceRegistryBuilder().build();
		sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
	}

	//@Test //hibernate一级缓存
	public void test() {
		// User user = new User("万涛", "123456");
		// session.save(user);
		// Student student=new Student();
		// student.setName("万晓飞");
		// student.setSex("女");
		// student.setRegDate(new Date());
		// session.save(student);

		// 1.验证hibernate１级缓存使访问数据库的频率减少了
		/*
		 * Student
		 * student1=session.get(Student.class,1);//通过get/load方法获取数据库的对象并放入到session缓存中，
		 * 此时发起select查询语句 System.out.println(student1); student1=null;//即使没有对象的引用该对象仍然存在
		 * Student
		 * student2=session.get(Student.class,1);//此时不会再使用select语句向数据库中查而是直接从session中拿
		 * System.out.println(student2);
		 */
		// System.out.println(student1==student2);

		// 2.验证session缓存与数据库中数据一致的问题
		/**
		 * flush():使数据库和缓存数据一致
		 * refresh():不管是否一致,都会发起select语句,判断缓存中数据和数据库中的数据是否一样,如果不一样,使缓存和数据库中的数据一致
		 * clear():清空缓存
		 */
		/*Student student = session.get(Student.class, 1);
		student.setName("王二");// 亲自数据库中的数据与session缓存的数据一致,如果不一致,则update
		 session.flush();// 在提交之前会自动调用flush()
		session.refresh(student);
		System.out.println(student);*/
		/*Student student1=session.get(Student.class, 1);
		System.out.println(student1);
		session.clear();//清空缓存
		//第二次从缓存中找不到,这会从数据库中拿
		Student student2=session.get(Student.class, 1);
		System.out.println(student2==student1);*/
	}
	//@Test //处理对象的状态:临时状态,持久状态,游离对象
	public void test1() {
		Student student3=new Student("万超1","男",new Date());//临时对象
		student3.setId(1);//设置游离状态对象的id不会报错,但是无效,hibernate会重构id.设置持久对象的id会报错
		//session.save(student3);//首先把student放入到缓存,然后发起insert　sql,持久状态:被session托管,被插入到数据库中
		session.delete(student3);//游离状态,如果没有操作会被垃圾回收
	}
	@Test //测试c3p0
	public void test2() {
		session.doWork(new Work() {
			
			public void execute(Connection connection) throws SQLException {
				System.out.println(connection);
			}
		});
	}

	@After
	public void destory() {
		transaction.commit();
		session.close();
		sessionFactory.close();
	}

}
