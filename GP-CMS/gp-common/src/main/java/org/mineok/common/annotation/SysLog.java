
package org.mineok.common.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解:使用在Controller下
 *
 * @author Mark sunlightcs@gmail.com
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

	String value() default "";
}
