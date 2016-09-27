/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.JobBuilder.newJob;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import org.quartz.Trigger;
import static org.quartz.TriggerBuilder.newTrigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author Ahmed
 */
@WebServlet(name="QuartzMain", loadOnStartup = 1)
public class QuartzMain extends HttpServlet{

    
    
    @Override
    public void init() throws ServletException {
        try {            
            // Grab the Scheduler instance from the Factory
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // and start it off
            scheduler.start();
            
            //.....QUICKTELLER.....QUICKTELLER.....QUICKTELLER
            // define the job and tie it to our HelloJob class
            JobDetail job = newJob(TaskBean.class)
                    .withIdentity("jobserv", "jbgroup1")
                    .build();

            // Trigger the job to run now, and then repeat every 60 seconds
            Trigger trigger = newTrigger()
                    .withIdentity("jbtrigger1", "jbgroup1")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(90)
                            .repeatForever())
                    .build();
            
            //.....SEND JOB........SEND JOB....SEND JOB.....            
            // define another job and tie it to our sendSMS class
            JobDetail sendjob = newJob(SendBean.class)
                    .withIdentity("sendjob", "sendjobgroup")
                    .build();
            
            // Trigger the job to run now, and then repeat every 60 seconds
            Trigger sendTrigger = newTrigger()
                    .withIdentity("sendTrigger", "sendjobgroup")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(60)
                            .repeatForever())
                    .build();
            
            //.....EMAIL JOB........EMAIL JOB....EMAIL JOB.....            
            // define another job and tie it to our sendEmail class
            JobDetail emailjob = newJob(EmailBean.class)
                    .withIdentity("emailjob", "emailjobgroup")
                    .build();
            
            // Trigger the job to run now, and then repeat every 60 seconds
            Trigger emailTrigger = newTrigger()
                    .withIdentity("emailTrigger", "emailjobgroup")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(180)
                            .repeatForever())
                    .build();
            
            
            //SCHEDULING......SCHEDULING........SCHEDULING
            // define another job and tie it to our sendSMS class
            JobDetail schedulejob = newJob(ScheduleBean.class)
                    .withIdentity("schedulejob", "schedulejobgroup")
                    .build();
            
            // Trigger the job to run now, and then repeat every 60 seconds
            Trigger scheduleTrigger = newTrigger()
                .withIdentity("trigger3", "schedulejobgroup")
                .startNow()
                .withSchedule(dailyAtHourAndMinute(10, 0)) // fire every day at 10:00
//                    .withSchedule(simpleSchedule()
//                            .withIntervalInSeconds(60)
//                            .repeatForever())
                .build();
            
                
            
            // Tell quartz to schedule the job using our trigger
            scheduler.scheduleJob(job, trigger);
            scheduler.scheduleJob(sendjob, sendTrigger);
            scheduler.scheduleJob(schedulejob, scheduleTrigger);
            scheduler.scheduleJob(emailjob, emailTrigger);

            //scheduler.shutdown();

        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }

    

    
 
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
      
      
}