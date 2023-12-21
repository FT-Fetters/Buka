package xyz.ldqc.buka.receiver.server.handler.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Fetters
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestHandlerClass {

  String path();

  String method();

}
