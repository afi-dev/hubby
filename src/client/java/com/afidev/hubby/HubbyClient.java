package com.afidev.hubby;

import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HubbyClient implements ClientModInitializer {
	private static final Logger logger = LogManager.getLogger(HubbyClient.class);

	@Override
	public void onInitializeClient() {
		logger.warn("Hubby mod is designed exclusively for server-side installation. This means that you don't need to have this mod installed on players' clients for everything to work properly. All the adjustments and features provided by this mod are managed and executed directly on a server.");
	}
}