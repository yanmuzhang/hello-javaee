package network.call.chain;

import network.call.chain.header.HttpUrlConnectionHeaderInsert;
import network.call.chain.header.UrlConnectionHeaderInsert;

import java.lang.instrument.Instrumentation;

/**
 * @author: sufeng
 * @create: 2020-01-15 15:03
 */
public class Agent {
    public static Instrumentation instrumentation;
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("=========开始执行premain============");
        Agent.instrumentation = inst;
        SimpleClassTransformer transformer = new SimpleClassTransformer();
        inst.addTransformer(transformer);
//        inst.addTransformer(new UrlConnectionHeaderInsert());
//        inst.addTransformer(new HttpUrlConnectionHeaderInsert());
    }
}
