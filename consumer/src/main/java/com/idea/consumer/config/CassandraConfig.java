package com.idea.consumer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractClusterConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;

import java.util.List;

@Configuration
public class CassandraConfig extends AbstractClusterConfiguration {

    private final String keyspace;
    private final String hosts;

    CassandraConfig(
            @Value("${spring.data.cassandra.keyspace-name}") String keyspace,
            @Value("${spring.data.cassandra.contact-points}") String hosts) {
        this.keyspace = keyspace;
        this.hosts = hosts;
    }

    @Bean
    @Override
    public CassandraClusterFactoryBean cluster() {

        RetryingCassandraClusterFactoryBean bean = new RetryingCassandraClusterFactoryBean();

        bean.setAddressTranslator(getAddressTranslator());
        bean.setAuthProvider(getAuthProvider());
        bean.setClusterBuilderConfigurer(getClusterBuilderConfigurer());
        bean.setClusterName(getClusterName());
        bean.setCompressionType(getCompressionType());
        bean.setContactPoints(getContactPoints());
        bean.setLoadBalancingPolicy(getLoadBalancingPolicy());
        bean.setMaxSchemaAgreementWaitSeconds(getMaxSchemaAgreementWaitSeconds());
        bean.setMetricsEnabled(getMetricsEnabled());
        bean.setNettyOptions(getNettyOptions());
        bean.setPoolingOptions(getPoolingOptions());
        bean.setPort(getPort());
        bean.setProtocolVersion(getProtocolVersion());
        bean.setQueryOptions(getQueryOptions());
        bean.setReconnectionPolicy(getReconnectionPolicy());
        bean.setRetryPolicy(getRetryPolicy());
        bean.setSpeculativeExecutionPolicy(getSpeculativeExecutionPolicy());
        bean.setSocketOptions(getSocketOptions());
        bean.setTimestampGenerator(getTimestampGenerator());

        bean.setKeyspaceCreations(getKeyspaceCreations());
        bean.setKeyspaceDrops(getKeyspaceDrops());
        bean.setStartupScripts(getStartupScripts());
        bean.setShutdownScripts(getShutdownScripts());

        return bean;
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        final CreateKeyspaceSpecification specification =
                CreateKeyspaceSpecification.createKeyspace(keyspace)
                        .ifNotExists()
                        .with(KeyspaceOption.DURABLE_WRITES, true)
                        .withSimpleReplication();
        return List.of(specification);
    }

    @Override
    protected String getContactPoints() {
        return hosts;
    }

    @Override
    protected boolean getMetricsEnabled() {
        return false;
    }
}

//@EnableCassandraRepositories(basePackages = "com.idea.consumer.repository")
//@Configuration
//public class CassandraConfig extends AbstractCassandraConfiguration {
//
//    @Value("${cassandra.contactpoints}")
//    private String contactPoints;
//
//    @Value("${cassandra.port}")
//    private int port;
//
//    @Value("${spring.data.cassandra.keyspace-name}")
//    private String keySpace;
//
//    @Value("${cassandra.basepackages}")
//    private String basePackages;
//
//    @Override
//    protected String getKeyspaceName() {
//        return keySpace;
//    }
//
//    @Override
//    protected String getContactPoints() {
//        return contactPoints;
//    }
//
//    @Override
//    protected int getPort() {
//        return port;
//    }
//
//    @Override
//    public SchemaAction getSchemaAction() {
//        return SchemaAction.CREATE_IF_NOT_EXISTS;
//    }
//
//    @Override
//    public String[] getEntityBasePackages() {
//        return new String[]{basePackages};
//    }
//
//    @Override
//    protected boolean getMetricsEnabled() {
//        return false;
//    }
//
//}
