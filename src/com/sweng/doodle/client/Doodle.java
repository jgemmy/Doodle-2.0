package com.sweng.doodle.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Doodle implements EntryPoint {
	@SuppressWarnings("unused")
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	@Override
	/*Questa funzione lancia tutto*/
	public void onModuleLoad() {
		new Doodle_Main();
	}
 }
