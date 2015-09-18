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
package org.sonatype.nexus.orient.entity;

import javax.annotation.Nonnull;

import org.sonatype.nexus.common.entity.EntityVersion;

import com.orientechnologies.orient.core.version.ORecordVersion;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Attached {@link EntityVersion}.
 *
 * @since 3.0
 */
public class AttachedEntityVersion
    implements EntityVersion
{
  private final EntityAdapter owner;

  private final ORecordVersion version;

  /**
   * Cached encoded value of version.
   */
  private volatile String encoded;

  public AttachedEntityVersion(final EntityAdapter owner, final ORecordVersion version) {
    this.owner = checkNotNull(owner);
    this.version = checkNotNull(version);
  }

  public ORecordVersion getVersion() {
    return version;
  }

  @Override
  @Nonnull
  public String getValue() {
    if (encoded == null) {
      checkState(!version.isTemporary(), "attempted use of temporary/uncommitted document version");
      encoded = version.toString();
    }
    return encoded;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    else if (o instanceof AttachedEntityVersion) {
      AttachedEntityVersion that = (AttachedEntityVersion)o;
      return version.equals(that.version);
    }
    else if (o instanceof EntityVersion) {
      EntityVersion that = (EntityVersion)o;
      return getValue().equals(that.getValue());
    }
    return false;
  }

  @Override
  public int hashCode() {
    return getValue().hashCode();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + '{' +
        owner.getSchemaType() + "->" +
        version +
        '}';
  }
}
