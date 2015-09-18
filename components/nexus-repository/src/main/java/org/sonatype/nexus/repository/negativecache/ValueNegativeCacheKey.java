/*
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2008-present Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */
package org.sonatype.nexus.repository.negativecache;

import static com.google.common.base.Preconditions.checkNotNull;

// TODO: implement Externalizable

/**
 * A simple value based {@link NegativeCacheKey}.
 *
 * @since 3.0
 */
public class ValueNegativeCacheKey
    implements NegativeCacheKey
{
  private final String value;

  public ValueNegativeCacheKey(final String value) {
    this.value = checkNotNull(value);
  }

  /**
   * @param key child key
   * @return false
   */
  @Override
  public boolean isParentOf(final NegativeCacheKey key) {
    checkNotNull(key);
    return false;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ValueNegativeCacheKey that = (ValueNegativeCacheKey) o;

    return value.equals(that.value);
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "value='" + value + '\'' +
        '}';
  }

}
