package io.github.wykopmobilny.ui.adapters.viewholders

import android.util.TypedValue
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import io.github.wykopmobilny.R
import io.github.wykopmobilny.databinding.PmmessageSentLayoutBinding
import io.github.wykopmobilny.glide.GlideApp
import io.github.wykopmobilny.models.dataclass.PMMessage
import io.github.wykopmobilny.ui.modules.NewNavigator
import io.github.wykopmobilny.utils.linkhandler.WykopLinkHandler
import io.github.wykopmobilny.utils.textview.prepareBody

class PMMessageViewHolder(
    private val binding: PmmessageSentLayoutBinding,
    private val linkHandler: WykopLinkHandler,
    private val navigator: NewNavigator,
) : RecyclableViewHolder(binding.root) {

    fun bindView(
        message: PMMessage,
        openSpoilersDialog: Boolean,
        enableYoutubePlayer: Boolean,
        enableEmbedPlayer: Boolean,
        showAdultContent: Boolean,
        hideNsfw: Boolean,
    ) {
        flipMessage(message.isSentFromUser)
        binding.apply {
            date.text = message.date

            message.app?.let {
                date.text = message.date
            }

            body.prepareBody(message.body, { linkHandler.handleUrl(it) }, null, openSpoilersDialog)
            embedImage.forceDisableMinimizedMode = true
            embedImage.setEmbed(
                embed = message.embed,
                enableYoutubePlayer = enableYoutubePlayer,
                enableEmbedPlayer = enableEmbedPlayer,
                showAdultContent = showAdultContent,
                hideNsfw = hideNsfw,
                navigator = navigator,
                isNsfw = false,
            )
        }
    }

    /**
     * This function flips the message depending on the direction.
     */
    private fun flipMessage(isSentFromUser: Boolean) {
        val cardViewParams = binding.notificationItem.layoutParams as RelativeLayout.LayoutParams
        // Resolve colors from attr
        val theme = binding.root.context.theme
        val usersMessageColor = TypedValue()
        theme?.resolveAttribute(R.attr.usersMessageColor, usersMessageColor, true)

        val usersMessageSubtextDirected = TypedValue()
        theme?.resolveAttribute(R.attr.usersMessageSubtextDirected, usersMessageSubtextDirected, true)

        val cardBackgroundColor = TypedValue()
        theme?.resolveAttribute(R.attr.yourMessageColor, cardBackgroundColor, true)

        val marginVertical = binding.root.resources.getDimension(R.dimen.padding_dp_mini).toInt()
        val marginHorizontal = binding.root.resources.getDimension(R.dimen.pmmessage_sent_layout_card_margin_horizontal).toInt()
        val marginFlip = binding.root.resources.getDimension(R.dimen.pmmessage_sent_layout_card_margin_flip).toInt()

        if (isSentFromUser) {
            binding.notificationItem.setCardBackgroundColor(usersMessageColor.data)
            binding.date.setTextColor(usersMessageSubtextDirected.data)
            cardViewParams.setMargins(marginFlip, marginVertical, marginHorizontal, marginVertical)
            cardViewParams.apply {
                removeRule(RelativeLayout.ALIGN_PARENT_START)
                addRule(RelativeLayout.ALIGN_PARENT_END)
                height = RelativeLayout.LayoutParams.WRAP_CONTENT
            }
        } else {
            binding.notificationItem.setCardBackgroundColor(cardBackgroundColor.data)
            binding.date.setTextColor(ContextCompat.getColor(binding.root.context, R.color.authorHeader_date_Dark))
            cardViewParams.setMargins(marginHorizontal, marginVertical, marginFlip, marginVertical)
            cardViewParams.apply {
                removeRule(RelativeLayout.ALIGN_PARENT_END)
                addRule(RelativeLayout.ALIGN_PARENT_START)
                height = RelativeLayout.LayoutParams.WRAP_CONTENT
            }
        }

        binding.notificationItem.layoutParams = cardViewParams
    }

    fun cleanRecycled() {
        binding.apply {
            date.text = null
            body.text = null
            GlideApp.with(this.root).clear(embedImage)
        }
    }
}
