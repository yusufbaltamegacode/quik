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

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkerFactory
import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dev.megacode.quik.blocking.BlockingClient
import dev.megacode.quik.blocking.BlockingManager
import dev.megacode.quik.common.ViewModelFactory
import dev.megacode.quik.common.util.BillingManagerImpl
import dev.megacode.quik.common.util.NotificationManagerImpl
import dev.megacode.quik.common.util.ShortcutManagerImpl
import dev.megacode.quik.feature.conversationinfo.injection.ConversationInfoComponent
import dev.megacode.quik.feature.themepicker.injection.ThemePickerComponent
import dev.megacode.quik.listener.ContactAddedListener
import dev.megacode.quik.listener.ContactAddedListenerImpl
import dev.megacode.quik.manager.ActiveConversationManager
import dev.megacode.quik.manager.ActiveConversationManagerImpl
import dev.megacode.quik.manager.AlarmManager
import dev.megacode.quik.manager.AlarmManagerImpl
import dev.megacode.quik.manager.BillingManager
import dev.megacode.quik.manager.ChangelogManager
import dev.megacode.quik.manager.ChangelogManagerImpl
import dev.megacode.quik.manager.KeyManager
import dev.megacode.quik.manager.KeyManagerImpl
import dev.megacode.quik.manager.NotificationManager
import dev.megacode.quik.manager.PermissionManager
import dev.megacode.quik.manager.PermissionManagerImpl
import dev.megacode.quik.manager.RatingManager
import dev.megacode.quik.manager.ReferralManager
import dev.megacode.quik.manager.ReferralManagerImpl
import dev.megacode.quik.manager.ShortcutManager
import dev.megacode.quik.manager.WidgetManager
import dev.megacode.quik.manager.WidgetManagerImpl
import dev.megacode.quik.mapper.CursorToContact
import dev.megacode.quik.mapper.CursorToContactGroup
import dev.megacode.quik.mapper.CursorToContactGroupImpl
import dev.megacode.quik.mapper.CursorToContactGroupMember
import dev.megacode.quik.mapper.CursorToContactGroupMemberImpl
import dev.megacode.quik.mapper.CursorToContactImpl
import dev.megacode.quik.mapper.CursorToConversation
import dev.megacode.quik.mapper.CursorToConversationImpl
import dev.megacode.quik.mapper.CursorToMessage
import dev.megacode.quik.mapper.CursorToMessageImpl
import dev.megacode.quik.mapper.CursorToPart
import dev.megacode.quik.mapper.CursorToPartImpl
import dev.megacode.quik.mapper.CursorToRecipient
import dev.megacode.quik.mapper.CursorToRecipientImpl
import dev.megacode.quik.mapper.RatingManagerImpl
import dev.megacode.quik.repository.BackupRepository
import dev.megacode.quik.repository.BackupRepositoryImpl
import dev.megacode.quik.repository.BlockingRepository
import dev.megacode.quik.repository.BlockingRepositoryImpl
import dev.megacode.quik.repository.ContactRepository
import dev.megacode.quik.repository.ContactRepositoryImpl
import dev.megacode.quik.repository.ConversationRepository
import dev.megacode.quik.repository.ConversationRepositoryImpl
import dev.megacode.quik.repository.MessageContentFilterRepository
import dev.megacode.quik.repository.MessageContentFilterRepositoryImpl
import dev.megacode.quik.repository.MessageRepository
import dev.megacode.quik.repository.MessageRepositoryImpl
import dev.megacode.quik.repository.ScheduledMessageRepository
import dev.megacode.quik.repository.ScheduledMessageRepositoryImpl
import dev.megacode.quik.repository.SyncRepository
import dev.megacode.quik.repository.SyncRepositoryImpl
import dev.megacode.quik.worker.InjectionWorkerFactory
import javax.inject.Singleton

@Module(subcomponents = [
    ConversationInfoComponent::class,
    ThemePickerComponent::class])
class AppModule(private var application: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context = application

    @Provides
    fun provideContentResolver(context: Context): ContentResolver = context.contentResolver

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Provides
    @Singleton
    fun provideRxPreferences(preferences: SharedPreferences): RxSharedPreferences {
        return RxSharedPreferences.create(preferences)
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
    }

    @Provides
    fun provideViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory = factory

    // Listener

    @Provides
    fun provideContactAddedListener(listener: ContactAddedListenerImpl): ContactAddedListener = listener

    // Manager

    @Provides
    fun provideBillingManager(manager: BillingManagerImpl): BillingManager = manager

    @Provides
    fun provideActiveConversationManager(manager: ActiveConversationManagerImpl): ActiveConversationManager = manager

    @Provides
    fun provideAlarmManager(manager: AlarmManagerImpl): AlarmManager = manager

    @Provides
    fun blockingClient(manager: BlockingManager): BlockingClient = manager

    @Provides
    fun changelogManager(manager: ChangelogManagerImpl): ChangelogManager = manager

    @Provides
    fun provideKeyManager(manager: KeyManagerImpl): KeyManager = manager

    @Provides
    fun provideNotificationsManager(manager: NotificationManagerImpl): NotificationManager = manager

    @Provides
    fun providePermissionsManager(manager: PermissionManagerImpl): PermissionManager = manager

    @Provides
    fun provideRatingManager(manager: RatingManagerImpl): RatingManager = manager

    @Provides
    fun provideShortcutManager(manager: ShortcutManagerImpl): ShortcutManager = manager

    @Provides
    fun provideReferralManager(manager: ReferralManagerImpl): ReferralManager = manager

    @Provides
    fun provideWidgetManager(manager: WidgetManagerImpl): WidgetManager = manager

    // Mapper

    @Provides
    fun provideCursorToContact(mapper: CursorToContactImpl): CursorToContact = mapper

    @Provides
    fun provideCursorToContactGroup(mapper: CursorToContactGroupImpl): CursorToContactGroup = mapper

    @Provides
    fun provideCursorToContactGroupMember(mapper: CursorToContactGroupMemberImpl): CursorToContactGroupMember = mapper

    @Provides
    fun provideCursorToConversation(mapper: CursorToConversationImpl): CursorToConversation = mapper

    @Provides
    fun provideCursorToMessage(mapper: CursorToMessageImpl): CursorToMessage = mapper

    @Provides
    fun provideCursorToPart(mapper: CursorToPartImpl): CursorToPart = mapper

    @Provides
    fun provideCursorToRecipient(mapper: CursorToRecipientImpl): CursorToRecipient = mapper

    // Repository

    @Provides
    fun provideBackupRepository(repository: BackupRepositoryImpl): BackupRepository = repository

    @Provides
    fun provideBlockingRepository(repository: BlockingRepositoryImpl): BlockingRepository = repository

    @Provides
    fun provideMessageContentFilterRepository(repository: MessageContentFilterRepositoryImpl): MessageContentFilterRepository = repository

    @Provides
    fun provideContactRepository(repository: ContactRepositoryImpl): ContactRepository = repository

    @Provides
    fun provideConversationRepository(repository: ConversationRepositoryImpl): ConversationRepository = repository

    @Provides
    fun provideMessageRepository(repository: MessageRepositoryImpl): MessageRepository = repository

    @Provides
    fun provideScheduledMessagesRepository(repository: ScheduledMessageRepositoryImpl): ScheduledMessageRepository = repository

    @Provides
    fun provideSyncRepository(repository: SyncRepositoryImpl): SyncRepository = repository

    // worker factory
    @Provides
    fun provideWorkerFactory(workerFactory: InjectionWorkerFactory): WorkerFactory = workerFactory
}