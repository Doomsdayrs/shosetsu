package app.shosetsu.android.ui.settings

import android.view.View
import androidx.annotation.CallSuper
import app.shosetsu.android.view.base.FastAdapterRecyclerController.BasicFastAdapterRecyclerController
import app.shosetsu.android.view.uimodels.settings.base.SettingsItemData
import app.shosetsu.android.viewmodel.abstracted.settings.ASubSettingsViewModel

/*
 * This file is part of shosetsu.
 *
 * shosetsu is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * shosetsu is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with shosetsu.  If not, see <https://www.gnu.org/licenses/>.
 */

/**
 * shosetsu
 * 29 / 01 / 2020
 */
abstract class SettingsSubController : BasicFastAdapterRecyclerController<SettingsItemData>() {
	@CallSuper
	override fun onViewCreated(view: View) {
		viewModel.getSettings().observe(this) { handleRecyclerUpdate(it) }
	}

	abstract val viewModel: ASubSettingsViewModel

	/** Apply adjustments to settings, such as click listeners */
	open val adjustments: List<SettingsItemData>.() -> Unit = {}

	override fun updateUI(newList: List<SettingsItemData>) {
		super.updateUI(newList.apply(adjustments))
	}

	/** Finds a setting via its data ID */
	inline fun <reified T : SettingsItemData> List<SettingsItemData>.find(id: Int): T? =
			filterIsInstance<T>().find { it.id == id }
}
