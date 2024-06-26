package cn.daben.beast.autoconfigure;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Justubborn
 * @since 2024/6/25
 */
@Slf4j
public abstract class AbstractAutoConfig {
    @PostConstruct
    public void init() {
        log.info("[Beast Starter] - Auto Configuration {} completed initialization.", this.getClass().getSimpleName());
    }
}
