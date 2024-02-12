package xyz.brainforce.brainforce.framework

import xyz.brainforce.brainforce.framework.lifecycle.InitializeHook
import xyz.brainforce.brainforce.framework.lifecycle.ShutdownHook

interface Application : InitializeHook, ShutdownHook
