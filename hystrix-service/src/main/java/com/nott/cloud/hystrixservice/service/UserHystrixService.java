package com.nott.cloud.hystrixservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import com.nott.cloud.hystrixservice.Result;
import com.nott.cloud.hystrixservice.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author zouwenlong
 * @Date 2022/7/11 20:21
 */

@Service
@Slf4j
public class UserHystrixService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${service-url.user-service}")
    private String url;

    /**
     * @HystrixCommand中的常用参数 fallbackMethod：指定服务降级处理方法；
     * ignoreExceptions：忽略某些异常，不发生服务降级；
     * commandKey：命令名称，用于区分不同的命令；
     * groupKey：分组名称，Hystrix会根据不同的分组来统计命令的告警及仪表盘信息；
     * threadPoolKey：线程池名称，用于划分线程池。
     * <p>
     * command key，代表了一类command，一般来说，代表了底层的依赖服务的一个接口
     * command group，代表了某一个底层的依赖服务，合理，一个依赖服务可能会暴露出来多个接口，每个接口就是一个command key
     * 举例：比如用户服务就是一个command group，用户服务里面有获取用户姓名、获取用户列表 这些都是不同的接口，就是不同的command key
     * command group，一般来说，推荐是根据一个服务去划分出一个线程池，command key默认都是属于同一个线程池的
     * <p>
     * command group，对应了一个服务，但是这个服务暴露出来的几个接口，访问量很不一样，差异非常之大，
     * 你可能就希望在这个服务command group内部，包含的对应多个接口的command key，做一些细粒度的资源隔离，
     * 对同一个服务的不同接口，都使用不同的线程池
     * 逻辑上来说，多个command key属于一个command group，在做统计的时候，会放在一起统计
     * 每个command key有自己的线程池，每个接口有自己的线程池，去做资源隔离和限流
     * 但是对于thread pool资源隔离来说，可能是希望能够拆分的更加一致一些，比如在一个功能模块内，对不同的请求可以使用不同的thread pool
     * 总结：command group一般来说，可以是对应一个服务，多个command key对应这个服务的多个接口，多个接口的调用共享同一个线程池
     * 如果说你的command key，要用自己的线程池，可以定义自己的threadpool key，就ok了
     */

    //调用方法与服务降级方法，方法上需要添加@HystrixCommand注解,当目标服务关闭后会自动触发降级方法getDefaultUser
    @HystrixCommand(fallbackMethod = "getDefaultUser", ignoreExceptions = NullPointerException.class)
    public Result getUser(Long id) {
        if (id == 1) {
            throw new NullPointerException(); //测试忽略异常
        }
        if (id == 2) {
            throw new IndexOutOfBoundsException(); //异常被处理，服务降级
        }
        return restTemplate.getForObject(url + "/userGet/{id}", Result.class, id);
    }

    public Result getDefaultUser(Long id) {
        return Result.data("default..." + id);
    }

    @HystrixCommand(fallbackMethod = "getDefaultUser", commandKey = "getUserCommand",
            groupKey = "getUserGroup", threadPoolKey = "getUserThreadPool")
    public Result getUserCommand(Long id) {
        return restTemplate.getForObject(url + "/userGet/{id}", Result.class, id);
    }

    //@CacheResult：开启缓存，默认所有参数作为缓存的key，cacheKeyMethod可以通过返回String类型的方法指定key
    @CacheResult(cacheKeyMethod = "getCacheKey")
    @HystrixCommand(fallbackMethod = "getDefaultUser", commandKey = "getCache")
    public Result getCacheUser(Long id) {
        log.info("getCache id" + id);
        return restTemplate.getForObject(url + "/userGet/{id}", Result.class, id);

    }

    public String getCacheKey(Long id) {
        log.info("getCacheKey....");
        return String.valueOf(id);
    }

    @CacheRemove(cacheKeyMethod = "getCacheKey", commandKey = "getCache")
    @HystrixCommand(fallbackMethod = "getDefaultUser", commandKey = "getCache")
    public Result getRemoveCacheUser(Long id) {
        log.info("removeche id" + id);
        return restTemplate.getForObject(url + "/userGet/{id}", Result.class, id);

    }

    //请求合并，对getUserFuture多次调用都会变成对batchMethod的value的单次调用
    @HystrixCollapser(batchMethod = "getUserByIds", collapserProperties = {
            @HystrixProperty(name = "timerDelayInMilliseconds", value = "100") //将间隔100ms的请求合并
    })
    public Future<User> getUserFuture(Long id) {
        return new AsyncResult<User>() {
            @Override
            public User invoke() {
                User result = restTemplate.getForObject(url + "/getUser/{id}", User.class, id);
                log.info("get obj from: " + id);
                return result;
            }
        };
    }

    @HystrixCommand
    public List<User> getUserByIds(List<Long> ids) {
        log.info("get from:" + ids);
        StringBuffer sb = new StringBuffer();
        if (!CollectionUtils.isEmpty(ids)) {
            if (ids.size() > 1) {
                for (Long id : ids) {
                    sb.append(id);
                    sb.append(",");
                }
            } else {
                sb.append(ids.get(0));
            }
            if (sb.lastIndexOf(",") > 1) {
                sb.deleteCharAt(sb.lastIndexOf(","));
            }
        }
        String str = sb.toString();

        return restTemplate.getForObject(url + "/userGetByIds/{str}", List.class, str);
    }
}
