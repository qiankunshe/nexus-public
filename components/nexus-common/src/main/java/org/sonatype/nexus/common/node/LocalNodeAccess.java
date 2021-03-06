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
package org.sonatype.nexus.common.node;

import java.security.cert.Certificate;

import org.sonatype.goodies.lifecycle.Lifecycle;

// TODO: rename to LocalNodeIdentity ?

/**
 * Provides access to the certificate-based node identifier.
 * <p/>
 * ID is based on certificate fingerprint digest encoded with "-" every 8 characters for better human readability while
 * remaining terse:
 * <p/>
 * <pre>05F4743F-A7565846-43FDF9D0-577BE4FB-079289C6</pre>
 *
 * @since 3.0
 */
public interface LocalNodeAccess
  extends Lifecycle
{
  /**
   * Returns the local-node certificate.
   */
  Certificate getCertificate();

  /**
   * Returns the local-node identifier.
   *
   * This is a variant format of the fingerprint.
   */
  String getId();

  /**
   * Returns the local-node fingerprint.
   *
   * This is the SHA1 of the certificate.
   */
  String getFingerprint();
}
