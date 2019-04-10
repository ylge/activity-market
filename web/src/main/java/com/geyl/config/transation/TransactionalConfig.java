package com.geyl.config.transation;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Collections;

/**
 * @author geyl
 * @date 2018/1/30.
 * 事务处理自动注解无需手动
 */
@Configuration
public class TransactionalConfig {

    private static final String AOP_POINTCUT_EXPRESSION = "execution(* com.geyl.*..*.*(..))";

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public TransactionInterceptor txAdvice() {

        RuleBasedTransactionAttribute readOnly = this.readOnlyTransactionRule();
        RuleBasedTransactionAttribute required = this.requiredTransactionRule();

        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();

        source.addTransactionalMethod("save*", required);
        source.addTransactionalMethod("insert*", required);
        source.addTransactionalMethod("add*", required);
        source.addTransactionalMethod("delete*", required);
        source.addTransactionalMethod("update*", required);
        source.addTransactionalMethod("edit*", required);
        source.addTransactionalMethod("exec*", required);
        source.addTransactionalMethod("set*", required);
        source.addTransactionalMethod("get*", readOnly);
        source.addTransactionalMethod("query*", readOnly);
        source.addTransactionalMethod("find*", readOnly);
        source.addTransactionalMethod("list*", readOnly);
        source.addTransactionalMethod("count*", readOnly);
        source.addTransactionalMethod("is*", readOnly);

        return new TransactionInterceptor(transactionManager, source);
    }

    @Bean
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }

    /**
     * 支持当前事务;如果不存在创建一个新的
     * {@link org.springframework.transaction.annotation.Propagation#REQUIRED}
     */
    private RuleBasedTransactionAttribute requiredTransactionRule() {
        RuleBasedTransactionAttribute required = new RuleBasedTransactionAttribute();
        required.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        required.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        required.setTimeout(TransactionDefinition.TIMEOUT_DEFAULT);
        return required;
    }

    /**
     * 只读事务
     * {@link org.springframework.transaction.annotation.Propagation#NOT_SUPPORTED}
     */
    private RuleBasedTransactionAttribute readOnlyTransactionRule() {
        RuleBasedTransactionAttribute readOnly = new RuleBasedTransactionAttribute();
        readOnly.setReadOnly(true);
        readOnly.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
        return readOnly;
    }
}
