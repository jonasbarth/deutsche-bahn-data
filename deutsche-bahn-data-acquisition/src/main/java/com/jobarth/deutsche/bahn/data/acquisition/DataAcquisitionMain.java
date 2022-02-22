package com.jobarth.deutsche.bahn.data.acquisition;

import com.google.common.collect.Lists;
import com.jobarth.deutsche.bahn.data.acquisition.jobs.TimetableNeo4JWriterJob;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Collection;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
@ComponentScan({"com.jobarth.deutsche.bahn.data.acquisition", "com.jobarth.deutsche.bahn.data.db"})
public class DataAcquisitionMain {

    public static void main(String[] args) {
        SpringApplication.run(DataAcquisitionMain.class, args);
    }

    @Bean
    CommandLineRunner run(Collection<String> evas) {
        return args -> {
            Collection<TimetableService> services = Lists.newArrayList();
            for (String evaNo : evas) {
                DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
                GenericBeanDefinition gbd = new GenericBeanDefinition();
                gbd.setBeanClass(QuartzTimetableService.class);

                MutablePropertyValues mpv = new MutablePropertyValues();
                mpv.add("eva", evaNo);
                mpv.add("timetableManager", ApplicationContextProvider.getApplicationContext().getBean(TimetableManagerImpl.class));
                mpv.add("neo4jJob", ApplicationContextProvider.getApplicationContext().getBean(TimetableNeo4JWriterJob.class));
                //mpv.add("schedulerFactoryBean", ApplicationContextProvider.getApplicationContext().getBean(SchedulerFactoryBean.class));
                gbd.setPropertyValues(mpv);
                beanFactory.registerBeanDefinition(evaNo, gbd);

                services.add(beanFactory.getBean(evaNo, QuartzTimetableService.class));
            }
            services.stream().forEach(TimetableService::start);

            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler(); //ApplicationContextProvider.getApplicationContext().getBean(SchedulerFactoryBean.class).getScheduler();
            scheduler.start();
        };
    }
}
