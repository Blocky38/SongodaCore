package com.songoda.core.hooks;

public interface Hook {
    /**
     * Get the name of the plugin being used
     */
    abstract String getName();

    /**
     * Check to see if the economy plugin being used is active
     *
     * @return true if the plugin is loaded and active
     */
    abstract boolean isEnabled();
}
