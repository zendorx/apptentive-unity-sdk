/*
 * Copyright (c) 2015, Apptentive, Inc. All Rights Reserved.
 * Please refer to the LICENSE file for the terms and conditions
 * under which redistribution and use of this file is permitted.
 */

package com.apptentive.android.sdk.module.messagecenter.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import com.apptentive.android.sdk.R;
import com.apptentive.android.sdk.module.engagement.interaction.view.common.ApptentiveDialogButton;
import com.apptentive.android.sdk.module.rating.view.ApptentiveBaseDialog;

/**
 * @author Sky Kelsey
 */
public class EmailValidationFailedDialog extends ApptentiveBaseDialog {
	public EmailValidationFailedDialog(Context context) {
		super(context, R.layout.apptentive_message_center_email_validation);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ApptentiveDialogButton ok = (ApptentiveDialogButton) findViewById(R.id.ok);
		ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dismiss();
			}
		});

	}
}
