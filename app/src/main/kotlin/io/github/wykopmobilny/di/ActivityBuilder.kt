package io.github.wykopmobilny.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.github.wykopmobilny.glide.GlideModule
import io.github.wykopmobilny.ui.modules.addlink.AddlinkActivity
import io.github.wykopmobilny.ui.modules.addlink.AddlinkFragmentProvider
import io.github.wykopmobilny.ui.modules.addlink.AddlinkModule
import io.github.wykopmobilny.ui.modules.embedview.EmbedViewActivity
import io.github.wykopmobilny.ui.modules.embedview.EmbedViewModule
import io.github.wykopmobilny.ui.modules.input.entry.add.AddEntryActivity
import io.github.wykopmobilny.ui.modules.input.entry.add.AddEntryActivityModule
import io.github.wykopmobilny.ui.modules.input.entry.comment.EditEntryCommentActivity
import io.github.wykopmobilny.ui.modules.input.entry.comment.EditEntryCommentActivityModule
import io.github.wykopmobilny.ui.modules.input.entry.edit.EditEntryActivity
import io.github.wykopmobilny.ui.modules.input.entry.edit.EditEntryActivityModule
import io.github.wykopmobilny.ui.modules.input.link.edit.LinkCommentEditActivity
import io.github.wykopmobilny.ui.modules.input.link.edit.LinkCommentEditModule
import io.github.wykopmobilny.ui.modules.links.downvoters.DownvotersActivity
import io.github.wykopmobilny.ui.modules.links.downvoters.DownvotersModule
import io.github.wykopmobilny.ui.modules.links.linkdetails.LinkDetailsActivity
import io.github.wykopmobilny.ui.modules.links.linkdetails.LinkDetailsModule
import io.github.wykopmobilny.ui.modules.links.related.RelatedActivity
import io.github.wykopmobilny.ui.modules.links.related.RelatedModule
import io.github.wykopmobilny.ui.modules.links.upvoters.UpvotersActivity
import io.github.wykopmobilny.ui.modules.links.upvoters.UpvotersModule
import io.github.wykopmobilny.ui.modules.mainnavigation.MainNavigationActivity
import io.github.wykopmobilny.ui.modules.mainnavigation.MainNavigationFragmentProvider
import io.github.wykopmobilny.ui.modules.mainnavigation.MainNavigationModule
import io.github.wykopmobilny.ui.modules.mikroblog.entry.EntryActivity
import io.github.wykopmobilny.ui.modules.mikroblog.entry.EntryDetailModule
import io.github.wykopmobilny.ui.modules.notificationslist.NotificationsListActivity
import io.github.wykopmobilny.ui.modules.notificationslist.NotificationsListFragmentProvider
import io.github.wykopmobilny.ui.modules.notificationslist.NotificationsListModule
import io.github.wykopmobilny.ui.modules.photoview.PhotoViewActivity
import io.github.wykopmobilny.ui.modules.pm.conversation.ConversationActivity
import io.github.wykopmobilny.ui.modules.pm.conversation.ConversationActivityModule
import io.github.wykopmobilny.ui.modules.profile.ProfileActivity
import io.github.wykopmobilny.ui.modules.profile.ProfileFragmentProvider
import io.github.wykopmobilny.ui.modules.profile.ProfileModule
import io.github.wykopmobilny.ui.modules.tag.TagActivity
import io.github.wykopmobilny.ui.modules.tag.TagActivityFragmentProvider
import io.github.wykopmobilny.ui.modules.tag.TagActivityModule

@Module
internal abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract fun glide(): GlideModule

    @ContributesAndroidInjector(modules = [EntryDetailModule::class])
    abstract fun bindEntryDetailsActivity(): EntryActivity

    @ContributesAndroidInjector(modules = [MainNavigationModule::class, MainNavigationFragmentProvider::class])
    abstract fun bindMainNavigationActivity(): MainNavigationActivity

    @ContributesAndroidInjector(modules = [ConversationActivityModule::class])
    abstract fun bindConversationActivity(): ConversationActivity

    @ContributesAndroidInjector
    abstract fun bindPhotoViewActivity(): PhotoViewActivity

    @ContributesAndroidInjector(modules = [AddEntryActivityModule::class])
    abstract fun bindAddEntryActivity(): AddEntryActivity

    @ContributesAndroidInjector(modules = [EditEntryActivityModule::class])
    abstract fun bindEditEntryActivity(): EditEntryActivity

    @ContributesAndroidInjector(modules = [EditEntryCommentActivityModule::class])
    abstract fun bindEditEntryCommentActivity(): EditEntryCommentActivity

    @ContributesAndroidInjector(modules = [LinkDetailsModule::class])
    abstract fun bindLinkDetailsActivity(): LinkDetailsActivity

    @ContributesAndroidInjector(modules = [TagActivityFragmentProvider::class, TagActivityModule::class])
    abstract fun bindTagActivity(): TagActivity

    @ContributesAndroidInjector(modules = [UpvotersModule::class])
    abstract fun bindUpvotersActivity(): UpvotersActivity

    @ContributesAndroidInjector(modules = [DownvotersModule::class])
    abstract fun bindDownvotersActivity(): DownvotersActivity

    @ContributesAndroidInjector(modules = [RelatedModule::class])
    abstract fun bindRelatedActivity(): RelatedActivity

    @ContributesAndroidInjector(modules = [ProfileModule::class, ProfileFragmentProvider::class])
    abstract fun bindProfileActivity(): ProfileActivity

    @ContributesAndroidInjector(modules = [LinkCommentEditModule::class])
    abstract fun bindLinkEditCommentActivity(): LinkCommentEditActivity

    @ContributesAndroidInjector(modules = [NotificationsListFragmentProvider::class, NotificationsListModule::class])
    abstract fun bindNotificationsActivity(): NotificationsListActivity

    @ContributesAndroidInjector(modules = [EmbedViewModule::class])
    abstract fun bindEmbedActivity(): EmbedViewActivity

    @ContributesAndroidInjector(modules = [AddlinkModule::class, AddlinkFragmentProvider::class])
    abstract fun bindAddLinkActivity(): AddlinkActivity
}
