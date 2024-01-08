package org.fxkc.common.job.config;

import cn.hutool.core.collection.CollUtil;
import org.fxkc.common.core.utils.StreamUtils;
import org.fxkc.common.core.utils.StringUtils;
import org.fxkc.common.job.config.properties.PowerJobProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import tech.powerjob.common.utils.CommonUtils;
import tech.powerjob.common.utils.NetUtils;
import tech.powerjob.worker.PowerJobSpringWorker;
import tech.powerjob.worker.common.PowerJobWorkerConfig;

import java.util.Arrays;
import java.util.List;

/**
 * Autoconfiguration class for PowerJob-worker.
 *
 * @author songyinyin
 * @since 2020/7/26 16:37
 */
@AutoConfiguration
@EnableConfigurationProperties(PowerJobProperties.class)
@ConditionalOnProperty(prefix = "powerjob.worker", name = "enabled", havingValue = "true", matchIfMissing = true)
public class PowerJobConfig{

    @Bean
    public PowerJobSpringWorker initPowerJob(PowerJobProperties properties, DiscoveryClient discoveryClient) {

        PowerJobProperties.Worker worker = properties.getWorker();

        /*
         * Address of PowerJob-server node(s). Do not mistake for ActorSystem port. Do not add
         * any prefix, i.e. http://.
         */
        List<String> serverAddress;
        if (StringUtils.isNotBlank(worker.getServerName())) {
            List<ServiceInstance> instances = discoveryClient.getInstances(worker.getServerName());
            if (CollUtil.isEmpty(instances)) {
                throw new RuntimeException("调度中心不存在!");
            }
            serverAddress = StreamUtils.toList(instances, instance ->
                String.format("%s:%s", instance.getHost(), instance.getPort()));
        } else {
            CommonUtils.requireNonNull(worker.getServerAddress(), "serverAddress can't be empty! " +
                "if you don't want to enable powerjob, please config program arguments: powerjob.worker.enabled=false");
            serverAddress = Arrays.asList(worker.getServerAddress().split(","));
        }
        /*
         * Create OhMyConfig object for setting properties.
         */
        PowerJobWorkerConfig config = new PowerJobWorkerConfig();
        /*
         * Configuration of worker port. Random port is enabled when port is set with non-positive number.
         */
        if (worker.getPort() != null) {
            config.setPort(worker.getPort());
        } else {
            int port = worker.getAkkaPort();
            if (port <= 0) {
                port = NetUtils.getRandomPort();
            }
            config.setPort(port);
        }
        /*
         * appName, name of the application. Applications should be registered in advance to prevent
         * error. This property should be the same with what you entered for appName when getting
         * registered.
         */
        config.setAppName(worker.getAppName());
        config.setServerAddress(serverAddress);
        config.setProtocol(worker.getProtocol());
        /*
         * For non-Map/MapReduce tasks, {@code memory} is recommended for speeding up calculation.
         * Map/MapReduce tasks may produce batches of subtasks, which could lead to OutOfMemory
         * exception or error, {@code disk} should be applied.
         */
        config.setStoreStrategy(worker.getStoreStrategy());
        /*
         * When enabledTestMode is set as true, PowerJob-worker no longer connects to PowerJob-server
         * or validate appName.
         */
        config.setAllowLazyConnectServer(worker.isAllowLazyConnectServer());
        /*
         * Max length of appended workflow context . Appended workflow context value that is longer than the value will be ignored.
         */
        config.setMaxAppendedWfContextLength(worker.getMaxAppendedWfContextLength());

        config.setTag(worker.getTag());

        config.setMaxHeavyweightTaskNum(worker.getMaxHeavyweightTaskNum());

        config.setMaxLightweightTaskNum(worker.getMaxLightweightTaskNum());

        config.setHealthReportInterval(worker.getHealthReportInterval());
        /*
         * Create PowerJobSpringWorker object and set properties.
         */
        return new PowerJobSpringWorker(config);
    }

}
