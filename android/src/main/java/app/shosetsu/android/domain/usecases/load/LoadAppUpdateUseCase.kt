package app.shosetsu.android.domain.usecases.load

import app.shosetsu.common.domain.repositories.base.IAppUpdatesRepository

/*
 * This file is part of Shosetsu.
 *
 * Shosetsu is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Shosetsu is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Shosetsu.  If not, see <https://www.gnu.org/licenses/>.
 */

/**
 * 22 / 12 / 2020
 *
 * Loads the app update, without calling network
 */
class LoadAppUpdateUseCase(
	private val repo: IAppUpdatesRepository
) {
	suspend operator fun invoke() = repo.loadAppUpdate()
}