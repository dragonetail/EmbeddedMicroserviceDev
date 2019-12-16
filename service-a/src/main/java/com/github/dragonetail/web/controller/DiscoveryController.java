package com.github.dragonetail.web.controller;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.github.dragonetail.service.AuthPocService;
import com.github.dragonetail.service.Hello2Service;
import com.github.dragonetail.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

/**
 * @author: 开发者姓名
 * @createDate: 2019-12-13 15:08
 * @description:
 */
@RestController
@RequestMapping("discovery")
public class DiscoveryController {

    @NacosInjected
    private NamingService namingService;

    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Hello2Service hello2Service;

    @GetMapping(value = "/all")
    @ResponseBody
    public List<Instance> all(@RequestParam String serviceName) throws NacosException {
        return namingService.getAllInstances(serviceName);
    }

    @GetMapping(value = "/get")
    @ResponseBody
    public List<Instance> get(@RequestParam String serviceName) throws NacosException {
        return namingService.getAllInstances(serviceName);
    }

    @GetMapping("/b/secret1")
    public String echoAppName() {
//        //使用 LoadBalanceClient 和 RestTemolate 结合的方式来访问
//        ServiceInstance serviceInstance = loadBalancerClient.choose("service-provider");
//        String url = String.format("http://%s:%s/echo/%s",serviceInstance.getHost(),serviceInstance.getPort(),"test");
//        System.out.println("request url:"+url);
//        return restTemplate.getForObject(url,String.class);

        try {
            Instance serviceInstance = namingService.selectOneHealthyInstance("service-b", "DEFAULT_GROUP");

            String url = String.format("http://%s:%s/service-b/secret", serviceInstance.getIp(), serviceInstance.getPort());
            System.out.println("request url:" + url);
            return restTemplate.getForObject(url, String.class);

        } catch (NacosException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }


    @GetMapping(value = "/b/secret2")
    @ResponseBody
    public String helloSecret() {

        try {
            Instance serviceInstance = namingService.selectOneHealthyInstance("service-b", "DEFAULT_GROUP");

            String url = String.format("http://%s:%s", serviceInstance.getIp(), serviceInstance.getPort());
            URI determinedBasePathUri = URI.create(url);

            return hello2Service.helloSecret(determinedBasePathUri);
        } catch (NacosException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

}