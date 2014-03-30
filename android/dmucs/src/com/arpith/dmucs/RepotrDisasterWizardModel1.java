/*
 * Copyright 2012 Roman Nurik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.arpith.dmucs;

import android.content.Context;
import co.juliansuarez.libwizardpager.wizard.model.AbstractWizardModel;
import co.juliansuarez.libwizardpager.wizard.model.BranchPage;
import co.juliansuarez.libwizardpager.wizard.model.CustomerInfoPage;
import co.juliansuarez.libwizardpager.wizard.model.MultipleFixedChoicePage;
import co.juliansuarez.libwizardpager.wizard.model.NumberPage;
import co.juliansuarez.libwizardpager.wizard.model.PageList;
import co.juliansuarez.libwizardpager.wizard.model.SingleFixedChoicePage;
import co.juliansuarez.libwizardpager.wizard.model.TextPage;

public class RepotrDisasterWizardModel1 extends AbstractWizardModel {
	public RepotrDisasterWizardModel1(Context context) {
		super(context);
	}

	@Override
	protected PageList onNewRootPageList() {
		return new PageList(new BranchPage(this, "Incident Severiaty")
				.addBranch(
						"Small",
						new MultipleFixedChoicePage(this, "Type").setChoices(
								"Murder", "Theft", "Other")
								.setRequired(true),
								
						new NumberPage(this, "No Of Victms").setRequired(true),

						new BranchPage(this, "Were you affected?")
								.addBranch(
										"Yes",
										new SingleFixedChoicePage(this,
												"Toast time").setChoices(
												"30 seconds", "1 minute",
												"2 minutes"))
								.addBranch("No",
										new CustomerInfoPage(this, "Your info")
										.setRequired(true)))

				.addBranch(
						"Medium",
						new SingleFixedChoicePage(this, "Type")
								.setChoices("Fire", "Other")
								.setRequired(true),

						new SingleFixedChoicePage(this, "Dressing").setChoices(
								"No dressing", "Balsamic", "Oil & vinegar",
								"Thousand Island", "Italian").setValue(
								"No dressing"),
								
						new TextPage(this, "Comments").setRequired(true),

						new CustomerInfoPage(this, "Your info")
								.setRequired(true))
				.addBranch(
						"LargeScale",
						new MultipleFixedChoicePage(this, "Type")
								.setChoices("Earthquake", "Tsunami")
								.setRequired(true)

				));
	}
}
