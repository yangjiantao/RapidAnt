/**
 * Copyright (c) Facebook, Inc. and its affiliates.
 * <p>
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.jiantao.utils.android;

/**
 *  See that class for more details. Used in
 * conjunction with {@link SoftAssertions}.
 */
public class AssertionException extends RuntimeException {

    public AssertionException(String detailMessage) {
        super(detailMessage);
    }
}
