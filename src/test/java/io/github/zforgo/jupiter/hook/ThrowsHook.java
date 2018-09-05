package io.github.zforgo.jupiter.hook;

public class ThrowsHook {

	public void start() {
		System.out.println("Throws Hook");
		throw new RuntimeException("just because");
	}
}
