package fr.carbonit.model;

import org.immutables.value.Value;
import org.immutables.vavr.encodings.VavrEncodingEnabled;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PACKAGE, ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
@VavrEncodingEnabled
@Value.Style(depluralize = true)
public @interface ImmutableStyle {
}
