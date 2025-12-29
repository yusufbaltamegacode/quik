/*
 * Copyright (C) 2017 Moez Bhatti <moez.bhatti@gmail.com>
 *
 * This file is part of QKSMS.
 *
 * QKSMS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * QKSMS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with QKSMS.  If not, see <http://www.gnu.org/licenses/>.
 */
package dev.megacode.quik.injection

import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import dev.megacode.quik.common.QKApplication
import dev.megacode.quik.common.QkDialog
import dev.megacode.quik.common.util.QkChooserTargetService
import dev.megacode.quik.common.widget.AvatarView
import dev.megacode.quik.common.widget.PagerTitleView
import dev.megacode.quik.common.widget.PreferenceView
import dev.megacode.quik.common.widget.QkEditText
import dev.megacode.quik.common.widget.QkSwitch
import dev.megacode.quik.common.widget.QkTextView
import dev.megacode.quik.common.widget.RadioPreferenceView
import dev.megacode.quik.feature.backup.BackupController
import dev.megacode.quik.feature.blocking.BlockingController
import dev.megacode.quik.feature.blocking.filters.MessageContentFiltersController
import dev.megacode.quik.feature.blocking.manager.BlockingManagerController
import dev.megacode.quik.feature.blocking.messages.BlockedMessagesController
import dev.megacode.quik.feature.blocking.numbers.BlockedNumbersController
import dev.megacode.quik.feature.compose.editing.DetailedChipView
import dev.megacode.quik.feature.conversationinfo.injection.ConversationInfoComponent
import dev.megacode.quik.feature.settings.SettingsController
import dev.megacode.quik.feature.settings.about.AboutController
import dev.megacode.quik.feature.settings.swipe.SwipeActionsController
import dev.megacode.quik.feature.themepicker.injection.ThemePickerComponent
import dev.megacode.quik.feature.widget.WidgetAdapter
import dev.megacode.quik.injection.android.ActivityBuilderModule
import dev.megacode.quik.injection.android.BroadcastReceiverBuilderModule
import dev.megacode.quik.injection.android.ServiceBuilderModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityBuilderModule::class,
    BroadcastReceiverBuilderModule::class,
    ServiceBuilderModule::class])
interface AppComponent {

    fun conversationInfoBuilder(): ConversationInfoComponent.Builder
    fun themePickerBuilder(): ThemePickerComponent.Builder

    fun inject(application: QKApplication)

    fun inject(controller: AboutController)
    fun inject(controller: BackupController)
    fun inject(controller: BlockedMessagesController)
    fun inject(controller: BlockedNumbersController)
    fun inject(controller: MessageContentFiltersController)
    fun inject(controller: BlockingController)
    fun inject(controller: BlockingManagerController)
    fun inject(controller: SettingsController)
    fun inject(controller: SwipeActionsController)

    fun inject(dialog: QkDialog)

    fun inject(service: WidgetAdapter)

    /**
     * This can't use AndroidInjection, or else it will crash on pre-marshmallow devices
     */
    fun inject(service: QkChooserTargetService)

    fun inject(view: AvatarView)
    fun inject(view: DetailedChipView)
    fun inject(view: PagerTitleView)
    fun inject(view: PreferenceView)
    fun inject(view: RadioPreferenceView)
    fun inject(view: QkEditText)
    fun inject(view: QkSwitch)
    fun inject(view: QkTextView)

}
