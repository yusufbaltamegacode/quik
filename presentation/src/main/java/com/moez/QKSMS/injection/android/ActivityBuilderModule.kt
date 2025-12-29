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
package dev.megacode.quik.injection.android

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.megacode.quik.feature.backup.BackupActivity
import dev.megacode.quik.feature.blocking.BlockingActivity
import dev.megacode.quik.feature.compose.ComposeActivity
import dev.megacode.quik.feature.compose.ComposeActivityModule
import dev.megacode.quik.feature.contacts.ContactsActivity
import dev.megacode.quik.feature.contacts.ContactsActivityModule
import dev.megacode.quik.feature.conversationinfo.ConversationInfoActivity
import dev.megacode.quik.feature.gallery.GalleryActivity
import dev.megacode.quik.feature.gallery.GalleryActivityModule
import dev.megacode.quik.feature.main.MainActivity
import dev.megacode.quik.feature.main.MainActivityModule
import dev.megacode.quik.feature.notificationprefs.NotificationPrefsActivity
import dev.megacode.quik.feature.notificationprefs.NotificationPrefsActivityModule
import dev.megacode.quik.feature.plus.PlusActivity
import dev.megacode.quik.feature.plus.PlusActivityModule
import dev.megacode.quik.feature.qkreply.QkReplyActivity
import dev.megacode.quik.feature.qkreply.QkReplyActivityModule
import dev.megacode.quik.feature.scheduled.ScheduledActivity
import dev.megacode.quik.feature.scheduled.ScheduledActivityModule
import dev.megacode.quik.feature.settings.SettingsActivity
import dev.megacode.quik.injection.scope.ActivityScope

@Module
abstract class ActivityBuilderModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [PlusActivityModule::class])
    abstract fun bindPlusActivity(): PlusActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [])
    abstract fun bindBackupActivity(): BackupActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [ComposeActivityModule::class])
    abstract fun bindComposeActivity(): ComposeActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [ContactsActivityModule::class])
    abstract fun bindContactsActivity(): ContactsActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [])
    abstract fun bindConversationInfoActivity(): ConversationInfoActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [GalleryActivityModule::class])
    abstract fun bindGalleryActivity(): GalleryActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [NotificationPrefsActivityModule::class])
    abstract fun bindNotificationPrefsActivity(): NotificationPrefsActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [QkReplyActivityModule::class])
    abstract fun bindQkReplyActivity(): QkReplyActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [ScheduledActivityModule::class])
    abstract fun bindScheduledActivity(): ScheduledActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [])
    abstract fun bindSettingsActivity(): SettingsActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [])
    abstract fun bindBlockingActivity(): BlockingActivity

}
