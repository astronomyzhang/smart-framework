package org.smart4j.framework.annotation;

import java.lang.annotation.*;

/**
 *Aspect切面注解
 *@author Garwen
 *@date 2019-12-9 11:42
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    Class<? extends Annotation> value();
}
