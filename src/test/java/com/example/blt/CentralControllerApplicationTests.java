package com.example.blt;

import com.whalin.MemCached.MemCachedClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)//随机生成一个端口号
public class CentralControllerApplicationTests {

	@Autowired
	public MemCachedClient memCachedClient;

	@Resource
	private RedisTemplate<String,String> redisTemplate;
	@Test
	public void contextLoads() {
	}

	@Test
	public void test(){
		String value = "50738";
		if (value.contains(":")){
			value = value.substring(0,value.lastIndexOf(":"));
			System.out.println("value: "+value);
		}else {
			System.out.println("error");
		}

	}

	@Test
	public void test2(){
		String address = "central-console192.168.16.103";
//		memCachedClient.set(address,"0",new Date(100000));
		Object value = memCachedClient.get(address);
//		memCachedClient.delete(address);
		System.out.println("value: "+value);
	}

	@Test
	public void test3(){
//        System.out.println(IP.IP1.getValue());
//		redisTemplate.opsForValue().set("test","test",100000, TimeUnit.MILLISECONDS);
//		String key = redisTemplate.opsForValue().get("test");
//		System.out.println("key: "+key);

		Jedis jedis = new Jedis("127.0.0.1",6379);
		jedis.auth("Tp123456");
//		jedis.setex("test",10,"test");
		System.out.println(jedis.get("test"));
	}

}
