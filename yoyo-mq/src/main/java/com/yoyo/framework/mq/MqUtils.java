package com.yoyo.framework.mq;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/***
 @Author:MrHuang
 @Date: 2019/6/28 18:23
 @DESC: TODO MQ发送消息Utils
 @VERSION: 1.0
 ***/
@Component
public class MqUtils {

    private static RocketMQTemplate rocketMQTemplate;

    private static String getDestination(String topic, String tags) {
        Assert.hasLength(topic, "topic not empty");
        return StringUtils.isBlank(tags) ? topic : topic + ":" + tags;
    }

    @Autowired
    private MqUtils(RocketMQTemplate rocketMQTemplate) {
        MqUtils.rocketMQTemplate = rocketMQTemplate;
    }

    /**
     * 同步发送消息
     * @param topic
     * @param payload
     * @return
     */
    public static SendResult syncSend(String topic, String tags, Object payload) {
       return rocketMQTemplate.syncSend(getDestination(topic, tags), payload);
    }

    /**
     * 同步发送消息
     * @param topic
     * @param payload
     * @return
     */
    public static SendResult syncSend(String topic, Object payload) {
        return syncSend(topic, null, payload);
    }

    /**
     * 异步发送消息
     * @param topic
     * @param payload
     * @param sendCallback
     */
    public static void asyncSend(String topic, String tags, Object payload, SendCallback sendCallback) {
        rocketMQTemplate.asyncSend(getDestination(topic, tags), payload, sendCallback);
    }

    /**
     * 异步发送消息
     * @param topic
     * @param payload
     * @param sendCallback
     */
    public static void asyncSend(String topic, Object payload, SendCallback sendCallback) {
        asyncSend(topic, null, payload, sendCallback);
    }

    /**
     * 同步发送延时消息
     * delayLevel : 0 表示不延时
     *              1 表示延时1s
     *              3 表示延时10s
     *              5 表示延时1m
     *              在服务器端（rocketmq-broker端）的属性配置文件中加入以下行：(下面是默认的)
     * messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     */
    public static SendResult syncSendDelay(String topic, String tags, Object payload, int delayLevel) {
        return rocketMQTemplate.syncSend(getDestination(topic, tags), MessageBuilder.withPayload(payload).build(),
                rocketMQTemplate.getProducer().getSendMsgTimeout(), delayLevel);
    }

    /**
     * 同步发送延时消息
     * delayLevel : 0 表示不延时
     *              1 表示延时1s
     *              3 表示延时10s
     *              5 表示延时1m
     *              在服务器端（rocketmq-broker端）的属性配置文件中加入以下行：(下面是默认的)
     * messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     */
    public static SendResult syncSendDelay(String topic, Object payload, int delayLevel) {
        return syncSendDelay(topic, null, payload, delayLevel);
    }


    /**
     * 异步发送延时消息
     * @param topic
     * @param payload
     * @param sendCallback
     * @param delayLevel
     */
    public static void asyncSendDelay(String topic, String tags,  Object payload, SendCallback sendCallback, int delayLevel) {
        rocketMQTemplate.asyncSend(getDestination(topic, tags), MessageBuilder.withPayload(payload).build(), sendCallback,
                rocketMQTemplate.getProducer().getSendMsgTimeout(), delayLevel);
    }

    /**
     * 异步发送延时消息
     * @param topic
     * @param payload
     * @param sendCallback
     * @param delayLevel
     */
    public static void asyncSendDelay(String topic, Object payload, SendCallback sendCallback, int delayLevel) {
        asyncSendDelay(topic, null, payload, sendCallback, delayLevel);
    }


    /**
     * 同步发送顺序消息
     * @param topic
     * @param payload
     * @param hashKey 为订单ID 或者 商品ID 等等
     * @return
     */
    public static SendResult syncSendOrderly(String topic, String tags, Object payload, String hashKey) {
        return rocketMQTemplate.syncSendOrderly(getDestination(topic, tags), payload, hashKey);
    }

    /**
     * 同步发送顺序消息
     * @param topic
     * @param payload
     * @param hashKey 为订单ID 或者 商品ID 等等
     * @return
     */
    public static SendResult syncSendOrderly(String topic, Object payload, String hashKey) {
        return syncSendOrderly(topic, null, payload, hashKey);
    }

    /**
     * 异步发送顺序消息
     * @param topic
     * @param payload
     * @param hashKey 为订单ID 或者 商品ID 等等
     * @param sendCallback
     * @return
     */
    public static void asyncSendOrderly(String topic, String tags, Object payload, String hashKey, SendCallback sendCallback) {
        rocketMQTemplate.asyncSendOrderly(getDestination(topic, tags), payload, hashKey, sendCallback);
    }

    /**
     * 异步发送顺序消息
     * @param topic
     * @param payload
     * @param hashKey 为订单ID 或者 商品ID 等等
     * @param sendCallback
     * @return
     */
    public static void asyncSendOrderly(String topic, Object payload, String hashKey, SendCallback sendCallback) {
       asyncSendOrderly(topic, null, payload, hashKey, sendCallback);
    }


    /**
     * 单向（Oneway）发送特点为只负责发送消息，不等待服务器回应且没有回调函数触发，
     * 即只发送请求不等待应答。此方式发送消息的过程耗时非常短，一般在微秒级别。
     * 适用于某些耗时非常短，但对可靠性要求并不高的场景，例如日志收集。
     * @param topic
     * @param tags
     * @param payload
     */
    public static void sendOneWay(String topic, String tags, Object payload) {
        rocketMQTemplate.sendOneWay(getDestination(topic, tags), payload);
    }

    /**
     * 单向（Oneway）发送特点为只负责发送消息，不等待服务器回应且没有回调函数触发，
     * 即只发送请求不等待应答。此方式发送消息的过程耗时非常短，一般在微秒级别。
     * 适用于某些耗时非常短，但对可靠性要求并不高的场景，例如日志收集。
     * @param topic
     * @param payload
     */
    public static void sendOneWay(String topic, Object payload) {
        sendOneWay(topic, null, payload);
    }

    /**
     * 单向有顺序（OnewayOrderly）发送特点为只负责发送消息，不等待服务器回应且没有回调函数触发，
     * 即只发送请求不等待应答。此方式发送消息的过程耗时非常短，一般在微秒级别。
     * 适用于某些耗时非常短，但对可靠性要求并不高的场景，例如日志收集。
     * @param topic
     * @param tags
     * @param payload
     */
    public static void sendOneWayOrderly(String topic, String tags, Object payload, String hashKey) {
        rocketMQTemplate.sendOneWayOrderly(getDestination(topic, tags), payload, hashKey);
    }

    /**
     * 单向有顺序（OnewayOrderly）发送特点为只负责发送消息，不等待服务器回应且没有回调函数触发，
     * 即只发送请求不等待应答。此方式发送消息的过程耗时非常短，一般在微秒级别。
     * 适用于某些耗时非常短，但对可靠性要求并不高的场景，例如日志收集。
     * @param topic
     * @param payload
     */
    public static void sendOneWayOrderly(String topic, Object payload, String hashKey) {
        sendOneWayOrderly(topic, null, payload, hashKey);
    }


    /**
     * 发送事务消息
     * @param txProducerGroup
     * @param topic
     * @param tags
     * @param payload
     * @param arg
     * @return
     */
    public static TransactionSendResult sendMessageInTransaction(String txProducerGroup, String topic, String tags, Object payload, Object arg) {
        return rocketMQTemplate.sendMessageInTransaction(txProducerGroup, getDestination(topic,tags),MessageBuilder.withPayload(payload).build(), arg);
    }
}
