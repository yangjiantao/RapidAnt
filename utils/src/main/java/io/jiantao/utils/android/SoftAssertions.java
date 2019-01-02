/**
 * Copyright (c) Facebook, Inc. and its affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.jiantao.utils.android;


import androidx.annotation.Nullable;

/**
 * 断言，不满足条件就抛出异常。
 */
public class SoftAssertions {

  /**
   * Throw {@link AssertionException} with a given message. Use this method surrounded with
   * {@code if} block with assert condition in case you plan to do string concatenation to produce
   * the message.
   */
  public static void assertUnreachable(String message) {
    throw new AssertionException(message);
  }

  /**
   * Asserts the given condition, throwing an {@link AssertionException} if the condition doesn't
   * hold.
   */
  public static void assertCondition(boolean condition, String message) {
    if (!condition) {
      throw new AssertionException(message);
    }
  }

  /**
   * Asserts that the given Object isn't null, throwing an {@link AssertionException} if it was.
   */
  public static <T> T assertNotNull(@Nullable T instance) {
    if (instance == null) {
      throw new AssertionException("Expected object to not be null!");
    }
    return instance;
  }
}
