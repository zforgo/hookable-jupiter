/*
 * Copyright 2015-2018 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v20.html
 */

package io.github.zforgo.jupiter.hook;

import org.junit.platform.commons.JUnitException;

/**
 * Thrown if an error is encountered while executing a
 * {@link Hook}.
 *
 * @see Hook
 */
class HookExecutionException extends JUnitException {

	private static final long serialVersionUID = 1L;

	public HookExecutionException(String message, Throwable cause) {
		super(message, cause);
	}

}
