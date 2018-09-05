package io.github.zforgo.jupiter.hook;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@Hookable
class DemoHookTest {

	@Test
	@Hook(hook = DemoHook.class)
	void shouldCall() {
		assertTrue(true);
	}

	@Test
	@Hook(hook = DemoHook.class)
	@Hook(hook = SecondHook.class)
	void shouldRepeatableCall() {
		assertTrue(true);
	}

	@Test
	@Hook(hook = ThrowsHook.class)
	void shouldSkippedByException() {
		fail("It mustn't been run");
	}

	@Test
	@Hook(hook = HasNoMethodHook.class)
	void shouldSkippedByMethodParam() {
		fail("It mustn't been run");
	}
}
