package ru.ildar.languagelearner.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.webflow.config.AbstractFlowConfiguration;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.mvc.builder.MvcViewFactoryCreator;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;
import org.springframework.webflow.security.SecurityFlowExecutionListener;

import java.util.Arrays;

/**
 * Configuration class for Spring WebFlow. */
@Configuration
public class WebFlowConfig extends AbstractFlowConfiguration
{

    @Autowired
    private ViewResolver viewResolver;

    @Bean
    public FlowHandlerAdapter flowHandlerAdapter()
    {
        FlowHandlerAdapter handlerAdapter = new FlowHandlerAdapter();
        handlerAdapter.setFlowExecutor(flowExecutor());
        handlerAdapter.setSaveOutputToFlashScopeOnRedirect(true);
        return handlerAdapter;
    }

    @Bean
    public FlowHandlerMapping flowHandlerMapping()
    {
        FlowHandlerMapping handlerMapping = new FlowHandlerMapping();
        handlerMapping.setOrder(-1);
        handlerMapping.setFlowRegistry(flowRegistry());
        return handlerMapping;
    }

	@Bean
	public FlowExecutor flowExecutor()
    {
		return getFlowExecutorBuilder(flowRegistry())
				.addFlowExecutionListener(new SecurityFlowExecutionListener(), "*")
				.build();
	}

	@Bean
	public FlowDefinitionRegistry flowRegistry()
    {
		return getFlowDefinitionRegistryBuilder(flowBuilderServices())
				//.setBasePath("classpath:/flows")
				//.addFlowLocationPattern("flows/**/*-flow.xml")
                .build();
	}

	@Bean
	public FlowBuilderServices flowBuilderServices()
    {
		return getFlowBuilderServicesBuilder()
				.setViewFactoryCreator(mvcViewFactoryCreator())
				.setValidator(validator())
				.setDevelopmentMode(true)
				.build();
	}

	@Bean
	public MvcViewFactoryCreator mvcViewFactoryCreator()
    {
		MvcViewFactoryCreator factoryCreator = new MvcViewFactoryCreator();
        factoryCreator.setViewResolvers(Arrays.<ViewResolver>asList(viewResolver));
		factoryCreator.setUseSpringBeanBinding(true);
		return factoryCreator;
	}

	@Bean
	public LocalValidatorFactoryBean validator() {
		return new LocalValidatorFactoryBean();
	}

}