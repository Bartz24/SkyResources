package com.bartz24.skyresources.plugin.theoneprobe;

import javax.annotation.Nullable;

import com.google.common.base.Function;

import mcjty.theoneprobe.api.ITheOneProbe;

public class GetTOP implements Function<ITheOneProbe, Void> {

  @Nullable
  @Override
  public Void apply(ITheOneProbe probe) {
    probe.registerProvider(new TempProvider());
    probe.registerProvider(new HeatProvider());
    probe.registerProvider(new MultiblockProvider());
    return null;
  }
}