package io.github.zforgo.jupiter.hook;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.util.AnnotationUtils;
import org.junit.platform.commons.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.jupiter.api.extension.ConditionEvaluationResult.disabled;
import static org.junit.jupiter.api.extension.ConditionEvaluationResult.enabled;

public class HookExtension implements ExecutionCondition {
	private static final ConditionEvaluationResult ENABLED_BY_DEFAULT = enabled(
			"@Hook is not present");

	private static Function<Class<?>, Method> findHookMethod = c ->
			ReflectionUtils
					.findMethods(c, (m) -> m.getParameterCount() == 0).stream()
					.findFirst()
					.orElseThrow(() -> new IllegalArgumentException("Couldn't find desired hook method"));

	private static Consumer<Class<?>> callHook = c -> findHookMethod
			.andThen(m -> {
				try {
					return m.invoke(ReflectionUtils.newInstance(c));
				} catch (IllegalAccessException | InvocationTargetException e) {
					throw new HookExecutionException("Couldn't call Hook", e.getCause());
				}
			})
			.apply(c);

	@Override
	public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
		if (!context.getTestMethod().isPresent()) {
			return ENABLED_BY_DEFAULT;
		}
		try {
			AnnotationUtils.findRepeatableAnnotations(context.getRequiredTestMethod(), Hook.class)
					.forEach(h -> callHook.accept(h.hook()));
		} catch (HookExecutionException e) {
			return disabled("Got an Exception: " + e.getCause().getMessage());
		} catch (Exception e) {
			return disabled("Got an Exception: " + e.getMessage());
		}
		return enabled("");
	}
}
