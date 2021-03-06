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
package org.sonatype.nexus.cache.internal;

import java.util.Map;

import javax.cache.CacheManager;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Runtime {@link CacheManager} provider using {@code nexus.cache.provider}
 * configuration to select the named cache provider to use.
 *
 * Defaults to {@code ehcache}.
 *
 * @since 3.0
 */
@Named("default")
@Singleton
public class RuntimeCacheManagerProvider
    extends ComponentSupport
    implements Provider<CacheManager>
{
  private final Map<String, Provider<CacheManager>> providers;

  private final String name;

  @Inject
  public RuntimeCacheManagerProvider(final Map<String, Provider<CacheManager>> providers,
                                     @Named("${nexus.cache.provider:-ehcache}") final String name)
  {
    this.providers = checkNotNull(providers);
    this.name = checkNotNull(name);
    checkArgument(!"default".equals(name));
    log.info("Cache-provider: {}", name);
    checkState(providers.containsKey(name), "Missing cache-provider: %s", name);
  }

  @Override
  public CacheManager get() {
    Provider<CacheManager> provider = providers.get(name);
    checkState(provider != null, "Cache-provider vanished: %s", name);
    CacheManager manager = provider.get();
    log.debug("Constructed cache-manager: {} -> {}", name, manager);
    return manager;
  }
}
