package com.study.annotation;

import java.lang.annotation.*;

/**
 * 參考:https://blog.csdn.net/yb546822612/article/details/88116654
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestAOPOrderAnnotation {
}
