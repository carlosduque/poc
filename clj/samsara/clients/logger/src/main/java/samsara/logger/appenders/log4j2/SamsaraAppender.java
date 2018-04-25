package samsara.logger.appenders.log4j2;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicBoolean;

import samsara.logger.EventLogger;
import samsara.logger.EventLoggerBuilder;

import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.LogEvent;


@Plugin(name = "SamsaraAppender", category = "Core", elementType = "appender", printObject = true)
public class SamsaraAppender extends AbstractAppender
{
    private EventLogger eventLogger;
    private AtomicBoolean warnOnce = new AtomicBoolean(false);
    private AtomicBoolean sendToSamsara = new AtomicBoolean(true);

    protected SamsaraAppender(String name, Filter filter, Layout<? extends Serializable> layout, EventLoggerBuilder builder, boolean ignoreExceptions) 
    {
        super(name, filter, layout, ignoreExceptions);

        eventLogger = builder.build();

        if(!builder.sendToSamsara())
        {
            warnOnce.set(true);
            //override and log to console
            sendToSamsara.set(false);
        }
    }

    @PluginFactory
    public static SamsaraAppender createAppender(@PluginAttribute("name") String name,
                                                 @PluginAttribute("apiUrl") String apiUrl,
                                                 @PluginAttribute("sourceId") String sourceId,
                                                 @PluginAttribute("appId") String appId,
                                                 @PluginAttribute("publishInterval") String publishInterval,
                                                 @PluginAttribute("minBufferSize") String minBufferSize,
                                                 @PluginAttribute("maxBufferSize") String maxBufferSize,
                                                 @PluginAttribute("compression") String compression,
                                                 @PluginAttribute("serviceName") String serviceName,
                                                 @PluginAttribute("ignoreExceptions") boolean ignoreExceptions,
                                                 @PluginElement("Layout") Layout<? extends Serializable> layout,
                                                 @PluginElement("Filter") Filter filter)
    {
        if(name == null)
        {
            LOGGER.error("No name provided for SamsaraAppender");
            return null;
        }

        if(layout == null)
        {
            layout = PatternLayout.createDefaultLayout();
        }

        Long publishIntervalLong = (publishInterval == null ? null : Long.parseLong(publishInterval));
        Long minBufferSizeLong = (minBufferSize == null ? null : Long.parseLong(minBufferSize));
        Long maxBufferSizeLong = (maxBufferSize == null ? null : Long.parseLong(maxBufferSize));

        EventLoggerBuilder builder = new EventLoggerBuilder();
        builder = (apiUrl == null ? builder : (EventLoggerBuilder)builder.setApiUrl(apiUrl));
        builder = (sourceId == null ? builder : (EventLoggerBuilder)builder.setSourceId(sourceId));
        builder = (appId == null ? builder : (EventLoggerBuilder)builder.setAppId(appId));
        builder = (publishIntervalLong == null ? builder : (EventLoggerBuilder)builder.setPublishInterval(publishIntervalLong));
        builder = (minBufferSizeLong == null ? builder : (EventLoggerBuilder)builder.setMinBufferSize(minBufferSizeLong));
        builder = (maxBufferSizeLong == null ? builder : (EventLoggerBuilder)builder.setMaxBufferSize(maxBufferSizeLong));
        builder = (compression == null ? builder : (EventLoggerBuilder)builder.setCompression(compression));
        builder = (serviceName == null ? builder : (EventLoggerBuilder)builder.setServiceName(serviceName));

        return new SamsaraAppender(name, filter, layout, builder, ignoreExceptions);
    }

    private void printWarning()
    {
        System.err.println("****************************************************************");
        System.err.println("SAMSARA: The apiUrl property for Appender (log4j2.xml) has not been set");
        System.err.println("SAMSARA: The Appender will NOT send logs to Samsara");
        System.err.println("****************************************************************\n");
    }

    @Override
    public void append(LogEvent event)
    {
        if(warnOnce.getAndSet(false))
        {
            printWarning();
        }

        String message = new String(this.getLayout().toByteArray(event));

        if(sendToSamsara.get())
        {
            eventLogger.log4j2Event(event.getLevel(), message, event.getThrown());
        }
    }
}
