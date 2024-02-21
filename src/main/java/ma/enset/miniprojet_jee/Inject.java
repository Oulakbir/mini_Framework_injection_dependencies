package ma.enset.miniprojet_jee;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.METHOD})
public @interface Inject {
    /**
     * Optional value specifying the name of the dependency to inject.
     * @return the name of the dependency
     */
    String value() default "";

    /**
     * Optional flag indicating if the dependency is required.
     * @return true if the dependency is required, false otherwise
     */
    boolean required() default true;

    // Validation pour les éléments cibles appropriés
    String[] allowedTargets() default { "FIELD", "CONSTRUCTOR", "METHOD" };
}
