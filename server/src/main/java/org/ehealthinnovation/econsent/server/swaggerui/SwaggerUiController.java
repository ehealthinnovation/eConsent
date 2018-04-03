package org.ehealthinnovation.econsent.server.swaggerui;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.NullLogChute;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import java.io.StringWriter;

/**
 * Spring Controller the swagger UI.
 */
@Controller
@ApiIgnore
public class SwaggerUiController {

    private String myAppContextPath;

    /**
     * Render the index.html
     */
    @RequestMapping(value = {"/swagger-ui/", "/swagger-ui/index.html"}, produces = "text/html", method = RequestMethod.GET)
    @Timed
    @ExceptionMetered
    public
    @ResponseBody
    String index() throws Exception {
        VelocityEngine engine = new VelocityEngine();
        engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        engine.setProperty("classpath." + RuntimeConstants.RESOURCE_LOADER + ".class", ClasspathResourceLoader.class.getName());
        engine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, NullLogChute.class.getName());
        engine.setProperty(RuntimeConstants.VM_LIBRARY, "");
        engine.init();

        Template template = engine.getTemplate("/swagger-ui/index.html.vm");

        VelocityContext context = new VelocityContext();
        context.put("api_basepath", myAppContextPath);

        StringWriter w = new StringWriter();
        template.merge(context, w);
        return w.toString();
    }

    public void setAppContextPath(String theAppContextPath) {
        myAppContextPath = theAppContextPath;
    }
}
