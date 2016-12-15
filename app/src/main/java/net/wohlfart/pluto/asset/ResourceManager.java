package net.wohlfart.pluto.asset;

import android.content.Context;

import com.google.common.util.concurrent.ListenableFuture;

import net.wohlfart.pluto.CoreRenderView;

/**
 * Created by michael on 14.12.16.
 */

public class ResourceManager {

    private final CoreRenderView coreRenderView;
    private final Context context;

    public ResourceManager(CoreRenderView coreRenderView, Context context) {
        this.coreRenderView = coreRenderView;
        this.context = context;
    }

    interface ResourceDefinition {


    }

    /*
    ListenableFuture<T> get(ResourecDefinition<T> definition) {

    }
    */
}
