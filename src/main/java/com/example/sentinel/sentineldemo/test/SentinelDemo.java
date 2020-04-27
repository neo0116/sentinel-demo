package com.example.sentinel.sentineldemo.test;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName SentinelDemo
 * @Description TODO
 * @Version 1.0
 **/
public class SentinelDemo {

    private static final String resouce = "doTest";

    public static void initFlowRules() {
        List<FlowRule> flowRules = new ArrayList<>();
        //流量规则
        FlowRule flowRule = new FlowRule();
        //受保护的资源
        flowRule.setResource(resouce);
        //流量限制规则
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        //每秒访问数
        flowRule.setCount(10);

        flowRules.add(flowRule);

        FlowRuleManager.loadRules(flowRules);
    }

    public static void main(String[] args) {
        initFlowRules();

        while (true) {
            Entry entry = null;
            try {
                entry = SphU.entry(resouce);
            } catch (BlockException e) {
                e.printStackTrace();
            } finally {
                if (Objects.nonNull(entry)) {
                    entry.close();
                }
            }
        }

    }

}
