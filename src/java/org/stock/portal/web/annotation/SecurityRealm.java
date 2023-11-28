package org.stock.portal.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.stock.portal.web.util.security.Realm;

@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.ANNOTATION_TYPE })
public @interface SecurityRealm {
    
    Class<? extends Realm> type();
    
    String[] parameters() default {};
	
}
